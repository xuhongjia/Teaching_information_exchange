package cn.horry.teaching_information_exchange.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;

import java.util.ArrayList;
import java.util.List;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.adapter.SelectCourseAdapter;
import cn.horry.teaching_information_exchange.adapter.ViewHolder;
import cn.horry.teaching_information_exchange.api.UserApi;
import cn.horry.teaching_information_exchange.entity.Course;
import cn.horry.teaching_information_exchange.entity.GeneralResponse;
import cn.horry.teaching_information_exchange.entity.User;
import cn.horry.teaching_information_exchange.ui.UserManager;

public class CourseInfoActivity extends BaseActivity implements View.OnClickListener {

    @BindView(id = R.id.left , click = true)
    private TextView left;
    @BindView(id = R.id.title)
    private TextView title;
    @BindView(id = R.id.right , click = true)
    private TextView right;
    @BindView(id = R.id.my_course)
    private ListView my_course;
    private SelectCourseAdapter selectCourseAdapter;
    private List<Course> data=new ArrayList<>();
    private User user;
    @Override
    public void setRootView() {
        setContentView(R.layout.activity_course_info);
    }

    @Override
    public void initData() {
        user= UserManager.getInstance().getUser();
        UserApi.getCourse(user.getId(), user.getIsTeacher(), new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                GeneralResponse<List<Course>> response = new Gson().fromJson(t,new TypeToken<GeneralResponse<List<Course>>>(){}.getType());
                if (response.isSuccess())
                {
                    data=response.getData();
                    selectCourseAdapter.setMdatas(data);
                    selectCourseAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
            }
        });
        selectCourseAdapter=new SelectCourseAdapter(this,data,R.layout.select_course_list_item);
    }

    @Override
    public void initWidget() {
        left.setText("");
        left.setVisibility(View.VISIBLE);
        title.setText("我的课程");
        right.setText("添加");
        right.setVisibility(View.VISIBLE);
        my_course.setAdapter(selectCourseAdapter);
        my_course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course course = (Course)((ViewHolder) view.getTag()).getView(R.id.course_name).getTag();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.left:
                finish();
                break;
            case R.id.right:
                if (user.getIsTeacher()==0)
                {//学生添加选课
                    startActivity(new Intent(this,SelectCourseForStudentActivity.class));
                }else{
                    //教师跳转到添加课程信息
                    startActivity(new Intent(this,addCourseActivity.class));
                }
                break;
            default:
                break;
        }
    }
}
