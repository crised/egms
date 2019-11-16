package e.app;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class UtilsReflex {

    //TODO: Methods are instance oriented, make them class oriented as well.

    public static <T> T getField(Object instance, String fieldName, Class<T> fieldType) {
        T ans = null;
        try {
            Field f = instance.getClass().getDeclaredField(fieldName);
            if (f == null) f = instance.getClass().getField(fieldName);
            f.setAccessible(true);
            ans = fieldType.cast(f.get(instance));
        } catch (Exception e) {

        }
        return ans;
    }

//    public static Method findMethod(Object instance, String methodName) {
//        try {
//
//            instance.getClass().getMethod();
//
//        } catch (Exception E) {
//
//        }
//
//    }

    public static <T> T callMethod(Object instance, Class<T> returnType,
                                   String methodName, Object[] args,
                                   Class<?>[] parameterTypes) {
        try {
            Method m = instance.getClass().getDeclaredMethod(methodName, parameterTypes);
            m.setAccessible(true);
            return returnType.cast(m.invoke(instance, args));
        } catch (Exception e) {
            return null;
        }
    }
}
