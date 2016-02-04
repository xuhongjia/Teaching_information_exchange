package cn.horry.teaching_information_exchange.utils;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;


/**
 * Created by horry on 2016/1/28.
 */
public class GDMap{
    private static GDMap _GDMap;
    private AMapLocationClient aMapLocationClient = null;
    private AMapLocationClientOption aMapLocationClientOption;
    private LocationListener loactionListener;
    private GDMap(Context context){
        aMapLocationClient = new AMapLocationClient(context);
        aMapLocationClientOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        aMapLocationClientOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        aMapLocationClientOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        aMapLocationClientOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        aMapLocationClientOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        //aMapLocationClientOption.setInterval(2000);
        aMapLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        loactionListener.getAddressString(aMapLocation.getAddress());
                        loactionListener.getAMapLocation(aMapLocation);
                    }else{
                        Log.e("AMapError", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
                stopLoction();
            }
        });
    }
    public static GDMap getInstance(Context context){
        if(_GDMap==null)
        {
            _GDMap=new GDMap(context);
        }
        return _GDMap;
    }
    public void startLoction(){
        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        aMapLocationClient.startLocation();
    }
    public void stopLoction(){
        aMapLocationClient.stopLocation();
    }

    public LocationListener getLoactionListener() {
        return loactionListener;
    }

    public void setLoactionListener(LocationListener loactionListener) {
        this.loactionListener = loactionListener;
    }
    public interface LocationListener{
        void getAddressString(String address);
        void getAMapLocation(AMapLocation aMapLocation);
    }
}
