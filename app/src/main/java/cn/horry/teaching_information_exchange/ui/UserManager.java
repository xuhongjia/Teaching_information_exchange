package cn.horry.teaching_information_exchange.ui;

import cn.horry.teaching_information_exchange.entity.User;

/**
 * Created by Administrator on 2015/12/14.
 */
public class UserManager {
    private static UserManager _UserManager = null;
    private User user;
    private UserManager(){

    }
    public static UserManager getInstance(){
        if(_UserManager==null)
        {
            _UserManager = new UserManager();
        }
        return _UserManager;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
