package cn.horry.teaching_information_exchange.api;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.http.Request;

import cn.horry.teaching_information_exchange.entity.FeedBack;
import cn.horry.teaching_information_exchange.utils.MyHttpParams;

/**
 * Created by Myy on 2016/2/15.
 */
public class FeedBackApi extends API {
    public static void addFeedBack(FeedBack feedBack ,HttpCallBack httpCallBack){
        String url = URL.concat("action/feedback/addFeedBack");
        HttpParams params = new MyHttpParams(feedBack);
        builderHttp.url(url).httpMethod(Request.HttpMethod.POST).params(params).callback(httpCallBack).useCache(false).request();
    }

    public static void getFeedBackForStudent( int uId, int page, HttpCallBack httpCallBack){
        String url = URL.concat("action/feedback/getFeedBackForStudent");
        HttpParams params = new MyHttpParams("uId",uId,"page",page);
        builderHttp.url(url).httpMethod(Request.HttpMethod.POST).params(params).callback(httpCallBack).useCache(false).request();
    }

    public static void getFeedBackForTeacher(int cId,HttpCallBack httpCallBack){
        String url = URL.concat("action/feedback/getFeedBackForTeacher");
        HttpParams params = new MyHttpParams("cId",cId);
        builderHttp.url(url).httpMethod(Request.HttpMethod.POST).params(params).callback(httpCallBack).useCache(false).request();
    }
    public static void updateFeedBack(String content , int fId , HttpCallBack httpCallBack){
        String url = URL.concat("action/feedback/updateFeedBack");
        HttpParams params = new MyHttpParams("content",content,"fId",fId);
        builderHttp.url(url).httpMethod(Request.HttpMethod.POST).params(params).callback(httpCallBack).useCache(false).request();
    }
}
