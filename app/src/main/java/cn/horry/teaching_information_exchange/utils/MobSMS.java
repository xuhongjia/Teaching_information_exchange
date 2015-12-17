package cn.horry.teaching_information_exchange.utils;

import android.content.Context;
import android.util.Log;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2015/12/17.
 */
public class MobSMS {
    private static MobSMS _MobSMS;
    private SMSSDK smssdk;
    private String AppKey="d778ca96e873";
    private String AppSecret="16c7d4f5e9077026f5b6210beeb7dd4f";
    private MobSMS(Context context){
        smssdk = new SMSSDK();
        smssdk.initSDK(context, AppKey, AppSecret);
        Log.e("sms","smsm");
    }
    public static MobSMS getInstance(Context context){
        if(_MobSMS==null)
        {
            _MobSMS = new MobSMS(context);
        }
        return _MobSMS;
    }

    public void setListenerAndRegiterEventHandler(final smsListener listener){
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        listener.validateSMSSuccess();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        listener.sendSMSSuccess((Boolean)data);
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    listener.onFailed((Throwable) data);
                }
            }
        };

        smssdk.registerEventHandler(eventHandler);
    }
    public void unregisterAllEventHandler(){
        smssdk.unregisterAllEventHandler();
    }

    public SMSSDK getSmssdk() {
        return smssdk;
    }

    public void setSmssdk(SMSSDK smssdk) {
        this.smssdk = smssdk;
    }

    public interface smsListener{
        /**
         * 发送验证码成功
         */
        void sendSMSSuccess(boolean isIntelligentVerification);

        /**
         * 验证验证码成功
         */
        void validateSMSSuccess();

        /**
         * 发送或者验证失败
         * @param data 失败信息
         */
        void onFailed(Throwable data);
    }
}
