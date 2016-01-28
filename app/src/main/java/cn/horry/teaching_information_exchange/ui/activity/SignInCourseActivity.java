package cn.horry.teaching_information_exchange.ui.activity;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import org.kymjs.kjframe.ui.BindView;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.entity.Course;

public class SignInCourseActivity extends BaseActivity implements View.OnClickListener {

    @BindView(id = R.id.left ,click = true)
    private View left;
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
    private Course course;
    private static final int VIDEO_CONTENT_DESC_MAX_LINE = 4;// 默认展示最大行数3行
    private static final int SHOW_CONTENT_NONE_STATE = 0;// 扩充
    private static final int SHRINK_UP_STATE = 1;// 收起状态
    private static final int SPREAD_STATE = 2;// 展开状态
    private static int mState = SHRINK_UP_STATE;
    private Drawable up;
    private Drawable down;
    @Override
    public void setRootView() {
        setContentView(R.layout.activity_sign_in_course);
    }
    @Override
    public void initData() {
        course = (Course)getIntent().getSerializableExtra("Course");
    }

    @Override
    public void initWidget() {
        title.setText("签到");
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
//                    up.setVisibility(View.GONE);
//                    down.setVisibility(View.VISIBLE);
                    mState = SHRINK_UP_STATE;
                } else if (mState == SHRINK_UP_STATE) {
                    content.setMaxLines(Integer.MAX_VALUE);
                    content.requestLayout();
                    show_more_text.setText("更多");
                    show_more_text.setCompoundDrawables(null,null,down,null);
//                    up.setVisibility(View.VISIBLE);
//                    down.setVisibility(View.GONE);
                    mState = SPREAD_STATE;
                }
                break;
            default:
                break;
        }
    }
}
