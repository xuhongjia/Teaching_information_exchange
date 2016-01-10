package cn.horry.teaching_information_exchange.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.kymjs.kjframe.ui.AnnotateUtil;

import cn.horry.teaching_information_exchange.R;

public class MyInforActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_infor);
        AnnotateUtil.initBindView(this);
    }
}
