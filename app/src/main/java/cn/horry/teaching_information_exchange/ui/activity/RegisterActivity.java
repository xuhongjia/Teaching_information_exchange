package cn.horry.teaching_information_exchange.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.kymjs.kjframe.ui.AnnotateUtil;
import org.kymjs.kjframe.ui.BindView;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.listener.MySmsListener;
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
        showShortText("注册成功，请等待！");
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.send_validation :
                count.start();
                mobSMS.getSmssdk().getVerificationCode("86",account.getText().toString().trim());
                break;
            case R.id.submit_validation :
                mobSMS.getSmssdk().submitVerificationCode("86",account.getText().toString().trim(),validation_code.getText().toString().trim());
                break;
            case R.id.left:
                finish();
                break;
            default:
                break;
        }
    }
}
