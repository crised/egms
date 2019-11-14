package e.app;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.google.android.gms.common.internal.IGmsCallbacks;
import com.google.android.gms.common.internal.zzb;

public class MyIGmsCallback extends IGmsCallbacks.zza {

    private static String TAG = MyIGmsCallback.class.getName();

    @Override
    public void onPostInitComplete(int i, IBinder iBinder, Bundle bundle) throws RemoteException {
        Log.d(TAG, "onPostInitComplete");

    }

    @Override
    public void zza(int i, Bundle bundle) throws RemoteException {
        Log.d(TAG, "zza");


    }

    @Override
    public void zza(int i, IBinder iBinder, zzb zzb) throws RemoteException {
        Log.d(TAG, "zza zzb");

    }

    @Override
    public IBinder asBinder() {
        IBinder iBinder = super.asBinder();

        return super.asBinder();
    }
}
