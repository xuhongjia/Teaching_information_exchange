package cn.horry.teaching_information_exchange.adapter;

import android.content.Context;

import java.util.List;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.entity.Course;
import cn.horry.teaching_information_exchange.entity.User;

/**
 * Created by horry on 2016/2/3.
 */
public class SignInStudentsAdapter extends CommonBaseAdapter<User> {

    public SignInStudentsAdapter(Context context, List<User> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, User user) {
        holder.setText(R.id.id,user.getId()+"");
        holder.setText(R.id.name,user.getName());
    }
}
