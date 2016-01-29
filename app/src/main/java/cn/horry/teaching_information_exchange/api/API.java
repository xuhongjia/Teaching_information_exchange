package cn.horry.teaching_information_exchange.api;

import org.kymjs.kjframe.KJHttp;

/**
 * Created by Administrator on 2015/12/14.
 */
public abstract class API {
    public static String REAL_URL = "http://horry.me:8080/TeachingInformationExchangeWeb/";
    public static String TEST_URL = "http://192.168.1.106:8080/web/";
    public static String DEFAULT_HEAD_IMG = "http://horry.oss-cn-shenzhen.aliyuncs.com/43d19356-cd11-4a5e-b5aa-e87cd974823a.png";
    public static String URL = REAL_URL;
    public static KJHttp.Builder builderHttp = new KJHttp.Builder();
}