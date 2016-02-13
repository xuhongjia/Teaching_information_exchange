package cn.horry.teaching_information_exchange.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.kymjs.kjframe.ui.BindView;

import java.text.SimpleDateFormat;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.entity.CourseHomeWork;

public class HomeWorkActivity extends BaseActivity implements View.OnClickListener{

    @BindView(id = R.id.left , click = true)
    private TextView left;
    @BindView(id = R.id.title)
    private TextView title;
    @BindView(id = R.id.course_title)
    private TextView course_title;
    @BindView(id = R.id.time)
    private TextView time;
    @BindView(id = R.id.content)
    private TextView content;
    private CourseHomeWork courseHomeWork;
    @Override
    public void setRootView() {
        setContentView(R.layout.activity_home_work);
    }

    @Override
    public void initData() {
        courseHomeWork = (CourseHomeWork) getIntent().getSerializableExtra("course");
    }

    @Override
    public void initWidget() {
        left.setText("");
        left.setVisibility(View.VISIBLE);
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
            default:
                break;
        }
    }
}
