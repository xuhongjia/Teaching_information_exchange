package cn.horry.teaching_information_exchange.adapter;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.entity.Course;

/**
 * Created by horry on 2016/2/3.
 */
public class SelectCourseAdapter extends CommonBaseAdapter<Course> {

    public SelectCourseAdapter(Context context, List<Course> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, Course course) {
        TextView course_name = holder.getView(R.id.course_name);
        course_name.setText(course.getName());
        course_name.setTag(course);
    }
}
