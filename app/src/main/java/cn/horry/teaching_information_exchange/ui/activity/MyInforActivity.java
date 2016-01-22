package cn.horry.teaching_information_exchange.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.kymjs.kjframe.ui.AnnotateUtil;
import org.kymjs.kjframe.ui.BindView;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.entity.User;
import cn.horry.teaching_information_exchange.ui.UserManager;

public class MyInforActivity extends BaseActivity implements View.OnClickListener {

    @BindView(id = R.id.title)
    private TextView title;
    @BindView(id = R.id.left , click = true)
    private TextView left;
    @BindView(id = R.id.name , click = true)
    private View name;
    @BindView(id = R.id.age , click = true)
    private View age;
    @BindView(id = R.id.sex , click = true)
    private View sex;
    @BindView(id = R.id.name_text)
    private TextView name_text;
    @BindView(id = R.id.age_text)
    private TextView age_text;
    @BindView(id = R.id.sex_text)
    private TextView sex_text;
    private User user;
    @Override
    public void setRootView() {
        setContentView(R.layout.activity_my_infor);
    }

    @Override
    public void initData() {
        user = UserManager.getInstance().getUser();
    }

    public void refreshData(){
        initWidget();
    }
    @Override
    public void initWidget() {
        title.setText("我的信息");
        left.setVisibility(View.VISIBLE);
        left.setText("");
        name_text.setText(user.getName());
        age_text.setText(user.getAge().toString());
        sex_text.setText(getSex(user.getSex()));
    }
    private String getSex(int sex){
        String s;
        if(sex==0)
        {
            s="男";
        }
        else if(sex==1)
        {
            s="女";
        }else
        {
            s="未设置";
        }
        return s;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.left:
                finish();
                break;
            case R.id.name:
                goUpdate(1);
                break;
            case R.id.age:
                goUpdate(2);
                break;
            case R.id.sex:
                goUpdate(3);
                break;
            default:
                break;
        }
    }
    private void goUpdate(int flag){
        Intent intent = new Intent(this,MyInfoItem.class);
        intent.putExtra("flag",flag);
        startActivity(intent);
    }
}
