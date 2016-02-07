package cn.horry.teaching_information_exchange.ui.activity;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;

import java.text.SimpleDateFormat;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.api.ValidationApi;
import cn.horry.teaching_information_exchange.entity.Course;
import cn.horry.teaching_information_exchange.entity.CourseValidationForTeacher;
import cn.horry.teaching_information_exchange.widget.SignInDialog;

public class SignInCourseTeacherActivity extends BaseActivity implements View.OnClickListener{
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
    @BindView(id = R.id.students_sign_in_all)
    private TextView students_sign_in_all;
    @BindView(id = R.id.time)
    private TextView validation_add_time;
    @BindView(id = R.id.state)
    private TextView validation_state;
    @BindView(id = R.id.validation_state_change , click = true)
    private View validation_state_change;
    private static final int VIDEO_CONTENT_DESC_MAX_LINE = 4;// 默认展示最大行数3行
    private static final int SHOW_CONTENT_NONE_STATE = 0;// 扩充
    private static final int SHRINK_UP_STATE = 1;// 收起状态
    private static final int SPREAD_STATE = 2;// 展开状态
    private static int mState = SHRINK_UP_STATE;
    private Drawable up;
    private Drawable down;
    private CourseValidationForTeacher course;
    private SignInDialog.Builder builder;
    @Override
    public void setRootView() {
        setContentView(R.layout.activity_sign_in_course_teacher);
    }

    @Override
    public void initData() {
        course = (CourseValidationForTeacher)getIntent().getSerializableExtra("Course");
    }

    @Override
    public void initWidget() {
        title.setText("签到");
        left.setVisibility(View.VISIBLE);
        left.setText("");
        course_title.setText(course.getName());
        content.setText(course.getContent());
        validation_add_time.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(course.getValidate_time()));
        if(course.getState()==1)
        {
            validation_state.setText("停止签到");
        }else
        {
            validation_state.setText("正在签到");
        }
        up = getResources().getDrawable(R.mipmap.detail_up);
        up.setBounds(0,0,up.getMinimumWidth(),up.getMinimumHeight());
        down = getResources().getDrawable(R.mipmap.detail_down);
        down.setBounds(0,0,down.getMinimumWidth(),down.getMinimumHeight());
        students_sign_in_all.setText("1/2");
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
            case R.id.validation_state_change:
                builder =  new SignInDialog.Builder(this);
                builder.setOnOkListener(new SignInDialog.Builder.OnOkListener() {
                    @Override
                    public void getDialogRadioButton(final DialogInterface dialog, final RadioButton radioButton) {
                        final int state;
                        if (radioButton.getText().toString().trim().equals("正在签到")) {
                            state = 0;
                        } else {
                            state = 1;
                        }
                        ValidationApi.updateValidationState(state, course.getvId(), new HttpCallBack() {
                            @Override
                            public void onSuccess(String t) {
                                super.onSuccess(t);
                                dialog.dismiss();
                                validation_state.setText(radioButton.getText());
                                course.setState(state);
                            }
                            @Override
                            public void onFailure(int errorNo, String strMsg) {
                                super.onFailure(errorNo, strMsg);
                            }
                        });
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setTitle("请选择签到状态");
                builder.create().show();
                break;
            default:
                break;
        }
    }
}
