package cn.horry.teaching_information_exchange.adapter;

import android.content.Context;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.entity.CourseFeedBack;

/**
 * Created by Myy on 2016/2/21.
 */
public class GetFeedBackCourseAdapter extends CommonBaseAdapter<CourseFeedBack> {
    SimpleDateFormat sFormat = new SimpleDateFormat("yy-MM-dd");
    public GetFeedBackCourseAdapter(Context context, List<CourseFeedBack> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, CourseFeedBack courseFeedBack) {
        TextView course_name = holder.getView(R.id.course_name);
        holder.setText(R.id.add_time,sFormat.format(courseFeedBack.getTime()));
        course_name.setText(courseFeedBack.getName());
        course_name.setTag(courseFeedBack);
    }
}
