package cn.horry.teaching_information_exchange.api;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.Request;

import cn.horry.teaching_information_exchange.entity.User;
import cn.horry.teaching_information_exchange.utils.MyHttpParams;

/**
 * Created by Administrator on 2015/12/14.
 */
public class UserApi extends API{
    public static void login(String account,String password,int isTeacher ,HttpCallBack httpCallBack){
        String url = URL.concat("action/user/login");
        MyHttpParams params  = new MyHttpParams("account",account,"password",password,"isTeacher",isTeacher);
        builderHttp.url(url).httpMethod(Request.HttpMethod.POST).params(params).callback(httpCallBack).useCache(false).request();
    }
    public static void getCourse(int uId , int isTeacher , HttpCallBack httpCallBack){
        String url = URL.concat("action/user/getCourse");
        MyHttpParams params = new MyHttpParams("uId",uId,"isTeacher",isTeacher);
        builderHttp.url(url).httpMethod(Request.HttpMethod.POST).params(params).callback(httpCallBack).useCache(false).request();
    }
    public static void register(User user,HttpCallBack httpCallBack){
        String url = URL.concat("action/user/register");
        MyHttpParams params = new MyHttpParams(user);
        builderHttp.url(url).httpMethod(Request.HttpMethod.POST).params(params).callback(httpCallBack).useCache(false).request();
    }
    public static void update(User user,HttpCallBack httpCallBack){
        String url = URL.concat("action/user/update");
        MyHttpParams params = new MyHttpParams(user);
        builderHttp.url(url).httpMethod(Request.HttpMethod.POST).params(params).callback(httpCallBack).useCache(false).request();
    }
}
