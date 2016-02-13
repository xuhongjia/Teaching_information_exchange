package cn.horry.teaching_information_exchange.adapter;

import android.content.Context;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.entity.CourseHomeWork;

/**
 * Created by Myy on 2016/2/13.
 */
public class GetHomeWorkCourseAdapter extends CommonBaseAdapter<CourseHomeWork> {
    SimpleDateFormat sFormat = new SimpleDateFormat("yy-MM-dd");
    public GetHomeWorkCourseAdapter(Context context, List<CourseHomeWork> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, CourseHomeWork courseHomeWork) {
        TextView course_name = holder.getView(R.id.course_name);
        holder.setText(R.id.add_time,sFormat.format(courseHomeWork.getTime()));
        course_name.setText(courseHomeWork.getName());
        course_name.setTag(courseHomeWork);
    }
}
