package e.app;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

public class MyHandler extends Handler {

    private static String TAG = MyHandler.class.getName();

    public MyHandler() {
        super();
        Log.d(TAG, "Constructor");
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        Log.d(TAG, "Received back a message!!: " + msg.toString());
        super.handleMessage(msg);
    }
}
