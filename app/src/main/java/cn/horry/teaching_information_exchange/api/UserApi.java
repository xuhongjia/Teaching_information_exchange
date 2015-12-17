package cn.horry.teaching_information_exchange.api;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.Request;

import cn.horry.teaching_information_exchange.utils.MyHttpParams;

/**
 * Created by Administrator on 2015/12/14.
 */
public class UserApi extends API{
    public static void login(String account,String password,int isTeacher ,HttpCallBack httpCallBack){
        String url = URL.concat("action/user/login");
        MyHttpParams params  = new MyHttpParams("account",account,"password",password,"isTeacher",isTeacher);
        builderHttp.url(url).httpMethod(Request.HttpMethod.POST).params(params).callback(httpCallBack).useCache(true).request();
    }
    public static void getCourse(int uId , int isTeacher ,int page, HttpCallBack httpCallBack){
        String url = URL.concat("action/user/getCourse");
        MyHttpParams params = new MyHttpParams("uId",uId,"isTeacher",isTeacher,"page",page);
        builderHttp.url(url).httpMethod(Request.HttpMethod.POST).params(params).callback(httpCallBack).useCache(true).request();
    }
}
