package cn.horry.teaching_information_exchange.api;

import org.kymjs.kjframe.KJHttp;

/**
 * Created by Administrator on 2015/12/14.
 */
public abstract class API {
    public static String REAL_URL = "http://horry.me:8080/TeachingInformationExchangeWeb/";
    public static String TEST_URL = "http://localhost:8080/web/";
    public static String URL = REAL_URL;
    public static KJHttp.Builder builderHttp = new KJHttp.Builder();
}