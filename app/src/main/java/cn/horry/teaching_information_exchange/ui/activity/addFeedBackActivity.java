package cn.horry.teaching_information_exchange.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;


import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.api.FeedBackApi;
import cn.horry.teaching_information_exchange.entity.Course;
import cn.horry.teaching_information_exchange.entity.FeedBack;
import cn.horry.teaching_information_exchange.entity.GeneralResponse;
import cn.horry.teaching_information_exchange.ui.UserManager;

public class addFeedBackActivity extends BaseActivity implements View.OnClickListener {

    @BindView(id = R.id.left , click = true)
    private TextView left;
    @BindView(id = R.id.title)
    private TextView title;
    @BindView(id = R.id.course_content)
    private TextView course_content;
    @BindView(id = R.id.course_title)
    private TextView course_title;
    @BindView(id = R.id.show_more , click = true)
    private View show_more;
    @BindView(id = R.id.show_more_text)
    private TextView show_more_text;
    @BindView(id = R.id.content)
    private EditText content;
    @BindView(id = R.id.submit , click = true)
    private View submit;
    private Course course;
    @Override
    public void setRootView() {
        setContentView(R.layout.activity_add_feed_back);
    }
    @Override
    public void initData() {
        course = (Course) getIntent().getSerializableExtra("Course");
    }

    @Override
    public void initWidget() {
        title.setText("添加课程问题");
        left.setVisibility(View.VISIBLE);
        left.setText("");
        course_title.setText(course.getName());
        course_content.setText(course.getContent());
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
                    course_content.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
                    course_content.requestLayout();
                    show_more_text.setText("收起");
                    show_more_text.setCompoundDrawables(null,null,up,null);
                    mState = SHRINK_UP_STATE;
                } else if (mState == SHRINK_UP_STATE) {
                    course_content.setMaxLines(Integer.MAX_VALUE);
                    course_content.requestLayout();
                    show_more_text.setText("更多");
                    show_more_text.setCompoundDrawables(null,null,down,null);
                    mState = SPREAD_STATE;
                }
                break;
            case R.id.submit:
                FeedBack feedBack = new FeedBack();
                feedBack.setContent(content.getText().toString().trim());
                feedBack.setcId(course.getId());
                feedBack.setuId(UserManager.getInstance().getUser().getId());
                feedBack.setTime(System.currentTimeMillis());
                FeedBackApi.addFeedBack(feedBack, new HttpCallBack() {
                    @Override
                    public void onSuccess(String t) {
                        GeneralResponse<Integer> response = new Gson().fromJson(t, new TypeToken<GeneralResponse<Integer>>(){}.getType());
                        if(response.isSuccess())
                        {
                            showShortText("添加成功，请等待老师查看！");
                            finish();
                        }
                    }
                    @Override
                    public void onFailure(int errorNo, String strMsg) {
                        super.onFailure(errorNo, strMsg);
                        showShortText("添加失败，请查看网络状态！");
                    }
                });
                break;
            default:
                break;
        }
    }
}
