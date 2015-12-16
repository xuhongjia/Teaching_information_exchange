package cn.horry.teaching_information_exchange.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.ui.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedBackFragment extends BaseFragment {


    public FeedBackFragment() {
        super();
    }

    @Override
    public View setRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_feed_back, container, false);
    }

    @Override
    public void onInitView() {

    }

    @Override
    public void onInitData() {

    }
}
