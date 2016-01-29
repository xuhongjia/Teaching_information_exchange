package cn.horry.teaching_information_exchange.ui.activity;

import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.kymjs.kjframe.ui.AnnotateUtil;
import org.kymjs.kjframe.ui.BindView;

import java.util.ArrayList;
import java.util.List;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.adapter.MyFragmentPagerAdapter;
import cn.horry.teaching_information_exchange.ui.UserManager;
import cn.horry.teaching_information_exchange.ui.fragment.BaseFragment;
import cn.horry.teaching_information_exchange.ui.fragment.FeedBackFragment;
import cn.horry.teaching_information_exchange.ui.fragment.HomeWorkFragment;
import cn.horry.teaching_information_exchange.ui.fragment.SignInFragment;
import cn.horry.teaching_information_exchange.utils.ImageUrlLoaderWithCache;
import cn.horry.teaching_information_exchange.widget.MyViewPager;
import cn.horry.teaching_information_exchange.widget.RoundCornerImageView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
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
    @BindView(id = R.id.toolbar)
    Toolbar toolbar;
    @BindView(id = R.id.drawer_layout)
    private DrawerLayout drawer;
    @BindView(id = R.id.nav_view)
    private NavigationView navigationView;
    /**
     * nav_header_main
     */
    private RoundCornerImageView myImageView;
    private TextView myTextView;
    private ViewPager.OnPageChangeListener changeListener;
    private boolean isTeacher;
    private List<BaseFragment> fragmentList ;
    private int currIndex = 0;
    private int bottomLineWidth;
    private int offset = 0;
    private int screenW;
    private int screenH;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_main);
    }

    /**
     * 初始化控件
     */
    public void initWidget(){
        initFragmentList();
        addhead();
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
                        currIndex = 0;
                        break;
                    case R.id.homework:
                        viewPager.setCurrentItem(1, false);
                        currIndex = 1;
                        break;
                    case R.id.feedback:
                        viewPager.setCurrentItem(2, false);
                        currIndex = 2;
                        break;
                    default:
                        break;
                }
            }
        });
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * 初始化navigation的头部
     */
    private void addhead(){
        //动态添加头布局，因为静态头布局无法引入里面的控件
        View view = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT));
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.nav_header);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                screenH / 3));
        myImageView = (RoundCornerImageView) relativeLayout.findViewById(R.id.imageView);
        ImageUrlLoaderWithCache.getInstence().ImageLoad(UserManager.getInstance().getUser().getImg(), myImageView);
        myTextView = (TextView)relativeLayout.findViewById(R.id.name);
        myTextView.setText(UserManager.getInstance().getUser().getName());
        navigationView.addHeaderView(view);
        myImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MyInforActivity.class));
            }
        });
    }

    /**
     * 初始化屏幕大小
     */
    private void InitWidth() {
        int screenWidth;
        // 屏幕宽度 如果是使用图片可以使用另一篇文章的宽度计算方法
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        screenWidth = display.getWidth();
        bottomLineWidth = screenWidth / 3;

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenW = dm.widthPixels;// 获取分辨率宽度
        screenH =dm.heightPixels;
        offset = (screenW / 3 - bottomLineWidth) / 2;// 计算偏移量

        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);

    }
    /**
     * 初始化FragmentList
     */
    public void initFragmentList(){
        fragmentList = new ArrayList<BaseFragment>();
        fragmentList.add(new SignInFragment());
        fragmentList.add(new HomeWorkFragment());
        fragmentList.add(new FeedBackFragment());
    }

    /**
     * 初始化数据
     */
    public void initData(){
        InitWidth();
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
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if(UserManager.getInstance().getUser().getIsTeacher()==1)
        {
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 右侧按钮点击事件，不需要
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (currIndex)
        {
            case 0://签到

                break;
            case 1://作业
                break;
            case 2://反馈信息
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * navigation的item点击事件
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.my_sign_in) {

        } else if (id == R.id.my_home_work) {

        } else if (id == R.id.my_feed_back) {

        } else if (id == R.id.login_out) {
            UserManager.getInstance().setUser(null);
            finish();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
