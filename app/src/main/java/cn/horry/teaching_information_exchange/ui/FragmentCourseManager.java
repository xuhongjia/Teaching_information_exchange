package cn.horry.teaching_information_exchange.ui;

import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.adapter.CommonBaseAdapter;
import cn.horry.teaching_information_exchange.adapter.ViewHolder;
import cn.horry.teaching_information_exchange.entity.Course;
import cn.horry.teaching_information_exchange.entity.CourseValidationForTeacher;

/**
 * Created by Administrator on 2015/12/17.
 */
public class FragmentCourseManager {
    private static FragmentCourseManager _FragmentCourseManager;
    private CommonBaseAdapter<CourseValidationForTeacher> adapter;
    private List<CourseValidationForTeacher> data = new ArrayList<>();
    private int page;
    private Context context;
    private FragmentCourseManager(Context context) {
        this.context = context;
        page = 0;
        initData();
    }
    public static FragmentCourseManager getInstance(Context context){
        if(_FragmentCourseManager==null)
        {
            _FragmentCourseManager=new FragmentCourseManager(context);
        }
        return _FragmentCourseManager;
    }
    public void initData(){
        adapter = new CommonBaseAdapter<CourseValidationForTeacher>(context,data, R.layout.sign_in_listview_item) {
            @Override
            public void convert(ViewHolder holder, CourseValidationForTeacher course) {
                TextView course_name = holder.getView(R.id.course_name);
                course_name.setText(course.getName());
                course_name.setTag(course);
            }
        };
    }

    public List<CourseValidationForTeacher> getData() {
        return data;
    }

    public void setData(List<CourseValidationForTeacher> data) {
        this.data = data;
    }

    public CommonBaseAdapter<CourseValidationForTeacher> getAdapter() {
        return adapter;
    }

    public void setAdapter(CommonBaseAdapter<CourseValidationForTeacher> adapter) {
        this.adapter = adapter;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
