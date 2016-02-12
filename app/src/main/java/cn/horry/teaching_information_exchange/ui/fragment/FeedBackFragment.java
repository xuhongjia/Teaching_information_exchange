package cn.horry.teaching_information_exchange.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;

import java.util.List;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.adapter.CommonBaseAdapter;
import cn.horry.teaching_information_exchange.adapter.ViewHolder;
import cn.horry.teaching_information_exchange.api.CourseApi;
import cn.horry.teaching_information_exchange.api.UserApi;
import cn.horry.teaching_information_exchange.entity.Course;
import cn.horry.teaching_information_exchange.entity.CourseValidation;
import cn.horry.teaching_information_exchange.entity.GeneralResponse;
import cn.horry.teaching_information_exchange.listener.PullRefreshListener;
import cn.horry.teaching_information_exchange.ui.FragmentCourseManager;
import cn.horry.teaching_information_exchange.ui.UserManager;
import cn.horry.teaching_information_exchange.ui.fragment.BaseFragment;
import cn.horry.teaching_information_exchange.widget.PullToRefreshLayout;
import cn.horry.teaching_information_exchange.widget.PullableListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedBackFragment extends BaseFragment {

    @BindView(id = R.id.sign_in_listview)
    PullableListView sign_in_listview;
    @BindView(id = R.id.sign_in_pull_to_refresh_layout)
    PullToRefreshLayout sign_in_pull_to_refresh_layout;
    private PullRefreshListener<CourseValidation> pullRefreshListener;
    private FragmentCourseManager fManager;
    public FeedBackFragment() {
        super();
    }
    @Override
    public View setRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_feed_back, container, false);
    }
    @Override
    public void onInitData() {
        fManager = FragmentCourseManager.getInstance(getmContext());
        if(fManager.getData().size()==0)
        {
            refreshData();
        }
        pullRefreshListener = new PullRefreshListener<CourseValidation>(fManager.getData(),fManager.getAdapter()) {
            //msg.what为100是加载最新，200为加载更多
            @Override
            public void updataRefresh(CommonBaseAdapter adapter, List<CourseValidation> data, Message msg, Handler handler) {
                handler.sendMessageDelayed(msg, 1500);
                fManager.setPage(0);
                refreshData();
            }

            @Override
            public void updataLoadMore(CommonBaseAdapter adapter, List<CourseValidation> data, Message msg, Handler handler) {
                handler.sendMessageDelayed(msg, 1500);
                fManager.setPage(fManager.getPage()+1);
                refreshData();
            }
        };
    }

    /**
     * 刷新数据
     */
    @Override
    public void refreshData() {
        int id = UserManager.getInstance().getUser().getId();
        int isTeacher = UserManager.getInstance().getUser().getIsTeacher();
        CourseApi.getValidationCourse(id, isTeacher, fManager.getPage(), new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                GeneralResponse<List<CourseValidation>> response = new Gson().fromJson(t, new TypeToken<GeneralResponse<List<CourseValidation>>>() {
                }.getType());
                if (response.isSuccess()) {
                    if (fManager.getPage() == 0) {
                        fManager.setData(response.getData());
                        fManager.getAdapter().setMdatas(fManager.getData());
                        fManager.getAdapter().notifyDataSetChanged();
                    } else if (response.getData().size() != 0) {
                        fManager.getData().addAll(response.getData());
                        fManager.getAdapter().notifyDataSetChanged();
                    } else {
                        fManager.setPage(fManager.getPage() - 1);
                    }
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
        sign_in_listview.setAdapter(fManager.getAdapter());
        sign_in_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course course = (Course)((ViewHolder) view.getTag()).getView(R.id.course_name).getTag();
                //跳转到详细界面
            }
        });
        sign_in_pull_to_refresh_layout.setOnRefreshListener(pullRefreshListener);
    }
}
