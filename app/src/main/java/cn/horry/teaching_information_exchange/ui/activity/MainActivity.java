package cn.horry.teaching_information_exchange.ui.activity;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
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
    @BindView(id = R.id.sign_in)
    private RadioButton sign_in;
    @BindView(id = R.id.homework)
    private RadioButton homework;
    @BindView(id = R.id.feedback)
    private RadioButton feedback;
    private ViewPager.OnPageChangeListener changeListener;
    private boolean isTeacher;
    private List<BaseFragment> fragmentList ;
    private int currIndex = 0;
    private int bottomLineWidth;
    private int offset = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnnotateUtil.initBindView(this);
        InitWidth();
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
                int one = offset * 2 + bottomLineWidth;// 页卡1 -> 页卡2 偏移量
                Animation animation = new TranslateAnimation(one * currIndex, one * position, 0, 0);// 显然这个比较简洁，只有一行代码。
                currIndex = position;
                animation.setFillAfter(true);
                animation.setDuration(100);

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
        viewPager.setCurrentItem(0, false);
        viewPager.setOnPageChangeListener(changeListener);
        menu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.sign_in:
                        viewPager.setCurrentItem(0, false);
                        break;
                    case R.id.homework:
                        viewPager.setCurrentItem(1, false);
                        break;
                    case R.id.feedback:
                        viewPager.setCurrentItem(2, false);
                        break;
                    default:
                        break;
                }
            }
        });
    }
    private void InitWidth() {
        int screenWidth;// 屏幕宽度 如果是使用图片可以使用另一篇文章的宽度计算方法
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        screenWidth = display.getWidth();

        bottomLineWidth = screenWidth / 3;



        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 3 - bottomLineWidth) / 2;// 计算偏移量

        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);

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
