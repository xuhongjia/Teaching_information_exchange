package cn.horry.teaching_information_exchange.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.AnnotateUtil;
import org.kymjs.kjframe.ui.BindView;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.api.API;
import cn.horry.teaching_information_exchange.api.UserApi;
import cn.horry.teaching_information_exchange.entity.GeneralResponse;
import cn.horry.teaching_information_exchange.entity.User;
import cn.horry.teaching_information_exchange.listener.MySmsListener;
import cn.horry.teaching_information_exchange.ui.UserManager;
import cn.horry.teaching_information_exchange.utils.MobSMS;
import cn.horry.teaching_information_exchange.utils.TimeCount;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    @BindView(id = R.id.account )
    private AutoCompleteTextView account;
    @BindView(id = R.id.validation_code)
    private EditText validation_code;
    @BindView(id = R.id.send_validation , click = true)
    private Button send_validation;
    @BindView(id = R.id.submit_validation , click = true)
    private Button submit_validation;
    @BindView(id = R.id.left , click = true)
    private TextView left;
    @BindView(id = R.id.title , click = true)
    private TextView title;
    @BindView(id = R.id.register_progress)
    private View mProgressView;
    @BindView(id = R.id.password)
    private TextView password;
    @BindView(id = R.id.register_form)
    private View register_form;
    @BindView(id = R.id.isTeacher)
    private RadioGroup isTeacher;
    private TimeCount count;
    private MobSMS mobSMS;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AnnotateUtil.initBindView(this);
        initData();
        initWidget();
    }
    public void initData(){
        count = new TimeCount(60*1000,1000,send_validation);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                //0为失败，1为发送成功，2为验证成功,-1位通过智能验证
                super.handleMessage(msg);
                if(msg.what == 0)
                {
                    Throwable data = (Throwable) msg.obj;
                    showShortText(data.getMessage());
                }else if(msg.what == 1)
                {
                    showShortText("发送成功");
                }else if(msg.what ==2){
                    PassVerification();
                }else if(msg.what==-1){
                    PassVerification();
                }
            }
        };
        MySmsListener mySmsListener = new MySmsListener(handler);
        mobSMS = MobSMS.getInstance(this);
        mobSMS.setListenerAndRegiterEventHandler(mySmsListener);
    }
    public void initWidget(){
        title.setText("注册");
        left.setVisibility(View.VISIBLE);
        left.setText("");
    }

    public void PassVerification(){
        User user = new User();
        user.setAccount(account.getText().toString().trim());
        user.setPassword(password.getText().toString().trim());
        user.setImg(API.DEFAULT_HEAD_IMG);
        user.setName("用户" + String.valueOf(System.currentTimeMillis()).substring(6));
        user.setLastLoginTime(System.currentTimeMillis());
        if(isTeacher.getCheckedRadioButtonId()==R.id.teacher)
        {
            user.setIsTeacher(1);
        }else
        {
            user.setIsTeacher(0);
        }
        user.setAge(0);
        user.setSex(0);
        UserApi.register(user, new HttpCallBack() {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);

            }
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                GeneralResponse<User> response = new Gson().fromJson(t, new TypeToken<GeneralResponse<String>>() {
                }.getType());
                if (response.isSuccess()) {
                    UserManager.getInstance().setUser(response.getData());
                    showProgress(false);
                    showShortText("注册成功，请重新登录！");
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class)); //无User用户
                } else {
                    showProgress(false);
                    showShortText(response.getMsg());
                }
            }
        });
    }

    /**
     * 点击事件
     * @param v 点击的View
     */
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.send_validation :
                count.start();
                mobSMS.getSmssdk().getVerificationCode("86",account.getText().toString().trim());
                break;
            case R.id.submit_validation :
                if(checkValue()) {
                    showProgress(true);
                    mobSMS.getSmssdk().submitVerificationCode("86", account.getText().toString().trim(), validation_code.getText().toString().trim());
                }
                break;
            case R.id.left:
                finish();
                break;
            default:
                break;
        }
    }
    /**
     *判断输入框的值是否合法
     */
    private boolean checkValue(){
        if(account.getText().toString().trim().length()!=11)
        {
            showShortText("请输入正确的手机号码！");
            return false;
        }
        if(password.getText().toString().trim().length()<6)
        {
            showShortText("密码需要大于等于6位！");
            return false;
        }
        if(validation_code.getText().toString().trim().length()!=4)
        {
            showShortText("请输入4位验证码！");
            return false;
        }
        return true;
    }

    /**
     * 判断显示progress
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // 大于等于sdk 13的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            register_form.setVisibility(show ? View.GONE : View.VISIBLE);
            register_form.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    register_form.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            //不同sdk的设置
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            register_form.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
