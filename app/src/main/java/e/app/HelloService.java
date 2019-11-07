package e.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class HelloService extends Service {

    private static String TAG = HelloService.class.getName();

    public HelloService() {
        Log.d(TAG, "crised: Hi!");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, intent.toString());
        return null;
    }
}
