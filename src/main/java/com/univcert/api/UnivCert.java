package com.univcert.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UnivCert {
    private static final String baseURL = "https://univcert.com/api";
    private static final OkHttpClient client = new OkHttpClient();
    private static final JSONParser parser = new JSONParser();
    protected UnivCert() {}

    public static Map<String, Object> checkDomain(String email, String universityName) throws IOException, ParseException {
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

        return new HashMap<>();
    }

    public static Map<String, Object> certify(String API_KEY, String email, String universityName, boolean univ_check) throws IOException, ParseException {
        String url = baseURL + "/certify";
        Request.Builder builder = new Request.Builder().url(url).get();

        JSONObject postObj = new JSONObject();
        postObj.put("key", API_KEY);
        postObj.put("email", email);
        postObj.put("univName", universityName);
        postObj.put("univ_check", univ_check); /** true -> 대학 도메인까지, false -> 단순 메일 인증만 **/

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postObj.toJSONString());
        builder.post(requestBody);
        Request request = builder.build();

        Response responseHTML = client.newCall(request).execute();

        if (responseHTML.isSuccessful() || responseHTML.code()==400)
            return parseHTMLToJSON(responseHTML);

        return new HashMap<>();
    }

    public static Map<String, Object> certifyCode(String API_KEY, String email, String universityName, int code) throws IOException, ParseException {
        String url = baseURL + "/certify";
        Request.Builder builder = new Request.Builder().url(url).get();

        JSONObject postObj = new JSONObject();
        postObj.put("key", API_KEY);
        postObj.put("email", email);
        postObj.put("univName", universityName);
        postObj.put("code", code); /** true -> 대학 도메인까지, false -> 단순 메일 인증만 **/

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postObj.toJSONString());
        builder.post(requestBody);
        Request request = builder.build();

        Response responseHTML = client.newCall(request).execute();

        if (responseHTML.isSuccessful() || responseHTML.code()==400)
            return parseHTMLToJSON(responseHTML);

        return new HashMap<>();
    }

    public static Map<String, Object> status(String API_KEY, String email) throws IOException, ParseException {
        String url = baseURL + "/status";
        Request.Builder builder = new Request.Builder().url(url).get();

        JSONObject postObj = new JSONObject();
        postObj.put("key", API_KEY);
        postObj.put("email", email);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postObj.toJSONString());
        builder.post(requestBody);
        Request request = builder.build();

        Response responseHTML = client.newCall(request).execute();

        if (responseHTML.isSuccessful() || responseHTML.code()==400)
            return parseHTMLToJSON(responseHTML);

        return new HashMap<>();
    }

    public static Map<String, Object> list(String API_KEY) throws IOException, ParseException {
        String url = baseURL + "/status";
        Request.Builder builder = new Request.Builder().url(url).get();

        JSONObject postObj = new JSONObject();
        postObj.put("key", API_KEY);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postObj.toJSONString());
        builder.post(requestBody);
        Request request = builder.build();

        Response responseHTML = client.newCall(request).execute();

        if (responseHTML.isSuccessful() || responseHTML.code()==400)
            return parseHTMLToJSON(responseHTML);

        return new HashMap<>();
    }

    private static Map<String, Object> parseHTMLToJSON(Response responseHTML) throws ParseException, IOException {
        ResponseBody body = responseHTML.body();
        Map map = new HashMap<>();
        if (body != null) {
            JSONObject response = (JSONObject) parser.parse(body.string());
            response.put("code", responseHTML.code());
            map = new ObjectMapper().readValue(response.toJSONString(), Map.class) ;
            return map;
        }
        return map;
    }
}
