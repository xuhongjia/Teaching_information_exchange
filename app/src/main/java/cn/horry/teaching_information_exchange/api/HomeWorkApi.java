package cn.horry.teaching_information_exchange.api;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.http.Request;

import cn.horry.teaching_information_exchange.utils.MyHttpParams;

/**
 * Created by Myy on 2016/2/13.
 */
public class HomeWorkApi extends API {
    public static void getCourseHomeWork(int uId , int isTeacher ,int page, HttpCallBack httpCallBack){
        String url = URL.concat("action/homework/getHomeWorkCourse");
        MyHttpParams params = new MyHttpParams("uId",uId,"isTeacher",isTeacher,"page",page);
        builderHttp.url(url).httpMethod(Request.HttpMethod.POST).params(params).callback(httpCallBack).useCache(false).request();
    }
    public static void updateHomeWork(int hId,String Content,HttpCallBack httpCallBack){
        String url = URL.concat("action/homework/updateHomeWork");
        HttpParams params = new MyHttpParams("hId",hId,"content",Content);
        builderHttp.url(url).httpMethod(Request.HttpMethod.POST).params(params).callback(httpCallBack).useCache(false).request();
    }
}
