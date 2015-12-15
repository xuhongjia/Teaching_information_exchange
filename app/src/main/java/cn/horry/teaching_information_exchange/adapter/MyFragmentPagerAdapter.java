package cn.horry.teaching_information_exchange.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.horry.teaching_information_exchange.ui.fragment.BaseFragment;

/**
 * Created by Administrator on 2015/12/15.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragmentsList;
    private FragmentManager fm;
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm=fm;
    }
    public MyFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragmentsList = fragments;
        this.fm = fm;
    }
    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }
    @Override
    public int getItemPosition(Object object) {
        //加此方法可以使viewpager可以进行刷新
        return PagerAdapter.POSITION_NONE;
    }
    public void setFragments(ArrayList<BaseFragment> fragments) {
        if(this.fragmentsList != null){
            FragmentTransaction ft = fm.beginTransaction();
            for(Fragment f:this.fragmentsList){
                ft.remove(f);
            }
            ft.commit();
            ft=null;
            fm.executePendingTransactions();
        }
        this.fragmentsList = fragments;
        notifyDataSetChanged();
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 注释自带的销毁方法防止页面被销毁
        //这个方法是重点
        // super.destroyItem(container, position, object);
        //
    }
}
