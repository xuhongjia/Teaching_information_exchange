package cn.horry.teaching_information_exchange.ui.fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;

import java.util.ArrayList;
import java.util.List;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.adapter.CommonBaseAdapter;
import cn.horry.teaching_information_exchange.adapter.ViewHolder;
import cn.horry.teaching_information_exchange.api.UserApi;
import cn.horry.teaching_information_exchange.entity.Course;
import cn.horry.teaching_information_exchange.entity.GeneralResponse;
import cn.horry.teaching_information_exchange.listener.PullRefreshListener;
import cn.horry.teaching_information_exchange.ui.UserManager;
import cn.horry.teaching_information_exchange.ui.fragment.BaseFragment;
import cn.horry.teaching_information_exchange.widget.PullToRefreshLayout;
import cn.horry.teaching_information_exchange.widget.PullableListView;

public class SignInFragment extends BaseFragment {

    @BindView(id = R.id.sign_in_listview)
    PullableListView sign_in_listview;
    @BindView(id = R.id.sign_in_pull_to_refresh_layout)
    PullToRefreshLayout sign_in_pull_to_refresh_layout;
    private List<Course> data;
    private CommonBaseAdapter<Course> adapter;
    public SignInFragment() {
        super();
    }
    @Override
    public View setRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onInitData() {
        data = new ArrayList<>();
        int id = UserManager.getInstance().getUser().getId();
        int isTeacher = UserManager.getInstance().getUser().getIsTeacher();
        UserApi.getCourse(id, isTeacher, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                GeneralResponse<List<Course>> response = new Gson().fromJson(t,new TypeToken<GeneralResponse<List<Course>>>(){}.getType());
                if(response.isSuccess())
                {
                    data = response.getData();
                    onInitView();
                }
            }
            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
            }
        });
    }

    @Override
    public void onInitView() {
        adapter = new CommonBaseAdapter<Course>(getmContext(),data,R.layout.sign_in_listview_item) {
            @Override
            public void convert(ViewHolder holder, Course course) {
                TextView course_name = holder.getView(R.id.course_name);
                course_name.setText(course.getName());
                course_name.setTag(course);
            }
        };
        sign_in_listview.setAdapter(adapter);
        sign_in_pull_to_refresh_layout.setOnRefreshListener(new PullRefreshListener<Course>(data,adapter) {
            //msg.what为100是加载最新，200为加载更多
            @Override
            public void updataRefresh(CommonBaseAdapter adapter, List<Course> data, Message msg, Handler handler) {
                handler.sendMessageDelayed(msg,1500);
            }

            @Override
            public void updataLoadMore(CommonBaseAdapter adapter, List<Course> data, Message msg, Handler handler) {
                handler.sendMessageDelayed(msg,1500);
            }
        });
    }

}
