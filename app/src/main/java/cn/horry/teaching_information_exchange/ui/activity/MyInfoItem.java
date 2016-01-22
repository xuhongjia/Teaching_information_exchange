package cn.horry.teaching_information_exchange.ui.activity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.api.UserApi;
import cn.horry.teaching_information_exchange.entity.User;
import cn.horry.teaching_information_exchange.ui.UserManager;

public class MyInfoItem extends BaseActivity implements View.OnClickListener {

    private int flag;
    @BindView(id = R.id.title)
    private TextView title;
    @BindView(id = R.id.left , click = true)
    private TextView left;
    @BindView(id = R.id.right , click = true)
    private TextView right;
    @BindView(id = R.id.name)
    private View name;
    @BindView(id = R.id.age)
    private View age;
    @BindView(id = R.id.sex)
    private View sex;
    @BindView(id = R.id.name_text)
    private TextView name_text;
    @BindView(id = R.id.age_text)
    private TextView age_text;
    @BindView(id = R.id.sex_text)
    private TextView sex_text;
    @BindView(id = R.id.man)
    private RadioButton man;
    private User user;
    @Override
    public void setRootView() {
        setContentView(R.layout.activity_my_info_item);
    }

    @Override
    public void initData() {
        flag=getIntent().getIntExtra("flag", 0);
        user = UserManager.getInstance().getUser();
    }

    @Override
    public void initWidget() {
        left.setText("");
        left.setVisibility(View.VISIBLE);
        right.setText("保存");
        right.setVisibility(View.VISIBLE);
        switch (flag)
        {
            case 0:
                break;
            case 1://名称
                title.setText("名称");
                name.setVisibility(View.VISIBLE);
                age.setVisibility(View.GONE);
                sex.setVisibility(View.GONE);
                break;
            case 2://年龄
                title.setText("年龄");
                name.setVisibility(View.GONE);
                age.setVisibility(View.VISIBLE);
                sex.setVisibility(View.GONE);
                break;
            case 3://性别
                title.setText("性别");
                name.setVisibility(View.GONE);
                age.setVisibility(View.GONE);
                sex.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.right:
                save();
                finish();
                break;
            case R.id.left:
                finish();
                break;
            default:
                break;
        }
    }

    private void save(){
        switch (flag)
        {
            case 0:
                break;
            case 1://名称
                user.setName(name_text.getText().toString().trim());
                break;
            case 2://年龄
                user.setAge(Integer.valueOf(age_text.getText().toString().trim()));
                break;
            case 3://性别
                if(man.isChecked())
                {
                    user.setSex(0);
                }else
                {
                    user.setSex(1);
                }
                break;
            default:
                break;
        }
        UserApi.update(user, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
            }
        });
    }
}
