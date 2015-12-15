package cn.horry.teaching_information_exchange.ui.fragment.teacher;


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
public class HomeWorkFragment extends BaseFragment {


    public HomeWorkFragment() {
        super();
    }

    @Override
    public View setRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home_work, container, false);
    }

    @Override
    public void onInitView() {

    }

    @Override
    public void onInitData() {

    }
}
