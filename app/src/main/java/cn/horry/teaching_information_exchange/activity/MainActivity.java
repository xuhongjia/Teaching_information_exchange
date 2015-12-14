package cn.horry.teaching_information_exchange.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioGroup;

import org.kymjs.kjframe.ui.AnnotateUtil;
import org.kymjs.kjframe.ui.BindView;

import java.util.List;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.widget.MyViewPager;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(id = R.id.menu )
    private RadioGroup menu;
    @BindView(id = R.id.viewPager)
    private MyViewPager viewPager;
    private List<Fragment> fragmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnnotateUtil.initBindView(this);
        initData();
        initWidget();
    }
    public void initWidget(){
        menu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                showShortText("切换成功");
            }
        });
    }
    public void initData(){

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.menu:
                break;
            default:
                break;
        }
    }
}
