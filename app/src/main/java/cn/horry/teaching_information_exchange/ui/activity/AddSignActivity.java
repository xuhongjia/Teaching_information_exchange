package cn.horry.teaching_information_exchange.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;

import java.util.Random;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.api.ValidationApi;
import cn.horry.teaching_information_exchange.entity.Course;
import cn.horry.teaching_information_exchange.entity.Validation;
import cn.horry.teaching_information_exchange.ui.UserManager;
import cn.horry.teaching_information_exchange.utils.GDMap;

public class AddSignActivity extends BaseActivity implements View.OnClickListener {
    @BindView(id = R.id.left ,click = true)
    private TextView left;
    @BindView(id = R.id.title)
    private TextView title;
    @BindView(id = R.id.content)
    private TextView content;
    @BindView(id = R.id.course_title)
    private TextView course_title;
    @BindView(id = R.id.show_more , click = true)
    private View show_more;
    @BindView(id = R.id.show_more_text)
    private TextView show_more_text;
    @BindView(id = R.id.location_icon , click = true)
    private View location_icon;
    @BindView(id = R.id.location)
    private AutoCompleteTextView location;
    @BindView(id = R.id.verification_code)
    private AutoCompleteTextView verification_code;
    @BindView(id = R.id.start_verify , click = true)
    private View start_verify;
    @BindView(id = R.id.validated)
    private View validated;
    @BindView(id = R.id.validation)
    private View validation;
    @BindView(id = R.id.location_progress)
    private View location_progress;
    @BindView(id = R.id.create_validation , click = true)
    private View create_validation;
    private static final int VIDEO_CONTENT_DESC_MAX_LINE = 4;// 默认展示最大行数3行
    private static final int SHOW_CONTENT_NONE_STATE = 0;// 扩充
    private static final int SHRINK_UP_STATE = 1;// 收起状态
    private static final int SPREAD_STATE = 2;// 展开状态
    private static int mState = SHRINK_UP_STATE;
    private Drawable up;
    private Drawable down;
    private Course course;
    private GDMap gdMap;
    private Double Tx;
    private Double Ty;
    @Override
    public void setRootView() {
        setContentView(R.layout.activity_add_sign);
    }

    @Override
    public void initData() {
        course = (Course)getIntent().getSerializableExtra("Course");
        gdMap = GDMap.getInstance(this);
        gdMap.setLoactionListener(new GDMap.LocationListener() {
            @Override
            public void getAddressString(String address) {
                location.setText(address.trim());
                location.setTextColor(Color.BLACK);
                location_icon.setVisibility(View.VISIBLE);
                location_progress.setVisibility(View.GONE);
            }

            @Override
            public void getAMapLocation(AMapLocation aMapLocation) {
                Tx = aMapLocation.getLatitude();
                Ty = aMapLocation.getLongitude();
            }
        });
    }

    @Override
    public void initWidget() {
        title.setText("添加签到信息");
        left.setVisibility(View.VISIBLE);
        left.setText("");
        course_title.setText(course.getName());
        content.setText(course.getContent());
        up = getResources().getDrawable(R.mipmap.detail_up);
        up.setBounds(0,0,up.getMinimumWidth(),up.getMinimumHeight());
        down = getResources().getDrawable(R.mipmap.detail_down);
        down.setBounds(0, 0, down.getMinimumWidth(), down.getMinimumHeight());
        gdMap.startLoction();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.left:
                finish();
                break;
            case R.id.show_more:
                if (mState == SPREAD_STATE) {
                    content.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
                    content.requestLayout();
                    show_more_text.setText("收起");
                    show_more_text.setCompoundDrawables(null,null,up,null);
                    mState = SHRINK_UP_STATE;
                } else if (mState == SHRINK_UP_STATE) {
                    content.setMaxLines(Integer.MAX_VALUE);
                    content.requestLayout();
                    show_more_text.setText("更多");
                    show_more_text.setCompoundDrawables(null,null,down,null);
                    mState = SPREAD_STATE;
                }
                break;
            case R.id.location_icon:
                location_icon.setVisibility(View.GONE);
                location_progress.setVisibility(View.VISIBLE);
                location.setText("正在获取地址...");
                location.setTextColor(Color.GRAY);
                gdMap.startLoction();
                break;
            case R.id.create_validation:
                verification_code.setText(getValidation());
                break;
            case R.id.start_verify:
                addValidation();
                break;
            default:
                break;
        }
    }

    private void addValidation(){
        if(check())
        {
            Validation validation = new Validation();
            validation.setCode(verification_code.getText().toString().trim());
            validation.setState(0);
            validation.setcId(course.getId());
            validation.setValidate_time(System.currentTimeMillis());
            validation.setT_x(Tx);
            validation.setT_y(Ty);
            validation.setuId(UserManager.getInstance().getUser().getId());
            ValidationApi.addValidation(validation, new HttpCallBack() {
                @Override
                public void onSuccess(String t) {
                    super.onSuccess(t);
                    finish();
                    showShortText("添加成功");
                }

                @Override
                public void onFailure(int errorNo, String strMsg) {
                    super.onFailure(errorNo, strMsg);

                }
            });
        }
    }
    private boolean check(){
        if(verification_code.getText().toString().trim().equals(""))
        {
            return false;
        }
        return true;
    }

    public String getValidation(){
        int x;//定义两变量
        Random ne=new Random();//实例化一个random的对象ne
        x=ne.nextInt(9999-1000+1)+1000;//为变量赋随机值1000-9999
        return x+"";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gdMap.stopLoction();
    }
}
