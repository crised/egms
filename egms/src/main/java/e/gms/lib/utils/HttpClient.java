package e.gms.lib.utils;

import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpClient {


    //    String email, String oAuthToken
    public Request buildAuthRequest(RequestBody body, String user_agent) {

        return new Request.Builder()
//                .url("https://postman-echo.com/post")
//                .url("http://dummy.com")

                .url(Consts.AUTH_REQUEST_URL)
//                .header(Consts.HTTP_HEADER_NAME_CONTENT_TYPE, Consts.HTTP_HEADER_VALUE_FORM_URL_ENCODED)
                .addHeader(Consts.HTTP_HEADER_NAME_AUTH_REQUEST_USER_AGENT, Consts.HTTP_HEADER_VALUE_GOOGLE_AUTH)
                .addHeader(Consts.AUTH_APP, Consts.GMS_PACKAGE_NAME)
                // Should be 0 at this point
                .addHeader(Consts.DEVICE, "0")
                .post(body)
                .build();

    }

}
