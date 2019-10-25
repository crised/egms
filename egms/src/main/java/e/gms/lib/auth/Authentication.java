package e.gms.lib.auth;

import android.net.Uri;
import android.os.Build;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import e.gms.lib.utils.Consts;
import e.gms.lib.utils.EGmsLibException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Authentication {

    private static final String TAG = Authentication.class.getName();

    /**
     * Blocking method
     *
     * @param oauth
     * @return
     */
    // TODO: Test this.
    // TODO: Consider making it async, with a callback parameter.
    // Similar to RetrieveRtToken
    public static Map<String, String> getFirstResponseSync(String oauth) {
        Map<String, String> responseMap = null;
        Request request = buildAuthRequest(buildBody(buildStringRequestBody(oauth)));
        OkHttpClient client = new OkHttpClient();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) responseMap = parseResponse(response.body().string());
            else throw new EGmsLibException("Could not authenticate");
            if (responseMap.size() < 2) throw new EGmsLibException("Got authentication error");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return responseMap;
    }


    public static Request buildAuthRequest(RequestBody body) {

        return new Request.Builder()
//                .url("https://postman-echo.com/post")
//                .url("http://dummy.com")
                .url(Consts.AUTH_REQUEST_URL)
                .addHeader(Consts.HTTP_HEADER_NAME_AUTH_REQUEST_USER_AGENT, format_user_agent_header())
                .addHeader(Consts.AUTH_APP, Consts.GMS_PACKAGE_NAME)
                .addHeader(Consts.DEVICE, "0")
                .post(body)
                .build();
    }


    public static RequestBody buildBody(String body) {
        MediaType mediaType = MediaType.get(Consts.HTTP_HEADER_VALUE_FORM_URL_ENCODED);
        return RequestBody.create(body, mediaType);
    }


    public static String buildStringRequestBody(String oauth) {
        StringBuilder sb = new StringBuilder();
        sb.append(pair(Consts.ADD_ACCOUNT, Consts.ANDROID_TRUE));
        sb.append(pair(Consts.ANDROID_ID, 0));
        sb.append(pair(Consts.AUTH_APP, Consts.GMS_PACKAGE_NAME));
        sb.append(pair(Consts.CLIENT_SIG, Consts.GMS_PACKAGE_SIGNATURE_SHA1));
        sb.append(pair(Consts.CALLER_PKG, Consts.GMS_PACKAGE_NAME));
        sb.append(pair(Consts.CALLER_SIG, Consts.GMS_PACKAGE_SIGNATURE_SHA1));
        sb.append(pair(Consts.DEVICE_COUNTRY, Locale.getDefault().getCountry().toLowerCase()));
        sb.append(pair(Consts.GET_ACCOUNT_ID, Consts.ANDROID_TRUE));
        sb.append(pair(Consts.GOOGLE_PLAY_VERSION_NAME, Consts.GOOGLE_PLAY_VERSION_VALUE));
        sb.append(pair(Consts.ACCESS_TOKEN, Consts.ANDROID_TRUE));
        sb.append(pair(Consts.LOCALE_LANG, Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry()));
        sb.append(pair(Consts.OPERATOR_COUNTRY, Locale.getDefault().getCountry().toLowerCase()));
        sb.append(pair(Consts.SDK_VERSION, android.os.Build.VERSION.SDK_INT));
        sb.append(pair(Consts.SERVICE, Consts.SERVICE_AC2DM));
        sb.append(pair(Consts.TOKEN, oauth));
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    private static String pair(Object key, Object value) {
        return Uri.encode(String.valueOf(key)) + "=" + Uri.encode(String.valueOf(value)) + "&";
    }

    /***
     * Sample Response:
     *
     * Token=aas_et/AKp...
     * Auth=pwcxWiTIdYD...
     * SID=pwcxWl...
     * LSID=pwc...
     * services=mail,hist,friendview,talk,cl,android
     * Email=e.foundation@gmail.com
     * GooglePlusUpdate=0
     * firstName=e..
     * lastName=e...
     *
     * @param response
     * @return
     */
    public static Map<String, String> parseResponse(String response) {
        Map<String, String> map = new HashMap<>();
        for (String line : response.split("\n")) {
            String[] pair = line.split("=");
            map.put(pair[0], pair[1]);
        }
        return map;
    }

    public static String format_user_agent_header() {
        return String.format(Consts.HTTP_HEADER_USER_AGENT_FORMAT_IT, Build.DEVICE, Build.ID);
    }

}
