package cn.horry.teaching_information_exchange.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.AnnotateUtil;
import org.kymjs.kjframe.ui.BindView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import cn.horry.teaching_information_exchange.R;
import cn.horry.teaching_information_exchange.api.UserApi;
import cn.horry.teaching_information_exchange.entity.GeneralResponse;
import cn.horry.teaching_information_exchange.entity.User;
import cn.horry.teaching_information_exchange.ui.UserManager;
import cn.horry.teaching_information_exchange.widget.RoundCornerImageView;

public class MyInforActivity extends BaseActivity implements View.OnClickListener {

    @BindView(id = R.id.title)
    private TextView title;
    @BindView(id = R.id.left, click = true)
    private TextView left;
    @BindView(id = R.id.name, click = true)
    private View name;
    @BindView(id = R.id.age, click = true)
    private View age;
    @BindView(id = R.id.sex, click = true)
    private View sex;
    @BindView(id = R.id.name_text)
    private TextView name_text;
    @BindView(id = R.id.age_text)
    private TextView age_text;
    @BindView(id = R.id.sex_text)
    private TextView sex_text;
    @BindView(id = R.id.my_course, click = true)
    private View my_course;
    @BindView(id = R.id.head_image, click = true)
    private RoundCornerImageView head_image;
    private User user;
    //获取图片
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    private static final String IMAGE_FILE_NAME = "header.jpg";
    private String[] items = new String[]{"选择本地图片", "拍照"};
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_my_infor);
    }

    @Override
    public void initData() {
        user = UserManager.getInstance().getUser();
    }

    public void refreshData() {
        initWidget();
    }

    @Override
    public void initWidget() {
        title.setText("我的信息");
        left.setVisibility(View.VISIBLE);
        left.setText("");
        name_text.setText(user.getName());
        age_text.setText(user.getAge().toString());
        sex_text.setText(getSex(user.getSex()));
    }

    private String getSex(int sex) {
        String s;
        if (sex == 0) {
            s = "男";
        } else if (sex == 1) {
            s = "女";
        } else {
            s = "未设置";
        }
        return s;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left:
                finish();
                break;
            case R.id.name:
                goUpdate(1);
                break;
            case R.id.age:
                goUpdate(2);
                break;
            case R.id.sex:
                goUpdate(3);
                break;
            case R.id.my_course:
                startActivity(new Intent(this, CourseInfoActivity.class));
                break;
            case R.id.head_image:
                setImg();
                break;
            default:
                break;
        }
    }

    //设置头像
    private void setImg() {
        //设置头像
        new AlertDialog.Builder(this)
                .setTitle("设置头像")
                .setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intentFromGallery = new Intent();
                                intentFromGallery.setType("image/*"); // 设置文件类型
                                intentFromGallery
                                        .setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intentFromGallery,
                                        IMAGE_REQUEST_CODE);
                                break;
                            case 1:

                                Intent intentFromCapture = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);
                                // 判断存储卡是否可以用，可用进行存储
                                if (hasSdcard()) {

                                    intentFromCapture.putExtra(
                                            MediaStore.EXTRA_OUTPUT,
                                            Uri.fromFile(new File(Environment
                                                    .getExternalStorageDirectory(),
                                                    IMAGE_FILE_NAME)));
                                }

                                startActivityForResult(intentFromCapture,
                                        CAMERA_REQUEST_CODE);
                                break;
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    /**
     * @return
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    //返回事件
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        } else {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    resizeImage(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    if (isSdcardExisting()) {
                        resizeImage(getImageUri());
                    } else {
                        showShortText("未找到存储卡，无法存储照片！");
                    }
                    break;

                case RESIZE_REQUEST_CODE:
                    if (data != null) {
                        showResizeImage(data);
                    }
                    break;
            }
        }


    }

    private Uri getImageUri() {
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                IMAGE_FILE_NAME));
    }

    private boolean isSdcardExisting() {
        final String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public void resizeImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }

    private void showResizeImage(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            upload(photo);
            Drawable drawable = new BitmapDrawable(head_image.getResources(), photo);
            head_image.setImageDrawable(drawable);
        }
    }

    public void upload(Bitmap photo) {
        File file = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserApi.uploadImg(file, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                GeneralResponse<String> s = new Gson().fromJson(t, new TypeToken<GeneralResponse<String>>() {
                }.getType());
                UserApi.updateUserImg(s.getData().split(";")[0],UserManager.getInstance().getUser().getId(), new HttpCallBack() {
                    @Override
                    public void onSuccess(String t) {
                        GeneralResponse<Integer> r = new Gson().fromJson(t, new TypeToken<GeneralResponse<Integer>>() {
                        }.getType());
                        showShortText(r.getMsg());
                    }
                    @Override
                    public void onFailure(int errorNo, String strMsg) {
                        super.onFailure(errorNo, strMsg);
                    }
                });
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
            }
        });
    }

    private void goUpdate(int flag) {
        Intent intent = new Intent(this, MyInfoItem.class);
        intent.putExtra("flag", flag);
        startActivity(intent);
    }


}
