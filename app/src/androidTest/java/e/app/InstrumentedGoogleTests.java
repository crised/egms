package e.app;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.internal.GoogleApiManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import e.app.chain.CustomBuilder;

@RunWith(AndroidJUnit4.class)
public class InstrumentedGoogleTests {

    private static final String TAG = InstrumentedGoogleTests.class.getName();

    @Test
    public void debugGoogleApiManager() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        GoogleApiManager googleApiManager = CustomBuilder.buildGoogleApiManager(appContext, false);
        Handler handler = UtilsReflex.getField(googleApiManager, "handler", Handler.class);
        Message message = handler.obtainMessage(6);
        boolean ans = googleApiManager.handleMessage(message);
        Log.d(TAG, "done");
    }

    @Test
    public void debugGoogleApiAvailability() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        Log.d(TAG, "IsGooglePlay Service available: " + googleApiAvailability.isGooglePlayServicesAvailable(appContext));
        Thread.sleep(500);

    }



}
