package e.app;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import java.util.StringJoiner;

public class Utils {

    public static String toString(Intent intent) {
        StringJoiner sj = new StringJoiner(" ");
        sj.add(intent.toString());
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            sj.add(",Bundle contents: ");
            for (String key : bundle.keySet()) {
                Object obj = bundle.get(key);
                if (obj != null) {
                    sj.add(key.getClass().toString());
                    sj.add(key + ":");
                    sj.add(obj.getClass().toString());
                    if (obj instanceof PendingIntent) sj.add(toString((PendingIntent) obj));
                    else sj.add(String.valueOf(obj));
                }
            }
        }
        if (intent.getAction() != null) sj.add(intent.getAction());
        return sj.toString();
    }

    public static String toString(PendingIntent pendingIntent) {
        StringJoiner sj = new StringJoiner(" ");
        sj.add("Pending Intent:");
        sj.add("creatorPackage").add(pendingIntent.getCreatorPackage());
        sj.add("intent Sender").add(pendingIntent.getIntentSender().getCreatorPackage());
        sj.add("toString").add(pendingIntent.toString());
        sj.add("end of pending intent");
        return sj.toString();
    }
}
