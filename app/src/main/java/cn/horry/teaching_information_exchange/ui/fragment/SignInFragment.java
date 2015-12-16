package cn.horry.teaching_information_exchange.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.kymjs.kjframe.ui.BindView;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.ui.fragment.BaseFragment;
import cn.horry.teaching_information_exchange.widget.PullableListView;

public class SignInFragment extends BaseFragment {

    @BindView(id = R.id.sign_in_listview)
    PullableListView sign_in_listview;
    public SignInFragment() {
        super();
    }
    @Override
    public View setRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onInitData() {

    }

    @Override
    public void onInitView() {

    }

}
