package cn.horry.teaching_information_exchange.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.kymjs.kjframe.ui.AnnotateUtil;
import org.kymjs.kjframe.ui.BindView;

import java.util.ArrayList;
import java.util.List;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.adapter.MyFragmentPagerAdapter;
import cn.horry.teaching_information_exchange.ui.UserManager;
import cn.horry.teaching_information_exchange.ui.fragment.BaseFragment;
import cn.horry.teaching_information_exchange.ui.fragment.student.FeedBackStudentFragment;
import cn.horry.teaching_information_exchange.ui.fragment.student.HomeWorkStudentFragment;
import cn.horry.teaching_information_exchange.ui.fragment.student.SignInStudentFragment;
import cn.horry.teaching_information_exchange.ui.fragment.teacher.FeedBackFragment;
import cn.horry.teaching_information_exchange.ui.fragment.teacher.HomeWorkFragment;
import cn.horry.teaching_information_exchange.ui.fragment.teacher.SignInFragment;
import cn.horry.teaching_information_exchange.widget.MyViewPager;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(id = R.id.menu )
    private RadioGroup menu;
    @BindView(id = R.id.viewPager)
    private MyViewPager viewPager; //滑动切换Fragment
    private List<BaseFragment> fragmentList ;
    @BindView(id = R.id.sign_in)
    private RadioButton sign_in;
    @BindView(id = R.id.homework)
    private RadioButton homework;
    @BindView(id = R.id.feedback)
    private RadioButton feedback;
    private ViewPager.OnPageChangeListener changeListener;
    private boolean isTeacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnnotateUtil.initBindView(this);
        initData();
        initWidget();
    }

    /**
     * 初始化控件
     */
    public void initWidget(){
        initFragmentList();
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        changeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position)
                {
                    case 0:
                        sign_in.setChecked(true);
                        break;
                    case 1:
                        homework.setChecked(true);
                        break;
                    case 2:
                        feedback.setChecked(true);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(0,false);
        viewPager.setOnPageChangeListener(changeListener);
        menu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.sign_in:
                        viewPager.setCurrentItem(0,false);
                        break;
                    case R.id.homework:
                        viewPager.setCurrentItem(1,false);
                        break;
                    case R.id.feedback:
                        viewPager.setCurrentItem(2,false);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 初始化FragmentList
     */
    public void initFragmentList(){
        fragmentList = new ArrayList<BaseFragment>();
        if(isTeacher)
        {
            fragmentList.add(new SignInFragment());
            fragmentList.add(new HomeWorkFragment());
            fragmentList.add(new FeedBackFragment());
        }
        else
        {
            fragmentList.add(new SignInStudentFragment());
            fragmentList.add(new HomeWorkStudentFragment());
            fragmentList.add(new FeedBackStudentFragment());
        }
    }

    /**
     * 初始化数据
     */
    public void initData(){
        if(UserManager.getInstance().getUser().getIsTeacher()==1)
        {
            isTeacher = true;
        }else
        {
            isTeacher = false;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.menu:
                break;
            default:
                break;
        }
    }
}
