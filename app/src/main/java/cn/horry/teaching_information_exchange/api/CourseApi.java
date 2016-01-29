package cn.horry.teaching_information_exchange.api;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.Request;

import cn.horry.teaching_information_exchange.utils.MyHttpParams;

/**
 * Created by Administrator on 2016/1/29.
 */
public class CourseApi extends API {
    public static void getValidationCourse(int uId , int isTeacher ,int page, HttpCallBack httpCallBack){
        String url = URL.concat("action/course/getValidationCourse");
        MyHttpParams params = new MyHttpParams("uId",uId,"isTeacher",isTeacher,"page",page);
        builderHttp.url(url).httpMethod(Request.HttpMethod.POST).params(params).callback(httpCallBack).useCache(true).request();
    }
}
