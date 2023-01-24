package com.univcert.api;

import com.squareup.okhttp.*;
import org.json.simple.JSONObject;

import java.io.IOException;

public class Library {
    private static String baseURL = "https://univcert.com/api";
    private static OkHttpClient client = new OkHttpClient();
    protected Library() {}

    public static void getDomain(String email, String universityName) throws IOException {
        String url = baseURL + "/try";
        Request.Builder builder = new Request.Builder().url(url).get();

        JSONObject postObj = new JSONObject();
        postObj.put("email", email);
        postObj.put("univName", universityName);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postObj.toJSONString());
        builder.post(requestBody);
        Request request = builder.build();
        // OkHttp 클라이언트로 GET 요청 객체 전송
        Response response = client.newCall(request).execute();
        if (response.isSuccessful() || response.code()==400) {
            // 응답 받아서 처리
            ResponseBody body = response.body();
            if (body != null) {
                System.out.println("Response:" + body.string());
            }
        }
        else
            System.err.println("Error Occurred");
    }
}

