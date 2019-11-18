package e.app.chain;

import android.content.Context;
import android.os.HandlerThread;
import android.os.Looper;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.ApiExceptionMapper;
import com.google.android.gms.common.api.internal.GoogleApiManager;

import e.app.UtilsReflex;

public class CustomBuilder {

    public static GoogleApi.Settings buildGoogleApiSettings(Context ctx) {
        GoogleApi.Settings.Builder googleApiSettingsBuilder = new GoogleApi.Settings.Builder();
        ApiExceptionMapper apiExceptionMapper = new ApiExceptionMapper();
        googleApiSettingsBuilder.setMapper(apiExceptionMapper); // 2 variables in  Settings.bBuilder
        googleApiSettingsBuilder.setLooper(ctx.getMainLooper());
        return googleApiSettingsBuilder.build();
    }

    public static Api<GoogleSignInOptions> buildApiGoogleSignInOptions() {
        return Auth.GOOGLE_SIGN_IN_API;
    }

    public static GoogleSignInOptions buildGoogleSignInOptions() {
        GoogleSignInOptions.Builder gsoBuilder = new GoogleSignInOptions.Builder();
        // Below is equivalent to DEFAULT_SIGN_IN
        gsoBuilder.requestId(); // Adds "openid" Scope to the Set<Scopes>
        gsoBuilder.requestProfile(); // Adds new Scope("profile") to the set of Scopes.
        return gsoBuilder.build();
    }

    // This is created internally within the constructor of GoogleApi
    public static GoogleApiManager buildGoogleApiManager(Context ctx, boolean direct) {
        if (direct) {
            return GoogleApiManager.zab(ctx);
        }
        // Linux Process priority level: -20 highest to 19 (lowest)
        // THREAD_PRIORITY_DEFAULT  = 0
        HandlerThread handlerThread = new HandlerThread("GoogleApiHandler", 9);
        handlerThread.start();
        Looper looperOfHandlerThread = handlerThread.getLooper();
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        Class<?>[] constructorArgsTypes = {Context.class, Looper.class, GoogleApiAvailability.class};
        Object[] constructorArgs = {ctx, looperOfHandlerThread, googleApiAvailability};
        return UtilsReflex.constructInstance(GoogleApiManager.class, constructorArgs, constructorArgsTypes, true);
    }


}
