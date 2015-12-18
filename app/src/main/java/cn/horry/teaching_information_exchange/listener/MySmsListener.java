package cn.horry.teaching_information_exchange.listener;


import android.os.Handler;

import cn.horry.teaching_information_exchange.utils.MobSMS;

/**
 * Created by Myy on 2015/12/18.
 */
public class MySmsListener implements MobSMS.smsListener {
    private Handler handler;
    public MySmsListener(Handler handler){
        this.handler=handler;
    }
    @Override
    public void sendSMSSuccess(boolean isIntelligentVerification) {
        if(isIntelligentVerification){
            handler.sendEmptyMessage(-1);
        }
        else{
            handler.sendEmptyMessage(1);
        }
    }

    @Override
    public void validateSMSSuccess() {
        handler.sendEmptyMessage(2);
    }

    @Override
    public void onFailed(Throwable data) {
        handler.sendMessage(handler.obtainMessage(0,data));
    }
}
