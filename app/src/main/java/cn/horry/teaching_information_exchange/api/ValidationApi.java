package cn.horry.teaching_information_exchange.api;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.http.Request;

import cn.horry.teaching_information_exchange.entity.Validation;
import cn.horry.teaching_information_exchange.entity.ValidationForStudent;
import cn.horry.teaching_information_exchange.utils.MyHttpParams;

/**
 * Created by Administrator on 2016/2/4.
 */
public class ValidationApi extends API {
    public static void addValidation(Validation validation ,HttpCallBack httpCallBack){
        String url = URL.concat("action/validation/addValidation");
        HttpParams params = new MyHttpParams(validation);
        builderHttp.url(url).httpMethod(Request.HttpMethod.POST).callback(httpCallBack).useCache(false).params(params).request();
    }
    public static void updateValidationState(int state,int id,HttpCallBack httpCallBack){
        String url = URL.concat("action/validation/updateValidationState");
        HttpParams params = new MyHttpParams("id",id,"state",state);
        builderHttp.url(url).httpMethod(Request.HttpMethod.POST).params(params).callback(httpCallBack).useCache(false).request();
    }
    public static void addValidationForStudent(ValidationForStudent validationForStudent,HttpCallBack httpCallBack){
        String url = URL.concat("action/validation/addValidationForStudent");
        HttpParams params = new MyHttpParams(validationForStudent);
        builderHttp.url(url).httpMethod(Request.HttpMethod.POST).params(params).callback(httpCallBack).useCache(false).request();
    }
}
