package cn.horry.teaching_information_exchange.adapter;


import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.entity.CourseFeedBack;
import cn.horry.teaching_information_exchange.utils.ImageUrlLoaderWithCache;
import cn.horry.teaching_information_exchange.widget.RoundCornerImageView;

/**
 * Created by Myy on 2016/2/21.
 */
public class FeedBackTeacherAdapter extends CommonBaseAdapter<CourseFeedBack> {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
    public FeedBackTeacherAdapter(Context context, List<CourseFeedBack> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, CourseFeedBack courseFeedBack) {
        holder.setText(R.id.id, courseFeedBack.getStudent_id() + "");
        ImageUrlLoaderWithCache.getInstence().ImageLoad(courseFeedBack.getStudent_img(), (RoundCornerImageView) holder.getView(R.id.img));
        holder.setText(R.id.validation_time,simpleDateFormat.format(courseFeedBack.getTime()));
        holder.setText(R.id.name,courseFeedBack.getStudent_name());
        holder.setText(R.id.content,courseFeedBack.getFeed_content());
    }
}
