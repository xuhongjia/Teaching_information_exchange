package cn.horry.teaching_information_exchange.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


import org.kymjs.kjframe.ui.BindView;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.adapter.ViewHolder;
import cn.horry.teaching_information_exchange.entity.CourseValidation;
import cn.horry.teaching_information_exchange.ui.UserManager;
import cn.horry.teaching_information_exchange.ui.activity.SignInCourseActivity;
import cn.horry.teaching_information_exchange.ui.activity.SignInCourseTeacherActivity;
import cn.horry.teaching_information_exchange.widget.PullToRefreshLayout;
import cn.horry.teaching_information_exchange.widget.PullableListView;

public class SignInFragment extends BaseFragment {

    @BindView(id = R.id.sign_in_listview)
    PullableListView sign_in_listview;
    @BindView(id = R.id.sign_in_pull_to_refresh_layout)
    PullToRefreshLayout sign_in_pull_to_refresh_layout;

    public SignInFragment() {
        super();
    }
    @Override
    public View setRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }
    @Override
    public void onInitData() {
       if(getfManager().getData().size()==0)
       {
           refreshData();
       }
    }

    @Override
    public void onInitView() {
        sign_in_listview.setAdapter(getfManager().getAdapter());
        sign_in_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CourseValidation course = (CourseValidation) ((ViewHolder) view.getTag()).getView(R.id.course_name).getTag();
                //跳转到详细界面
                Bundle bundle = new Bundle();
                bundle.putSerializable("Course", course);
                Intent intent;
                if (UserManager.getInstance().getUser().getIsTeacher() == 1) {
                    intent = new Intent(getmContext(), SignInCourseTeacherActivity.class);
                } else {
                    intent = new Intent(getmContext(), SignInCourseActivity.class);
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        sign_in_pull_to_refresh_layout.setOnRefreshListener(getPullRefreshListener());
    }

}
