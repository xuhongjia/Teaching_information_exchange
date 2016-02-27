package cn.horry.teaching_information_exchange.ui.activity;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;

import java.text.SimpleDateFormat;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.api.CourseApi;
import cn.horry.teaching_information_exchange.entity.Course;
import cn.horry.teaching_information_exchange.entity.Student_Course;
import cn.horry.teaching_information_exchange.ui.UserManager;

public class addStudentCourseActivity extends BaseActivity implements View.OnClickListener{

    @BindView(id = R.id.left ,click = true)
    private TextView left;
    @BindView(id = R.id.title)
    private TextView title;
    @BindView(id = R.id.content)
    private TextView content;
    @BindView(id = R.id.course_title)
    private TextView course_title;
    @BindView(id = R.id.teacher_name)
    private TextView teacher_name;
    @BindView(id = R.id.course_time)
    private TextView course_time;
    @BindView(id = R.id.select , click = true)
    private View select;
    private Course course;
    @Override
    public void setRootView() {
        setContentView(R.layout.activity_add_student_course);
    }

    @Override
    public void initData() {
        course = (Course) getIntent().getSerializableExtra("Course");
    }

    @Override
    public void initWidget() {
        left.setVisibility(View.VISIBLE);
        left.setText("");
        title.setText("课程信息");
        content.setText(course.getContent());
        course_title.setText(course.getName());
        course_time.setText(new SimpleDateFormat("yy-MM-dd").format(course.getTime()));
        teacher_name.setText(course.getT_name());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.left:
                finish();
                break;
            case R.id.select:
                Student_Course student_course = new Student_Course();
                student_course.setcId(course.getId());
                student_course.setuId(UserManager.getInstance().getUser().getId());
                CourseApi.addStudentCourse(student_course, new HttpCallBack() {
                    @Override
                    public void onSuccess(String t) {
                        showShortText("添加成功");
                        finish();
                    }

                    @Override
                    public void onFailure(int errorNo, String strMsg) {
                        super.onFailure(errorNo, strMsg);
                    }
                });
                break;
            default:
                break;
        }
    }
}
