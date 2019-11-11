package e.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.os.Parcel;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Scope;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final String TAG = ExampleInstrumentedTest.class.getName();

    //    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("e.app", appContext.getPackageName());
    }


    //    @Test
    public void tinkerWithGoogleSignInAccount() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, URISyntaxException {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Log.e(TAG, "crised");
        Constructor<GoogleSignInAccount> constructor = GoogleSignInAccount.class.getDeclaredConstructor(int.class, String.class, String.class, String.class, String.class, Uri.class, String.class, long.class, String.class, List.class, String.class, String.class);
        constructor.setAccessible(true);

        Object[] args = {0, "", "", "hi@gmail.com", "", Uri.EMPTY, "", 0L, "",
                new ArrayList<Scope>() {
                }, "", ""};
        GoogleSignInAccount source = constructor.newInstance(args);
        Log.d(TAG, source.getEmail()); // worked'
        Parcel parcel = Parcel.obtain();         //~ object written is a return value from AIDL. 1. writeToParcel may be 0 or 1.
//        googleSignInAccount.writeToParcel(parcel, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
        source.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        GoogleSignInAccount dest = GoogleSignInAccount.CREATOR.createFromParcel(parcel);
        assertEquals(source.getEmail(), dest.getEmail());
        parcel.recycle();


    }

    //    @Test
    public void parcelableSimpleFlow() {
        int value = 92;
        Parcel parcel = Parcel.obtain();
        parcel.writeInt(value);
        parcel.setDataPosition(0); // Important
        assertEquals(value, parcel.readInt());
    }

    //    @Test
    public void parcelableFlow() {
        String test = "foo";
        ParcelableExample source = new ParcelableExample(test);
        Parcel parcel = Parcel.obtain();
        source.writeToParcel(parcel, 0);
        parcel.setDataPosition(0); // Important
        ParcelableExample dest = ParcelableExample.CREATOR.createFromParcel(parcel);
        assertEquals(test, dest.myString);
    }

    public void getAdsInfo() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Log.d(TAG, "About to call Google Play Services");
        try {
            AdvertisingIdClient.Info info = AdvertisingIdClient.getAdvertisingIdInfo(appContext);
            Log.d(TAG, "Advertising client info: " + info.toString()); //{b92bb45d-0ea9-4ae4-b128-89b65f57b665}false
        } catch (IOException | GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException e) {
            Log.e(TAG, "Exception!");
        }
        Log.d(TAG, "finished");
    }

    //    @Test
    public void adsFlow() {
        new Thread() {
            @Override
            public void run() {
                getAdsInfo();
            }
        }.run();
    }

    //    @Test
    public void getServiceManagerInfo() {

        try {
            Class serviceManager = Class.forName("android.os.ServiceManager");
            Method[] serviceManagerMethods = serviceManager.getDeclaredMethods();
            for (Method method : serviceManagerMethods) {
                Log.d(TAG, method.getName());
                method.setAccessible(true);
            }
            Method getService = serviceManager.getMethod("getService", new Class[]{String.class});
            Method checkService = serviceManager.getMethod("checkService", new Class[]{String.class});
            Method listServices = serviceManager.getMethod("listServices", new Class[]{});
//            Object result = listServices.invoke(serviceManager, new Object[]{Context.AUDIO_SERVICE}))
            Object result = listServices.invoke(serviceManager, new Object[]{});
            if (result != null) {
                String[] systemServices = (String[]) result;
                for (String serviceName : systemServices) {
                    Log.d(TAG, serviceName);
                }
            }
//            if (result != null) {
//                IBinder binder = (IBinder) result;
//
//
//            }
        } catch
        (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
                        e) {
            Log.e(TAG, "Could't found Service Manager");
        }

    }

    private static ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            Log.d(TAG, "Hi crised");

            Log.d(TAG, "onServiceConnected");
            Log.d(TAG, name.flattenToString());
            Log.d(TAG, name.getClassName());
            Utils.logIBinder(TAG, iBinder);
            Log.d(TAG, "done");

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
        }
    };

    //    @Test
    public void callAdService() throws InterruptedException {
        Log.d(TAG, "Hello");
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Intent intent = new Intent();
        String signInService = "com.google.android.gms.auth.api.signin.service.START";
        String identifierService = "com.google.android.gms.ads.identifier.service.START";
        intent.setAction(signInService);
        intent.setPackage("com.google.android.gms");
        appContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        Thread.sleep(1000);
        appContext.unbindService(serviceConnection);
    }

    @Test
    public void getChimeraClass() {
        try {
            Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            GoogleSignInClient client = GoogleSignIn.getClient(appContext, gso);
            Intent signInIntent = client.getSignInIntent();
            Class serviceManager = Class.forName("com.google.android.gms.chimera.GmsApiService");
//            Class[] classes = Utils.getClasses("com.google.android.gms");
//            Log.d(TAG, "Classes length: " + classes.length);
//            for (Class clazz : classes) {
//                Log.d(TAG, clazz.getName());
//            }


        } catch (ClassNotFoundException  e) {
            // We get class not found exception
            Log.e(TAG, e.getMessage());
        }

    }


}
