package e.app.chain;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.ApiExceptionMapper;

public class Start {

    private static final String TAG = Start.class.getName();


    public Start(Context ctx) {
//        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        GoogleSignIn.getClient(appCtx, gso); //new GoogleSignInClient(appCtx, gso), GoogleApi<GoogleSignInOption> descendan
        GoogleSignInOptions gso = CustomBuilder.buildGoogleSignInOptions();
        Api<GoogleSignInOptions> auth_api = CustomBuilder.buildApiGoogleSignInOptions();
        GoogleApi.Settings googleApiSettings = CustomBuilder.buildGoogleApiSettings(ctx); //this class has a Status Exception Mapper, Account(null) and looper.
        GoogleApi<GoogleSignInOptions> googleApi = new GoogleApi<GoogleSignInOptions>(ctx, auth_api, gso, googleApiSettings);

//            public GoogleApi(@NonNull Context var1, Api<O> var2, @Nullable O var3, GoogleApi.Settings var4) {
        Log.d(TAG, "Done");

//        new GoogleApi<GoogleSignInOptions>(appCtx,e)


    }


}
