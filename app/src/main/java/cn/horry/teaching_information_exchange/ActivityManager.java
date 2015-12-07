package cn.horry.teaching_information_exchange;

import android.app.Activity;

import java.util.LinkedList;

/**
 * Created by Administrator on 2015/12/7.
 */
public class ActivityManager {
    private static ActivityManager _ActivityManager;
    private LinkedList<Activity> activityList = new LinkedList<Activity>();
    private ActivityManager(){

    }
    public static ActivityManager getInstance(){
        if(_ActivityManager==null)
        {
            _ActivityManager=new ActivityManager();
        }
        return _ActivityManager;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public Activity getTop(){
        return activityList.getLast();
    }

    public void removeLastActivity(){
        activityList.removeLast();
    }

    public void removeActivity(int position)
    {
        activityList.remove(position);
    }

    public LinkedList<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(LinkedList<Activity> activityList) {
        this.activityList = activityList;
    }
}
