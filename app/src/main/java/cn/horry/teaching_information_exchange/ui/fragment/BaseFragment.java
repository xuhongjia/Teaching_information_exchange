package cn.horry.teaching_information_exchange.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.AnnotateUtil;

import java.util.ArrayList;
import java.util.List;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.adapter.CommonBaseAdapter;
import cn.horry.teaching_information_exchange.adapter.ViewHolder;
import cn.horry.teaching_information_exchange.api.UserApi;
import cn.horry.teaching_information_exchange.entity.Course;
import cn.horry.teaching_information_exchange.entity.GeneralResponse;
import cn.horry.teaching_information_exchange.listener.PullRefreshListener;
import cn.horry.teaching_information_exchange.ui.FragmentCourseManager;
import cn.horry.teaching_information_exchange.ui.UserManager;
import cn.horry.teaching_information_exchange.ui.activity.BaseActivity;

/**
 * Created by Administrator on 2015/12/15.
 */
public abstract class BaseFragment extends Fragment {
    private BaseActivity mContext = null;
    private View rootView;
    private boolean isInit;
    private PullRefreshListener<Course> pullRefreshListener;
    private FragmentCourseManager fManager;
    public BaseFragment(){
    }
    //视图创建完成
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = (BaseActivity) getActivity();
        fManager = FragmentCourseManager.getInstance(mContext);
        onInitData();
        pullRefreshListener = new PullRefreshListener<Course>(fManager.getData(),fManager.getAdapter()) {
            //msg.what为100是加载最新，200为加载更多
            @Override
            public void updataRefresh(CommonBaseAdapter adapter, List<Course> data, Message msg, Handler handler) {
                handler.sendMessageDelayed(msg, 1500);
                fManager.setPage(0);
                refreshData();
            }

            @Override
            public void updataLoadMore(CommonBaseAdapter adapter, List<Course> data, Message msg, Handler handler) {
                handler.sendMessageDelayed(msg, 1500);
                fManager.setPage(fManager.getPage()+1);
                refreshData();
            }
        };
        onInitView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = setRootView(inflater,container);
        AnnotateUtil.initBindView(this, rootView);
        return rootView;
    }
    public abstract View setRootView(LayoutInflater inflater, ViewGroup container);
    /**
     * 在异步中完成后执行
     */
    public abstract void onInitView();

    /**
     * 先执行
     */
    public abstract void onInitData();
    @Override
    public void onResume() {
        super.onResume();
        // 判断当前fragment是否显示
        if (getUserVisibleHint()) {
            isInit = true;
            showData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    //界面重新显示出来
    public void onActive(){

    }

    public BaseActivity getmContext() {
        return mContext;
    }

    public void setmContext(BaseActivity mContext) {
        this.mContext = mContext;
    }
    /**
     * 初始化数据
     * @author horry
     * @date 2015/12/15
     */
    private void showData() {
        if (isInit) {
            isInit = false;//加载数据完成
            // 加载各种数据
            refreshData();
        }
    }
    /**
     * 数据刷新
     */
    public void refreshData(){
        int id = UserManager.getInstance().getUser().getId();
        int isTeacher = UserManager.getInstance().getUser().getIsTeacher();
        UserApi.getCourse(id, isTeacher,fManager.getPage(), new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                GeneralResponse<List<Course>> response = new Gson().fromJson(t, new TypeToken<GeneralResponse<List<Course>>>() {
                }.getType());
                if (response.isSuccess()) {
                    if(fManager.getPage()==0) {
                        fManager.setData(response.getData());
                        fManager.getAdapter().setMdatas(fManager.getData());
                        fManager.getAdapter().notifyDataSetChanged();
                    }else if(response.getData().size()!=0)
                    {
                        fManager.getData().addAll(response.getData());
                        fManager.getAdapter().notifyDataSetChanged();
                    }
                    else
                    {
                        fManager.setPage(fManager.getPage()-1);
                    }
                }
            }
            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
            }
        });
    }
    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }


    public PullRefreshListener<Course> getPullRefreshListener() {
        return pullRefreshListener;
    }

    public void setPullRefreshListener(PullRefreshListener<Course> pullRefreshListener) {
        this.pullRefreshListener = pullRefreshListener;
    }

    public FragmentCourseManager getfManager() {
        return fManager;
    }

    public void setfManager(FragmentCourseManager fManager) {
        this.fManager = fManager;
    }
}
