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

// TODO: Add debug Logs Request and Response.
public class Authentication {

    private static final String TAG = Authentication.class.getName();

    private OkHttpClient okHttp;
    private Map<String, String> responseMap;
    private short connectionNumber;
    private String oAuthToken;


    private Authentication() {
        this.okHttp = new OkHttpClient();
    }

    public Authentication(String oAuthToken) {
        this();
        this.oAuthToken = oAuthToken;
        this.responseMap = new HashMap<>();
        startAuthCommunication(false);
    }

    public Authentication(Map<String, String> responseMap) {
        this();
        this.responseMap = responseMap;
        startAuthCommunication(true);
    }

    private void startAuthCommunication(boolean onlySecondCall) {
        try {
            if (!onlySecondCall) executeRequest();
            else connectionNumber = 1; // 1-> Second Connection
            executeRequest();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage() + ", " + responseMap.toString());
            responseMap = null;
        }
    }

    /**
     * Blocking method
     *
     * @param
     * @return
     */
    // TODO: Test this.
    // TODO: Consider making it async, with a callback parameter.
    // Similar to RetrieveRtToken
    // TODO: RequestStrings can be unified easily if order doesn't matter.
    public void executeRequest() throws Exception {
        Request request;
        if (connectionNumber == 0) request = buildAuthRequest(buildFirst());
        else request = buildAuthRequest(buildSecond());
        Log.d(TAG, "Request Header: " + request.toString());
        Log.d(TAG, "Request Body: " + request.body().toString());
        Response response = this.okHttp.newCall(request).execute();
        if (response.code() == 200) parseResponse(response.body().string());
        else throw new EGmsLibException("Could not authenticate");
    }

    public Request buildAuthRequest(String bodyString) {
        MediaType mediaType = MediaType.get(Consts.HTTP_HEADER_VALUE_FORM_URL_ENCODED);
        RequestBody body = RequestBody.create(bodyString, mediaType);
        return new Request.Builder()
//                .url("https://postman-echo.com/post")
//                .url("http://dummy.com")
                .url(Consts.AUTH_REQUEST_URL)
                .header(Consts.HTTP_HEADER_NAME_AUTH_REQUEST_USER_AGENT, formatUserAgentHeader())
                .addHeader(Consts.AUTH_APP, Consts.GMS_PACKAGE_NAME)
                .addHeader(Consts.DEVICE, getAndroidId())
                .post(body)
                .build();
    }


    public String buildFirst() {
        StringBuilder sb = new StringBuilder();
        sb.append(pair(Consts.ADD_ACCOUNT, Consts.ANDROID_TRUE));
        sb.append(pair(Consts.ANDROID_ID, getAndroidId()));
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
        sb.append(pair(Consts.TOKEN, this.oAuthToken)); // Receives
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public String buildSecond() throws EGmsLibException {
        final String first_request_id = "_0";
        String aas_et_token = this.responseMap.get(Consts.TOKEN + first_request_id);
        String Email = this.responseMap.get(Consts.EMAIL + first_request_id);
        if (aas_et_token == null || Email == null)
            throw new EGmsLibException("First response values are wrong.");
        StringBuilder sb = new StringBuilder();
        sb.append(pair(Consts.ADD_ACCOUNT, Consts.ANDROID_TRUE));
        sb.append(pair(Consts.ANDROID_ID, getAndroidId())); // Set to 0 before checkin.
        sb.append(pair(Consts.AUTH_APP, Consts.GMS_PACKAGE_NAME));
        sb.append(pair(Consts.CLIENT_SIG, Consts.GMS_PACKAGE_SIGNATURE_SHA1));
        sb.append(pair(Consts.CALLER_PKG, Consts.GMS_PACKAGE_NAME));
        sb.append(pair(Consts.CALLER_SIG, Consts.GMS_PACKAGE_SIGNATURE_SHA1));
        sb.append(pair(Consts.DEVICE_COUNTRY, Locale.getDefault().getCountry().toLowerCase()));
        sb.append(pair(Consts.EMAIL, Email)); // Not present in first request
        sb.append(pair(Consts.GET_ACCOUNT_ID, Consts.ANDROID_TRUE));
        sb.append(pair(Consts.GOOGLE_PLAY_VERSION_NAME, Consts.GOOGLE_PLAY_VERSION_VALUE));
//        sb.append(pair(Consts.ACCESS_TOKEN, Consts.ANDROID_TRUE)); Not present in this one.
        sb.append(pair(Consts.HAS_PERMISSION, Consts.ANDROID_TRUE)); // Not present in first request
        sb.append(pair(Consts.LOCALE_LANG, Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry()));
        sb.append(pair(Consts.OPERATOR_COUNTRY, Locale.getDefault().getCountry().toLowerCase()));
        sb.append(pair(Consts.SDK_VERSION, android.os.Build.VERSION.SDK_INT));
        sb.append(pair(Consts.SERVICE, Consts.SERVICE_AC2DM));
        sb.append(pair(Consts.SYSTEM_PARTITION, Consts.ANDROID_TRUE));//// Not present in first request
        sb.append(pair(Consts.TOKEN, aas_et_token));// Different token, not oauth token.
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    /***
     * First Response Sample:
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
     * Second Response Sample:
     *
     * SID=pwcxWpTqHuCjhuq2kOm2_J_lcJnSV2z8MvVC_jJG_af40iRPDtJv8o2a9SDtRGCEB_TuKw.
     * LSID=pwcxWmAI0pnzzUO3Qnlp8ITP408DEc85WV14jAmijwrf_-gCoGNAkDC8zA3AwDgG2Vbk4A.
     * Auth=pwcxWvL1xOlqphNQxefJGiprCKfR45MQCsdFYOXOE-nQTcZFk4bhX55cIbuFNtcTUfs7aA.
     * issueAdvice=auto
     * services=mail,hist,friendview,talk,cl,android
     * GooglePlusUpdate=0
     * Email=crised.e.foundation@gmail.com
     * firstName=crised
     * lastName=e
     *
     *
     * @param response
     * @return
     */
    public void parseResponse(String response) {
        Log.d(TAG, "Response " + connectionNumber + ":\n" + response);
        for (String line : response.split("\n")) {
            String[] pair = line.split("=");
            responseMap.put(pair[0] + "_" + connectionNumber, pair[1]);
        }
        connectionNumber += 1;
    }

    public String formatUserAgentHeader() {
        return String.format(Consts.HTTP_HEADER_USER_AGENT_FORMAT_IT, Build.DEVICE, Build.ID);
    }

    // TODO: Implement this, after the first checkin.
    public String getAndroidId() {
        return "0";
    }

    private static String pair(Object key, Object value) {
        return Uri.encode(String.valueOf(key)) + "=" + Uri.encode(String.valueOf(value)) + "&";
    }

    /***
     * Parsed sample data return:
     * {Token_0=aas_et/AKppINb8WnpK,
     * services_1=mail,hist,friendview,talk,cl,android,
     * issueAdvice_1=auto,
     * Auth_1=pwcxWv964I,
     * Auth_0=pwcxWt_r4gXdhWEVkcX,
     * SID_0=pwcxWmwK6bziNritrI-GUux,
     * GooglePlusUpdate_1=0,
     * GooglePlusUpdate_0=0,
     * LSID_1=pwcxWqt7v,
     * SID_1=pwcxWvk_t,
     * lastName_1=e,
     * lastName_0=e,
     * LSID_0=pwcxWj,
     * Email_1=e@gmail.com,
     * firstName_0=c,
     * Email_0=e@gmail.com,
     * firstName_1=c,
     * services_0=mail,hist,friendview,talk,cl,android}
     * @return
     */
    public Map<String, String> getResponseMap() {
        return responseMap;
    }

}
