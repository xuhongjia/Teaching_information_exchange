package cn.horry.teaching_information_exchange.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import cn.horry.teaching_information_exchange.R;


public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoLogin();
            }
        },2000);
    }
    private void gotoLogin(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
