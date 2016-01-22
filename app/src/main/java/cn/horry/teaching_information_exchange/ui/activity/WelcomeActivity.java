package cn.horry.teaching_information_exchange.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import cn.horry.teaching_information_exchange.R;


public class WelcomeActivity extends BaseActivity {

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_welcome);
    }

    @Override
    public void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoLogin();
            }
        },2000);
    }

    @Override
    public void initWidget() {

    }

    private void gotoLogin(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
