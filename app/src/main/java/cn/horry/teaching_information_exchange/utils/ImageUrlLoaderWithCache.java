package cn.horry.teaching_information_exchange.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.bitmap.BitmapCallBack;

import cn.horry.teaching_information_exchange.R;

/**
 * Created by Myy on 2015/11/6.
 */
public class ImageUrlLoaderWithCache {
    private static ImageUrlLoaderWithCache _ImageUrlLoaderWithCache = null;
    private static LruCache<String,Bitmap> imageCache = null;
    private Handler handler;
    private ImageUrlLoaderWithCache(){
        initImageCache();
        initHandler();
    }
    public static ImageUrlLoaderWithCache getInstence(){
        if(_ImageUrlLoaderWithCache==null)
        {
            _ImageUrlLoaderWithCache=new ImageUrlLoaderWithCache();
        }
        return _ImageUrlLoaderWithCache;
    }
    /**
     * 初始化图片缓存
     */
    private  void initImageCache() {
        int runMemory = (int) Runtime.getRuntime().maxMemory();//获得最大内存
        int cacheMemory = runMemory / 5;
        imageCache = new LruCache<String, Bitmap>(cacheMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {

                return value.getHeight() * value.getRowBytes();
            }
        };
    }
    public void ImageLoad(final String path, final ImageView imageView){
        if(path==null)
        {
            imageView.setImageDrawable(imageView.getResources().getDrawable(R.mipmap.default_head));
            return ;
        }
        imageView.setTag(path);
        Bitmap bitmap = imageCache.get(path);
        if(bitmap!=null)
        {
            refreshImageView(path, imageView, bitmap);
        }
        else {
            getUrlImage(path, imageView);
        }
    }

    private void getUrlImage(final String path,ImageView imageView){
        KJBitmap.Builder builder = new KJBitmap.Builder();
        Drawable drawable = imageView.getResources().getDrawable(R.mipmap.default_head);
        builder.view(imageView).imageUrl(path).loadBitmap(drawable).errorBitmap(drawable).callback(new BitmapCallBack() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                super.onSuccess(bitmap);
                imageCache.put(path,bitmap);
            }
            @Override
            public void onFailure(Exception e) {
                super.onFailure(e);
            }

        }).display();
    }

    /**
     * 初始化handler，用于刷新imageView
     */
    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                ImageEntity entity = (ImageEntity) msg.obj;
                entity.imageView.setImageBitmap(entity.bitmap);
            }
        };
    }

    /**
     * 刷新图片
     *
     * @param path
     * @param imageView
     * @param bitmap
     */
    private void refreshImageView(String path, ImageView imageView, Bitmap bitmap) {
        ImageEntity entity = new ImageEntity();
        entity.bitmap = bitmap;
        entity.imageView = imageView;
        Message msg = Message.obtain();
        msg.obj = entity;
        if (path.equals((String) imageView.getTag()))
            handler.sendMessage(msg);
    }

    /**
     * 保存图片
     */
    class ImageEntity {
        ImageView imageView;
        Bitmap bitmap;
    }
}