package cn.horry.teaching_information_exchange.ui.activity;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.api.CourseApi;
import cn.horry.teaching_information_exchange.entity.Course;
import cn.horry.teaching_information_exchange.ui.UserManager;

public class addCourseActivity extends BaseActivity implements View.OnClickListener{

    @BindView(id = R.id.left , click = true)
    private TextView left;
    @BindView(id = R.id.title)
    private TextView title;
    @BindView(id = R.id.course_title)
    private AutoCompleteTextView course_title;
    @BindView(id = R.id.course_content)
    private EditText course_content;
    @BindView(id = R.id.add , click = true)
    private View add;
    @Override
    public void setRootView() {
        setContentView(R.layout.activity_add_course);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initWidget() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.left:
                finish();
                break;
            case R.id.add:
                Course course = new Course();
                course.setContent(course_content.getText().toString().trim());
                course.setName(course_title.getText().toString().trim());
                course.settId(UserManager.getInstance().getUser().getId());
                course.setTime(System.currentTimeMillis());
                CourseApi.addCourse(course, new HttpCallBack() {
                    @Override
                    public void onSuccess(String t) {
                        showShortText("添加成功");
                        finish();
                    }

                    @Override
                    public void onFailure(int errorNo, String strMsg) {
                        showShortText("添加失败");
                    }
                });
                break;
            default:
                break;
        }
    }
}
