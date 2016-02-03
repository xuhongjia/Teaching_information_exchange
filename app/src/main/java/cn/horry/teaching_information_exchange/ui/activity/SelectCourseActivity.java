package cn.horry.teaching_information_exchange.ui.activity;

import android.content.Intent;
import android.os.Bundle;
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

public class SelectCourseActivity extends BaseActivity implements View.OnClickListener{

    private User user;
    private SelectCourseAdapter selectCourseAdapter ;
    @BindView(id = R.id.select_course)
    private ListView select_course;
    @BindView(id = R.id.left , click = true)
    private TextView left;
    @BindView(id = R.id.title)
    private TextView title;
    @Override
    public void setRootView() {
        setContentView(R.layout.activity_select_course);
    }

    @Override
    public void initData() {
        user = UserManager.getInstance().getUser();
        selectCourseAdapter = new SelectCourseAdapter(this, new ArrayList<Course>(),R.layout.select_course_list_item);
        select_course.setAdapter(selectCourseAdapter);
        UserApi.getCourse(user.getId(), user.getIsTeacher(), new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                GeneralResponse<List<Course>> response = new Gson().fromJson(t,new TypeToken<GeneralResponse<List<Course>>>(){}.getType());
                if(response.isSuccess())
                {
                    selectCourseAdapter.setMdatas(response.getData());
                    selectCourseAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
            }
        });
    }

    @Override
    public void initWidget() {
        left.setVisibility(View.VISIBLE);
        left.setText("");
        title.setText("请选择课程");
        select_course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course course = (Course)((ViewHolder) view.getTag()).getView(R.id.course_name).getTag();
                //跳转到详细界面
                Bundle bundle = new Bundle();
                bundle.putSerializable("Course",course);
                Intent intent = new Intent(SelectCourseActivity.this, AddSignActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
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
            default:
                break;
        }
    }
}
