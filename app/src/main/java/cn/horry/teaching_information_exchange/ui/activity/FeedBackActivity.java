package cn.horry.teaching_information_exchange.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;

import java.text.SimpleDateFormat;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.api.FeedBackApi;
import cn.horry.teaching_information_exchange.entity.CourseFeedBack;
import cn.horry.teaching_information_exchange.entity.GeneralResponse;

public class FeedBackActivity extends BaseActivity implements View.OnClickListener{
    @BindView(id = R.id.left , click = true)
    private TextView left;
    @BindView(id = R.id.title)
    private TextView title;
    @BindView(id = R.id.course_title)
    private TextView course_title;
    @BindView(id = R.id.time)
    private TextView time;
    @BindView(id = R.id.content)
    private TextView content;
    @BindView(id = R.id.teacher_name)
    private TextView teacher_name;
    private CourseFeedBack course;
    @Override
    public void setRootView() {
        setContentView(R.layout.activity_feed_back);
    }

    @Override
    public void initData() {
        course = (CourseFeedBack) getIntent().getSerializableExtra("course");
    }

    @Override
    public void initWidget() {
        left.setText("");
        left.setVisibility(View.VISIBLE);
        title.setText("课程反馈");
        course_title.setText(course.getName());
        time.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(course.getTime()));
        content.setText(course.getFeed_content());
        teacher_name.setText(course.getT_name());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.left:
                finish();
                break;
            case R.id.save:
                FeedBackApi.updateFeedBack(content.getText().toString().trim(), course.getfId(), new HttpCallBack() {
                    @Override
                    public void onSuccess(String t) {
                        GeneralResponse<Integer> response = new Gson().fromJson(t, new TypeToken<GeneralResponse<Integer>>(){}.getType());
                        if (response.isSuccess())
                        {
                            showShortText("更新成功，返回课程反馈列表！");
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(int errorNo, String strMsg) {
                        super.onFailure(errorNo, strMsg);
                        showShortText("更新失败，请检查网络状态！");
                    }
                });
                break;
            default:
                break;
        }
    }
}
