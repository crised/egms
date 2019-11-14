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


    public Start(Context appCtx) {
//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
        GoogleSignInOptions.Builder gsoBuilder = new GoogleSignInOptions.Builder();
        // Below is equivalent to DEFAULT_SIGN_IN
        gsoBuilder.requestId(); // Adds "openid" Scope to the Set<Scopes>
        gsoBuilder.requestProfile(); // Adds new Scope("profile") to the set of Scopes.
        GoogleSignInOptions gso = gsoBuilder.build();

        // Craetes a GoogleApi<GoogleSignInOption> descendant object.
//        GoogleSignInClient client = GoogleSignIn.getClient(appCtx, gso); //new GoogleSignInClient(appCtx, gso)
        Api<GoogleSignInOptions> auth_api = Auth.GOOGLE_SIGN_IN_API;
        ApiExceptionMapper apiExceptionMapper = new ApiExceptionMapper();
        GoogleApi<GoogleSignInOptions> googleApi = new GoogleApi<GoogleSignInOptions>(appCtx, auth_api, gso, apiExceptionMapper);

        Log.d(TAG, "Done");

//        new GoogleApi<GoogleSignInOptions>(appCtx,e)


    }
}
