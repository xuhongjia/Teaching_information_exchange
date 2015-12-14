package cn.horry.teaching_information_exchange.utils;

import org.kymjs.kjframe.http.HttpParams;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by Administrator on 2015/12/14.
 */
public class MyHttpParams extends HttpParams{
    public MyHttpParams(){
        super();
    }
    public MyHttpParams(Object object)
    {
        if (object == null)
            return;
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            if (Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers)
                    || Modifier.isTransient(modifiers)) {
                continue;
            }
            field.setAccessible(true);
            Object valueObject;
            try {
                valueObject = field.get(object);
                if (valueObject == null)
                    continue;
                put(field.getName(), valueObject.toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }
    public MyHttpParams(Object... objects) {
        if (objects == null || objects.length % 2 != 0) {
            return;
        }
        for (int i = 1; i < objects.length; i += 2) {
            put(String.valueOf(objects[i - 1]),
                    String.valueOf(objects[i]));
        }
    }
}
