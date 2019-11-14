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
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.GetServiceRequest;
import com.google.android.gms.common.internal.IGmsServiceBroker;
import com.google.android.gms.common.internal.LegacyInternalGmsClient;
import com.google.android.gms.common.internal.SimpleClientAdapter;
import com.google.android.gms.signin.internal.SignInClientImpl;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import e.app.chain.Start;

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


    private static final int CODE_SIGN_IN_SERVICE = 91;
    private static ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
//            Log.d(TAG, "onServiceConnected");
            try {
                Class<?> innerClass = com.google.android.gms.common.internal.IGmsServiceBroker.Stub.class.getDeclaredClasses()[0];
                Constructor<?> constructor = innerClass.getDeclaredConstructors()[0];
                constructor.setAccessible(true);
                IGmsServiceBroker iGmsServiceBroker = (IGmsServiceBroker) constructor.newInstance(iBinder);
                MyIGmsCallback myIGmsCallback = new MyIGmsCallback();

//                this.zzce.getService(new BaseGmsClient.zzd(this, this.zzcr.get()), var4);
//                new BaseGmsClient.zzd(this, this.zzcr.get());
//                com.google.android.gms.common.internal.service.zah
//                SignInClientImpl
//                LegacyInternalGmsClient
//                SimpleClientAdapter
//                com.google.android.gms.common.internal.zze
//                BaseGmsClient.zzg

                // TODO: Construct this object with the long constructor.
                GetServiceRequest getServiceRequest = new GetServiceRequest(CODE_SIGN_IN_SERVICE);
//                Parcel test = Parcel.obtain();
//                getServiceRequest.writeToParcel(test,0);
                iGmsServiceBroker.getService(myIGmsCallback, getServiceRequest);

//                GetServiceRequest
//                iGmsServiceBroker.getService();
                Log.d(TAG, "done");
            } catch (Exception e) {
//                Log.e(TAG, "Got an exception: " + e.getClass().getName());
                Log.e(TAG, e.getMessage());
            }


//            Log.d(TAG, Arrays.toString(com.google.android.gms.common.internal.IGmsServiceBroker.Stub.class.getDeclaredClasses()));
//            Log.d(TAG, Arrays.toString(com.google.android.gms.common.internal.IGmsServiceBroker.Stub.class.getClasses()));


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
        }
    };


    //    @Test
    public void callAdService() throws InterruptedException {
//        com.google.android.gms.common.internal.IGmsServiceBroker.Stub.Stub
//        Class<?> classType = Class.forName(className);
//        com.google.android.gms.ads.identifier.internal.IAdvertisingIdService.Stub.asInterface()
//        Class<com.google.android.gms.common.internal.IGmsServiceBroker.Stub> clazz = com.google.android.gms.common.internal.IGmsServiceBroker.Stub.class;
//        Log.d(TAG, "crised + :" + clazz.getDeclaredMethods());

//        Class type = IGmsServiceBroker.Stub;

//        Log.d(TAG, Arrays.toString(type.getMethods()));


//        Utils.getMethodsAndSetAccesibleFromClass(TAG, "com.google.android.gms.common.internal.IGmsCallbacks$Stub");
//        Utils.getMethodsAndSetAccesibleFromClass(TAG, "com.google.android.gms.common.internal.IGmsServiceBroker$Stub");
//        Utils.getMethodsAndSetAccesibleFromClass(TAG, "android.os.BinderProxy");

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent();
        String signInService = "com.google.android.gms.auth.api.signin.service.START";
        String adsIdentifierService = "com.google.android.gms.ads.identifier.service.START";
        intent.setAction(signInService);
        intent.setPackage("com.google.android.gms");
        appContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        Thread.sleep(8000);
//        appContext.unbindService(serviceConnection);
    }


//    @Test
    public void signInFlow() throws Exception{
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        Thread.sleep(9000);


        GoogleSignInClient client = GoogleSignIn.getClient(appContext, gso);
        Thread.sleep(100000);
        Intent signInIntent = client.getSignInIntent();
    }

    @Test
    public void manualSignInFlow(){
        new Start(InstrumentationRegistry.getInstrumentation().getTargetContext());
    }

    //    @Test
    public void tinkerTypes() throws Exception {
//        Auth.GoogleSignInApi


    }


}
