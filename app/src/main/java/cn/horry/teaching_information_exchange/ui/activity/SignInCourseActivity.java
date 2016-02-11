package cn.horry.teaching_information_exchange.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.api.ValidationApi;
import cn.horry.teaching_information_exchange.entity.Course;
import cn.horry.teaching_information_exchange.entity.CourseValidation;
import cn.horry.teaching_information_exchange.entity.GeneralResponse;
import cn.horry.teaching_information_exchange.entity.ValidationForStudent;
import cn.horry.teaching_information_exchange.ui.UserManager;
import cn.horry.teaching_information_exchange.utils.GDMap;

public class SignInCourseActivity extends BaseActivity implements View.OnClickListener {

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
    @BindView(id = R.id.teacher_name)
    private TextView teacher_name;
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
    @BindView(id = R.id.sign_in_ing)
    private View sign_in_ing;
    private CourseValidation course;
    private static final int VIDEO_CONTENT_DESC_MAX_LINE = 4;// 默认展示最大行数3行
    private static final int SHOW_CONTENT_NONE_STATE = 0;// 扩充
    private static final int SHRINK_UP_STATE = 1;// 收起状态
    private static final int SPREAD_STATE = 2;// 展开状态
    private static int mState = SHRINK_UP_STATE;
    private int overTime = 10 * 60 * 1000;//5分钟
    private Drawable up;
    private Drawable down;
    private GDMap gdMap;
    private Double Sx;
    private Double Sy;
    @Override
    public void setRootView() {
        setContentView(R.layout.activity_sign_in_course);
    }
    @Override
    public void initData() {
        course = (CourseValidation)getIntent().getSerializableExtra("Course");
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
                Sx = aMapLocation.getLatitude();
                Sy = aMapLocation.getLongitude();
            }
        });
    }

    @Override
    public void initWidget() {
        title.setText("签到");
        course_title.setText(course.getName());
        content.setText(course.getContent());
        teacher_name.setText(course.getT_name());
        up = getResources().getDrawable(R.mipmap.detail_up);
        up.setBounds(0,0,up.getMinimumWidth(),up.getMinimumHeight());
        down = getResources().getDrawable(R.mipmap.detail_down);
        down.setBounds(0, 0, down.getMinimumWidth(), down.getMinimumHeight());
        left.setVisibility(View.VISIBLE);
        left.setText("");
        if(course.getValidation_state()>0)
        {
            showView(false, validation, validated);
        }
        else
        {
            showView(true, validation, validated);
        }
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
            case R.id.location_icon://获取位置
                location_icon.setVisibility(View.GONE);
                location_progress.setVisibility(View.VISIBLE);
                location.setText("正在获取地址...");
                location.setTextColor(Color.GRAY);
                gdMap.startLoction();
                break;
            case R.id.start_verify://提交
                SignIn();
                break;
            default:
                break;
        }
    }

    //签到
    private void SignIn(){
        showView(true,sign_in_ing,validation);
        if(checkValidation())
        {
            ValidationForStudent validationForStudent = new ValidationForStudent();
            if(System.currentTimeMillis()-course.getValidate_time()>overTime)
            {
                validationForStudent.setState(1);
            }else
            {
                validationForStudent.setState(0);
            }
            validationForStudent.setCode(verification_code.getText().toString().trim());
            validationForStudent.setValidate_time(System.currentTimeMillis());
            validationForStudent.setvId(course.getvId());
            validationForStudent.setS_x(Sx);
            validationForStudent.setS_y(Sy);
            validationForStudent.setuId(UserManager.getInstance().getUser().getId());
            ValidationApi.addValidationForStudent(validationForStudent, new HttpCallBack() {
                @Override
                public void onSuccess(String t) {
                    GeneralResponse<String> response = new Gson().fromJson(t,new TypeToken<GeneralResponse<String>>(){}.getType());
                    if(response.isSuccess())
                    {
                        showView(true,validated,sign_in_ing);
                    }
                }

                @Override
                public void onFailure(int errorNo, String strMsg) {
                    super.onFailure(errorNo, strMsg);
                    showView(false, sign_in_ing, validation);
                    showShortText("签到失败，请重试！");
                }
            });
        }
    }

    private boolean checkValidation(){
        if(verification_code.getText().toString().trim().length()>=4)
        {
            showShortText("请填写4位验证码！");
            return false;
        }
        LatLng startLatlng = new LatLng(course.getT_x(), course.getT_y());
        LatLng endLatlng = new LatLng(Sx, Sy);
        if(AMapUtils.calculateLineDistance(startLatlng, endLatlng)>5){
            showShortText("你不在教室中，不能进行签到！");
            return false;
        }
        return true;
    }
    /**
     * 显示哪个view
     * @param isValidated true为显示第一个view，false为显示第二个view
     * @param view1
     * @param view2
     */
    private void showView(final boolean isValidated , final View view1, final View view2){
        // 大于等于sdk 13的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            view2.setVisibility(isValidated ? View.GONE : View.VISIBLE);
            view2.animate().setDuration(shortAnimTime).alpha(
                    isValidated ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view2.setVisibility(isValidated ? View.GONE : View.VISIBLE);
                }
            });

            view1.setVisibility(isValidated ? View.VISIBLE : View.GONE);
            view1.animate().setDuration(shortAnimTime).alpha(
                    isValidated ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view1.setVisibility(isValidated ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            //不同sdk的设置
            view1.setVisibility(isValidated ? View.VISIBLE : View.GONE);
            view2.setVisibility(isValidated ? View.GONE : View.VISIBLE);
        }
    }
}
