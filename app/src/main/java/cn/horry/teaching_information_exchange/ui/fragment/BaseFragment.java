package cn.horry.teaching_information_exchange.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.kymjs.kjframe.ui.AnnotateUtil;

import cn.horry.teaching_information_exchange.ui.activity.BaseActivity;

/**
 * Created by Administrator on 2015/12/15.
 */
public abstract class BaseFragment extends Fragment {
    private BaseActivity mContext = null;
    private View rootView;
    private boolean isInit;
    public BaseFragment(){
    }
    //视图创建完成
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = (BaseActivity) getActivity();
        onInitData();
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
    public abstract void refreshData();
    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }
}
