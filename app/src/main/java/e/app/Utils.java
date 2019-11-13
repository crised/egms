package e.app;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.StringJoiner;

public class Utils {

    public static String toString(Intent intent) {
        StringJoiner sj = new StringJoiner(" ");
        sj.add(intent.toString());
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            sj.add(",Bundle contents: ");
            for (String key : bundle.keySet()) {
                Object obj = bundle.get(key);
                if (obj != null) {
                    sj.add(key.getClass().toString());
                    sj.add(key + ":");
                    sj.add(obj.getClass().toString());
                    if (obj instanceof PendingIntent) sj.add(toString((PendingIntent) obj));
                    else sj.add(String.valueOf(obj));
                }
            }
        }
        if (intent.getAction() != null) sj.add(intent.getAction());
        return sj.toString();
    }

    public static String toString(PendingIntent pendingIntent) {
        StringJoiner sj = new StringJoiner(" ");
        sj.add("Pending Intent:");
        sj.add("creatorPackage").add(pendingIntent.getCreatorPackage());
        sj.add("intent Sender").add(pendingIntent.getIntentSender().getCreatorPackage());
        sj.add("toString").add(pendingIntent.toString());
        sj.add("end of pending intent");
        return sj.toString();
    }

    public static void logIBinder(String TAG, IBinder iBinder) {
        StringJoiner sj = new StringJoiner(" ");

        try {
            String interfaceDescriptor = iBinder.getInterfaceDescriptor();
            sj.add("interfaceDescriptor").add(interfaceDescriptor);
            IInterface iInterface = iBinder.queryLocalInterface(interfaceDescriptor);
            if (iInterface != null) sj.add("iInterface").add(iInterface.toString());
            /***
             *
             * In Java 7, you can cast a SocketInputStream to a FileInputStream,
             * and call getFD() to get the FileDescriptor object.
             *
             * Then you can use reflection to access the FileDescriptor object's private int fd field.
             * (You use the Class.getDeclaredField(...) method to get the Field,
             * call Field.setAccessible(true), and then get the field's value using Field.getInt(...).)
             *
             */
//            iBinder.dump(FileDescriptor.out, null);
            Log.d(TAG, "done");


        } catch (RemoteException e) {
            Log.e(TAG, e.getMessage());

        }
        Log.d(TAG, sj.toString());

    }

    public static Method[] getMethodsAndSetAccesibleFromClass(String TAG, String classname) {
        Log.d(TAG, "Getting declared methods for class: " + classname);
//        Class<MyClass> clazz = MyClass.class;
//        Class type = Class.forName(classname);

//        Class binderProxy = iBinder.getClass();
//        Log.d(TAG, Arrays.toString(binderProxy.getMethods()));
        Method[] methods = null;
        try {
            Class type = Class.forName(classname);
            methods = type.getMethods();
//            methods = type.getDeclaredMethods();
            for (Method method : methods) {
                Log.d(TAG, method.getName());
                method.setAccessible(true);
            }
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Class not found!");
        }
        Log.d(TAG, "Done getting methods\n");
        return methods;
    }
}