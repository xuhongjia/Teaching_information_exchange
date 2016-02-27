package cn.horry.teaching_information_exchange.api;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.http.Request;

import cn.horry.teaching_information_exchange.entity.Course;
import cn.horry.teaching_information_exchange.entity.Student_Course;
import cn.horry.teaching_information_exchange.utils.MyHttpParams;

/**
 * Created by Administrator on 2016/1/29.
 */
public class CourseApi extends API {
    public static void getValidationCourse(int uId , int isTeacher ,int page, HttpCallBack httpCallBack){
        String url = URL.concat("action/course/getValidationCourse");
        MyHttpParams params = new MyHttpParams("uId",uId,"isTeacher",isTeacher,"page",page);
        builderHttp.url(url).httpMethod(Request.HttpMethod.POST).params(params).callback(httpCallBack).useCache(false).request();
    }
    public static void  getAllValidationStudents(int vId,int cId,HttpCallBack httpCallBack)
    {
        String url = URL.concat("action/course/getAllValidationStudents");
        HttpParams params =new MyHttpParams("vId",vId,"cId",cId);
        builderHttp.url(url).httpMethod(Request.HttpMethod.POST).params(params).callback(httpCallBack).useCache(false).request();
    }
    public static void addCourse(Course course , HttpCallBack httpCallBack){
        String url = URL.concat("action/course/addCourse");
        HttpParams params =new MyHttpParams(course);
        builderHttp.url(url).httpMethod(Request.HttpMethod.POST).params(params).callback(httpCallBack).useCache(false).request();
    }
    public static void addStudentCourse(Student_Course student_course ,HttpCallBack httpCallBack){
        String url = URL.concat("action/course/addStudentCourse");
        HttpParams params =new MyHttpParams(student_course);
        builderHttp.url(url).httpMethod(Request.HttpMethod.POST).params(params).callback(httpCallBack).useCache(false).request();
    }
    public static void getAllCourse(HttpCallBack httpCallBack){
        String url = URL.concat("action/course/getAllCourse");
        builderHttp.url(url).httpMethod(Request.HttpMethod.POST).callback(httpCallBack).useCache(true).request();
    }
}
