package com.univcert.api;

import com.squareup.okhttp.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class UnivCert {
    private static String baseURL = "https://univcert.com/api";
    private static OkHttpClient client = new OkHttpClient();
    private static JSONParser parser = new JSONParser();
    protected UnivCert() {}

    public static String checkDomain(String email, String universityName) throws IOException, ParseException {
        String url = baseURL + "/try";
        Request.Builder builder = new Request.Builder().url(url).get();

        JSONObject postObj = new JSONObject();
        postObj.put("email", email);
        postObj.put("univName", universityName);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postObj.toJSONString());
        builder.post(requestBody);
        Request request = builder.build();

        Response responseHTML = client.newCall(request).execute();
        
        if (responseHTML.isSuccessful() || responseHTML.code()==400)
            return parseHTMLToJSON(responseHTML);

        return "";
    }

    private static String parseHTMLToJSON(Response responseHTML) throws ParseException, IOException {
        ResponseBody body = responseHTML.body();
        if (body != null) {
            JSONObject response = (JSONObject) parser.parse(body.string());
            response.put("code", responseHTML.code());
            return response.toJSONString();
        }
        return "";
    }
}

