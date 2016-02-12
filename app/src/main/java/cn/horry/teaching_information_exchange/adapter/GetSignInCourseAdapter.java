package cn.horry.teaching_information_exchange.adapter;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.entity.CourseValidation;

/**
 * Created by Myy on 2016/2/12.
 */
public class GetSignInCourseAdapter extends CommonBaseAdapter<CourseValidation> {
    public GetSignInCourseAdapter(Context context, List<CourseValidation> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, CourseValidation courseValidation) {
        TextView course_name = holder.getView(R.id.course_name);
        course_name.setText(courseValidation.getName());
        course_name.setTag(courseValidation);
    }
}
