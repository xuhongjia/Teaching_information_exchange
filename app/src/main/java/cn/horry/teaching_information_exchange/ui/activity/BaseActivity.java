package cn.horry.teaching_information_exchange.ui.activity;


import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import org.kymjs.kjframe.ui.AnnotateUtil;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.ui.ActivityManager;

public abstract class BaseActivity extends AppCompatActivity {

    public static final int VIDEO_CONTENT_DESC_MAX_LINE = 4;// 默认展示最大行数3行
    public static final int SHOW_CONTENT_NONE_STATE = 0;// 扩充
    public static final int SHRINK_UP_STATE = 1;// 收起状态
    public static final int SPREAD_STATE = 2;// 展开状态
    public static int mState = SHRINK_UP_STATE;
    public Drawable up;
    public Drawable down;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView();
        AnnotateUtil.initBindView(this);
        initData();
        up = getResources().getDrawable(R.mipmap.detail_up);
        up.setBounds(0, 0, up.getMinimumWidth(), up.getMinimumHeight());
        down = getResources().getDrawable(R.mipmap.detail_down);
        down.setBounds(0, 0, down.getMinimumWidth(), down.getMinimumHeight());
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
