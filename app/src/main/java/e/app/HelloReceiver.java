package e.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class HelloReceiver extends BroadcastReceiver {

    private static final String TAG = HelloReceiver.class.getName();

    public HelloReceiver() {
        Log.d(TAG, "crised");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "crised");
        Log.d(TAG, intent.toString());
    }
}
