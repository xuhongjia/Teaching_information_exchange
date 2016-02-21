package cn.horry.teaching_information_exchange.ui.activity;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.adapter.FeedBackTeacherAdapter;
import cn.horry.teaching_information_exchange.api.FeedBackApi;
import cn.horry.teaching_information_exchange.entity.CourseFeedBack;
import cn.horry.teaching_information_exchange.entity.GeneralResponse;

public class FeedBackTeacherActivity extends BaseActivity implements View.OnClickListener{
    @BindView(id = R.id.left , click = true)
    private TextView left;
    @BindView(id = R.id.title)
    private TextView title;
    @BindView(id = R.id.course_title)
    private TextView course_title;
    @BindView(id = R.id.time)
    private TextView time;
    @BindView(id = R.id.feed_back_list_view)
    private ListView feed_back_list_view;
    private CourseFeedBack courseFeedBack;
    private List<CourseFeedBack> data = new ArrayList<>();
    private FeedBackTeacherAdapter adapter;
    @Override
    public void setRootView() {
        setContentView(R.layout.activity_feed_back_teacher);
    }

    @Override
    public void initData() {
        adapter = new FeedBackTeacherAdapter(this,data ,R.layout.feed_back_students_list_item);
        courseFeedBack = (CourseFeedBack) getIntent().getSerializableExtra("course");
        FeedBackApi.getFeedBackForTeacher(courseFeedBack.getId(), new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                GeneralResponse<List<CourseFeedBack>> response = new Gson().fromJson(t , new TypeToken<GeneralResponse<List<CourseFeedBack>>>(){}.getType());
                if (response.isSuccess())
                {
                    data = response.getData();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
            }
        });
    }

    @Override
    public void initWidget() {
        left.setText("");
        left.setVisibility(View.VISIBLE);
        title.setText("课程反馈");
        course_title.setText(courseFeedBack.getName());
        time.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(courseFeedBack.getTime()));
        feed_back_list_view.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.left:
                finish();
                break;
            default:
                break;
        }
    }
}
