package cn.horry.teaching_information_exchange.ui.activity;


import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;

import java.text.SimpleDateFormat;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.api.HomeWorkApi;
import cn.horry.teaching_information_exchange.entity.CourseHomeWork;
import cn.horry.teaching_information_exchange.entity.GeneralResponse;

public class HomeWorkTeacherActivity extends BaseActivity implements View.OnClickListener{
    @BindView(id = R.id.left , click = true)
    private TextView left;
    @BindView(id = R.id.title)
    private TextView title;
    @BindView(id = R.id.course_title)
    private TextView course_title;
    @BindView(id = R.id.time)
    private TextView time;
    @BindView(id = R.id.content)
    private EditText content;
    @BindView(id = R.id.update , click = true)
    private View update;
    private CourseHomeWork courseHomeWork;
    @Override
    public void setRootView() {
        setContentView(R.layout.activity_home_work_teacher);
    }

    @Override
    public void initData() {
        courseHomeWork = (CourseHomeWork) getIntent().getSerializableExtra("course");
    }

    @Override
    public void initWidget() {
        left.setVisibility(View.VISIBLE);
        left.setText("");
        title.setText("作业信息");
        course_title.setText(courseHomeWork.getName());
        time.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(courseHomeWork.getTime()));
        content.setText(courseHomeWork.getContent());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.left:
                finish();
                break;
            case R.id.update:
                final String c = content.getText().toString().trim();
                if(!content.equals(""))
                {
                    HomeWorkApi.updateHomeWork(courseHomeWork.gethId(), c, new HttpCallBack() {
                        @Override
                        public void onSuccess(String t) {
                            GeneralResponse<Integer> response = new Gson().fromJson(t,new TypeToken<GeneralResponse<Integer>>(){}.getType());
                            if(response.isSuccess())
                            {
                                courseHomeWork.setContent(c);
                                showShortText("修改成功！");
                            }
                        }

                        @Override
                        public void onFailure(int errorNo, String strMsg) {
                            super.onFailure(errorNo, strMsg);
                        }
                    });
                }
                break;
            default:
                break;
        }
    }
}
