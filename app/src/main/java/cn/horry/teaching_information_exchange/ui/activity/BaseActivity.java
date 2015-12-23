package cn.horry.teaching_information_exchange.ui.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import cn.horry.teaching_information_exchange.ui.ActivityManager;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
