package e.app;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.internal.BaseGmsClient;

public class MyGmsClient extends BaseGmsClient {

    public MyGmsClient(Context context, Looper looper, int i, BaseConnectionCallbacks baseConnectionCallbacks, BaseOnConnectionFailedListener baseOnConnectionFailedListener, String s) {
        super(context, looper, i, baseConnectionCallbacks, baseOnConnectionFailedListener, s);
    }

    @NonNull
    @Override
    protected String getStartServiceAction() {
        return null;
    }

    @NonNull
    @Override
    protected String getServiceDescriptor() {
        return null;
    }

    @Nullable
    @Override
    protected IInterface createServiceInterface(IBinder iBinder) {
        return null;
    }
}
