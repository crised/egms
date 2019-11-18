package e.app;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UtilsReflex {

    private static final String TAG = UtilsReflex.class.getName();


    //TODO: Methods are instance oriented, make them class oriented as well.

    public static <T> T getField(Object instance, String fieldName, Class<T> fieldType) {
        try {
            Field f = instance.getClass().getDeclaredField(fieldName);
            if (f == null) f = instance.getClass().getField(fieldName);
            f.setAccessible(true);
            return fieldType.cast(f.get(instance));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    /***
     * Method to build instances from private Constructors.
     * @param classType
     * @param args
     * @param parameterTypes
     * @param declared
     * @param <T>
     * @return
     */
    public static <T> T constructInstance(Class<T> classType, Object[] args,
                                          Class<?>[] parameterTypes, boolean declared) {
        Constructor<T> constructor;
        try {
            if (declared) constructor = classType.getDeclaredConstructor(parameterTypes);
            else constructor = classType.getDeclaredConstructor(parameterTypes);
            constructor.setAccessible(true);
            T instance = constructor.newInstance(args);
            return instance;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }


    public static <T> T callMethod(Object instance, Class<T> returnType,
                                   String methodName, Object[] args,
                                   Class<?>[] parameterTypes) {
        try {
            Method m = instance.getClass().getDeclaredMethod(methodName, parameterTypes);
            m.setAccessible(true);
            return returnType.cast(m.invoke(instance, args));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }
}
