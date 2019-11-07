package e.app;

import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.Scope;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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


    @Test
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
        assertEquals(source.getEmail(),dest.getEmail());


    }

    @Test
    public void parcelableSimpleFlow() {
        int value = 92;
        Parcel parcel = Parcel.obtain();
        parcel.writeInt(value);
        parcel.setDataPosition(0); // Important
        assertEquals(value, parcel.readInt());
    }

    @Test
    public void parcelableFlow() {
        String test = "foo";
        ParcelableExample source = new ParcelableExample(test);
        Parcel parcel = Parcel.obtain();
        source.writeToParcel(parcel, 0);
        parcel.setDataPosition(0); // Important
        ParcelableExample dest = ParcelableExample.CREATOR.createFromParcel(parcel);
        assertEquals(test, dest.myString);
    }
}
