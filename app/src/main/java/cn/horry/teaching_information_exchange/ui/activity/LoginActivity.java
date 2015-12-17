package cn.horry.teaching_information_exchange.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.AnnotateUtil;
import org.kymjs.kjframe.ui.BindView;

import java.util.ArrayList;
import java.util.List;

import cn.horry.teaching_information_exchange.ui.GsonManager;
import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.ui.UserManager;
import cn.horry.teaching_information_exchange.api.UserApi;
import cn.horry.teaching_information_exchange.entity.GeneralResponse;
import cn.horry.teaching_information_exchange.entity.User;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity  implements  OnClickListener{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;


    // UI references.
    @BindView(id = R.id.account)
    private AutoCompleteTextView mAccount;
    @BindView(id = R.id.password)
    private EditText mPasswordView;
    @BindView(id = R.id.login_progress)
    private View mProgressView;
    @BindView(id = R.id.toolbar)
    private Toolbar toolbar;
    @BindView(id = R.id.isTeacher)
    private RadioGroup isTeacher;
    private int IsTeacher;
    @BindView(id = R.id.rootLayout)
    private View rootLayout;
    @BindView(id = R.id.sign_in_button , click = true)
    private Button SignInButton;
    @BindView(id = R.id.forget_password , click = true)
    private TextView forget_password;
    @BindView(id = R.id.register , click = true)
    private TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnnotateUtil.initBindView(this);
        initWidget();
    }

    /**
     * 初始化控件
     */
    private void initWidget(){

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        toolbar.setTitle("教学信息交流平台");
        toolbar.setTitleTextColor(Color.YELLOW);
        setSupportActionBar(toolbar);
        isTeacher.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.teacher) {
                    IsTeacher = 1;
                    showShortText("是教师");

                } else {
                    IsTeacher = 0;
                }
            }
        });
    }
    /**
     * handler跳转
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mAccount.setText("");
            mPasswordView.setText("");
            ((RadioButton)isTeacher.getChildAt(0)).setChecked(true);
            showProgress(false);
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
    };
    private void attemptLogin() {
        // 获取输入框文本
        String account = mAccount.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;
         // 验证密码

        // 检测账号
        if (TextUtils.isEmpty(account)) {
            showShortText(getString(R.string.error_field_required));
            focusView = mAccount;
            cancel = true;
        }else if (TextUtils.isEmpty(password) && !isPasswordValid(password))
        {
            showShortText(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            //焦点给在验证出错的view上
            focusView.requestFocus();
        } else {
            //验证通过，显示progress；
            showProgress(true);
            //调用api
            UserApi.login(account, password, IsTeacher, new HttpCallBack() {
                @Override
                public void onSuccess(String t) {
                    super.onSuccess(t);
                    GeneralResponse<User> response = GsonManager.getInstance().getGson().fromJson(t, new TypeToken<GeneralResponse<User>>() {
                    }.getType());
                    if (response.isSuccess()) {
                        UserManager.getInstance().setUser(response.getData());
                        handler.sendEmptyMessageDelayed(0, 2000);
                        showShortText("登录成功");
                    } else {
                        showShortText("登录失败");
                        showProgress(false);
                    }
                }

                @Override
                public void onFailure(int errorNo, String strMsg) {
                    super.onFailure(errorNo, strMsg);
                }
            });

        }
    }

    /**
     * 验证密码
     * @param password
     * @return
     */
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * 判断显示progress
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // 大于等于sdk 13的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            rootLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            rootLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    rootLayout.setVisibility(show ? View.GONE : View.VISIBLE);
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
            rootLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void gotoRegister(){
        startActivity(new Intent(this,RegisterActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.sign_in_button:
                //进行登录跳转
                attemptLogin();
                break;
            case R.id.forget_password:
                //忘记密码入口
                break;
            case R.id.register:
                //跳转注册页面
                gotoRegister();
                break;
            default:
                break;
        }
    }
}

