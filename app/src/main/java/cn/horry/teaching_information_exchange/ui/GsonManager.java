package cn.horry.teaching_information_exchange.ui;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2015/12/14.
 */
public class GsonManager {
    private static GsonManager _GsonManager = null;
    private Gson gson = new Gson();
    private GsonManager(){

    }
    public static GsonManager getInstance(){
        if(_GsonManager == null)
        {
            _GsonManager = new GsonManager();
        }
        return _GsonManager;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }
}
