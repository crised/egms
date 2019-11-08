package e.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "hi");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance(); // this can live within the library app

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        int connectionResult = apiAvailability.isGooglePlayServicesAvailable(this);

        if (connectionResult == 0) Log.e(TAG, "SUCCESS");
        else Log.e(TAG, "ERROR");
        if (connectionResult == 9) Log.d(TAG, "SERVICE INVALID");
        signInFlow();
        new Thread() {
            @Override
            public void run() {
                getAdsInfo();
            }
        }.run();
    }

    private void getAdsInfo() {
        try {
            AdvertisingIdClient.Info info = AdvertisingIdClient.getAdvertisingIdInfo(this);
            Log.d(TAG, info.toString());
        } catch (IOException | GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException e) {
            Log.e(TAG, "Exception!");
        }
    }

    private void signInFlow() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient client = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = client.getSignInIntent();
        Utils.toString(signInIntent);
        Log.d(TAG, "crised intent " + String.valueOf(signInIntent));
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

//
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount.createDefault();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }
}
