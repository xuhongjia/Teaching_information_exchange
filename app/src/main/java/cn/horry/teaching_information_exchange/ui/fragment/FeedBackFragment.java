package cn.horry.teaching_information_exchange.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;

import java.util.ArrayList;
import java.util.List;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.adapter.CommonBaseAdapter;
import cn.horry.teaching_information_exchange.adapter.GetFeedBackCourseAdapter;
import cn.horry.teaching_information_exchange.adapter.GetHomeWorkCourseAdapter;
import cn.horry.teaching_information_exchange.adapter.ViewHolder;
import cn.horry.teaching_information_exchange.api.CourseApi;
import cn.horry.teaching_information_exchange.api.FeedBackApi;
import cn.horry.teaching_information_exchange.api.UserApi;
import cn.horry.teaching_information_exchange.entity.Course;
import cn.horry.teaching_information_exchange.entity.CourseFeedBack;
import cn.horry.teaching_information_exchange.entity.CourseValidation;
import cn.horry.teaching_information_exchange.entity.GeneralResponse;
import cn.horry.teaching_information_exchange.listener.PullRefreshListener;
import cn.horry.teaching_information_exchange.ui.FragmentCourseManager;
import cn.horry.teaching_information_exchange.ui.UserManager;
import cn.horry.teaching_information_exchange.ui.activity.FeedBackActivity;
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
    private PullRefreshListener<CourseFeedBack> pullRefreshListener;
    private List<CourseFeedBack> data = new ArrayList<>();
    private GetFeedBackCourseAdapter adapter;
    private int pager=0;
    public FeedBackFragment() {
        super();
    }
    @Override
    public View setRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_feed_back, container, false);
    }
    @Override
    public void onInitData() {
        adapter = new GetFeedBackCourseAdapter(getmContext(),data,R.layout.listview_item);
        pullRefreshListener = new PullRefreshListener<CourseFeedBack>(data,adapter) {
            //msg.what为100是加载最新，200为加载更多
            @Override
            public void updataRefresh(CommonBaseAdapter adapter, List<CourseFeedBack> data, Message msg, Handler handler) {
                handler.sendMessageDelayed(msg, 1500);
                pager=0;
                refreshData();
            }

            @Override
            public void updataLoadMore(CommonBaseAdapter adapter, List<CourseFeedBack> data, Message msg, Handler handler) {
                handler.sendMessageDelayed(msg, 1500);
                pager+=1;
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
        if(isTeacher==0)
        {
            FeedBackApi.getFeedBackForStudent(id, pager, new HttpCallBack() {
                @Override
                public void onSuccess(String t) {
                    super.onSuccess(t);
                    GeneralResponse<List<CourseFeedBack>> response = new Gson().fromJson(t, new TypeToken<GeneralResponse<List<CourseFeedBack>>>(){}.getType());
                    if (response.isSuccess()) {
                        if (pager == 0) {
                            adapter.setMdatas(response.getData());
                            adapter.notifyDataSetChanged();
                        } else if (response.getData().size() != 0) {
                            data.addAll(response.getData());
                            adapter.notifyDataSetChanged();
                        } else {
                            pager-=1;
                        }
                    }
                }

                @Override
                public void onFailure(int errorNo, String strMsg) {
                    super.onFailure(errorNo, strMsg);
                }
            });
        }else {
            UserApi.getCourse(id, isTeacher, new HttpCallBack() {
                @Override
                public void onSuccess(String t) {
                    GeneralResponse<List<CourseFeedBack>> response = new Gson().fromJson(t,new TypeToken<GeneralResponse<List<CourseFeedBack>>>(){}.getType());
                    if (response.isSuccess()) {
                        if (pager == 0) {
                            adapter.setMdatas(response.getData());
                            adapter.notifyDataSetChanged();
                        } else if (response.getData().size() != 0) {
                            data.addAll(response.getData());
                            adapter.notifyDataSetChanged();
                        } else {
                            pager-=1;
                        }
                    }
                }

                @Override
                public void onFailure(int errorNo, String strMsg) {
                    super.onFailure(errorNo, strMsg);
                }
            });
        }

    }

    @Override
    public void onInitView() {
        sign_in_listview.setAdapter(adapter);
        sign_in_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CourseFeedBack course = (CourseFeedBack) ((ViewHolder) view.getTag()).getView(R.id.course_name).getTag();
                //跳转到详细界面
                Intent intent;
                Bundle bundle = new Bundle();
                bundle.putSerializable("course", course);
                if (UserManager.getInstance().getUser().getIsTeacher() == 0) {
                    intent = new Intent(getmContext(), FeedBackActivity.class);
                } else {
                    intent = new Intent(getmContext(), FeedBackActivity.class);
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        sign_in_pull_to_refresh_layout.setOnRefreshListener(pullRefreshListener);
    }
}
