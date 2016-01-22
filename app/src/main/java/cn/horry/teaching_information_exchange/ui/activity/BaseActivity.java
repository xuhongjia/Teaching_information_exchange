package cn.horry.teaching_information_exchange.ui.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import org.kymjs.kjframe.ui.AnnotateUtil;

import cn.horry.teaching_information_exchange.ui.ActivityManager;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView();
        AnnotateUtil.initBindView(this);
        initData();
        initWidget();
        ActivityManager.getInstance().addActivity(this);
    }
    public abstract void setRootView();
    public abstract void initData();
    public abstract void initWidget();
    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }
    public void refreshData(){

    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().removeActivity(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void showLongText(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
    public void showShortText(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
