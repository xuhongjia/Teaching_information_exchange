package cn.horry.teaching_information_exchange.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import org.kymjs.kjframe.ui.BindView;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.entity.Course;

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
    private static final int VIDEO_CONTENT_DESC_MAX_LINE = 4;// 默认展示最大行数3行
    private static final int SHOW_CONTENT_NONE_STATE = 0;// 扩充
    private static final int SHRINK_UP_STATE = 1;// 收起状态
    private static final int SPREAD_STATE = 2;// 展开状态
    private static int mState = SHRINK_UP_STATE;
    private Drawable up;
    private Drawable down;
    private Course course;
    @Override
    public void setRootView() {
        setContentView(R.layout.activity_add_sign);
    }

    @Override
    public void initData() {
        course = (Course)getIntent().getSerializableExtra("Course");
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
        down.setBounds(0,0,down.getMinimumWidth(),down.getMinimumHeight());
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
            case R.id.location:
                location_icon.setVisibility(View.GONE);
                location_progress.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    /**
     * 重新获取地址的progress
     * @param location
     */
    private void showLocationIcon(final boolean location){
        // 大于等于sdk 13的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            location_progress.setVisibility(location ? View.GONE : View.VISIBLE);
            location_progress.animate().setDuration(shortAnimTime).alpha(
                    location ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    validated.setVisibility(location ? View.GONE : View.VISIBLE);
                }
            });

            location_icon.setVisibility(location ? View.VISIBLE : View.GONE);
            location_icon.animate().setDuration(shortAnimTime).alpha(
                    location ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    validation.setVisibility(location ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            //不同sdk的设置
            location_icon.setVisibility(location ? View.VISIBLE : View.GONE);
            location_progress.setVisibility(location ? View.GONE : View.VISIBLE);
        }
    }
}
