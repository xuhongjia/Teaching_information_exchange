package cn.horry.teaching_information_exchange.adapter;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.entity.ValidationForStudent;
import cn.horry.teaching_information_exchange.utils.ImageUrlLoaderWithCache;
import cn.horry.teaching_information_exchange.widget.RoundCornerImageView;

/**
 * Created by horry on 2016/2/3.
 */
public class SignInStudentsAdapter extends CommonBaseAdapter<ValidationForStudent> {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
    public SignInStudentsAdapter(Context context, List<ValidationForStudent> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, ValidationForStudent validationForStudent) {
        holder.setText(R.id.id, validationForStudent.getuId() + "");
        ImageUrlLoaderWithCache.getInstence().ImageLoad(validationForStudent.getImg(), (RoundCornerImageView) holder.getView(R.id.img));
        holder.setText(R.id.validation_time,simpleDateFormat.format(validationForStudent.getValidate_time()));
        holder.setText(R.id.name,validationForStudent.getName());
    }
}
