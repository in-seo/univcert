package com.univcert.api;

import com.squareup.okhttp.*;
import org.json.simple.JSONObject;

import java.io.IOException;

public class UnivCert {
    private static String baseURL = "https://univcert.com/api";
    private static OkHttpClient client = new OkHttpClient();
    protected UnivCert() {}

    public static void getDomain(String email, String universityName) throws IOException {
        String url = baseURL + "/try";
        Request.Builder builder = new Request.Builder().url(url).get();

        JSONObject postObj = new JSONObject();
        postObj.put("email", email);
        postObj.put("univName", universityName);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postObj.toJSONString());
        builder.post(requestBody);
        Request request = builder.build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful() || response.code()==400) {
            ResponseBody body = response.body();
            if (body != null) {
                System.out.println("응답:" + body.string());
            }
        }
        else
            System.err.println("Error Occurred");
    }
}

