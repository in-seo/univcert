package com.univcert.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * https://univcert.com   메일 및 대학 인증 API.
 * 개발자분은 해당 사이트에서 API 키 발급 후 사용 하시면 됩니다.
 * https://github.com/in-seo/univcert  에 자세한 내용 있고,
 * 도움이 되셨다면 스타 한번 눌러주시면 큰 힘이 됩니다. 많이 퍼뜨려 주세요 감사합니다 :)
 */
public class UnivCert {
    /** 모든 반환 값은 Map<String, Object>로 드립니다. 반환 값을 .get("success") 와 같은 메서드로 뽑아 쓰시면 됩니다. */
    private static final String baseURL = "https://univcert.com/api";
    private static final OkHttpClient client = new OkHttpClient();
    private static final JSONParser parser = new JSONParser();
    protected UnivCert() {}

    /** ✉ 이용자 메일 인증 시작 (인증코드 발송) */
    public static Map<String, Object> certify(String API_KEY, String email, String universityName, boolean univ_check) throws IOException {
        String url = baseURL + "/v1/certify";
        Request.Builder builder = new Request.Builder().url(url).get();

        JSONObject postObj = new JSONObject();
        postObj.put("key", API_KEY);
        postObj.put("email", email);
        postObj.put("univName", universityName);
        postObj.put("univ_check", univ_check); /** true -> 대학 도메인까지, false -> 단순 메일 인증만 */

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postObj.toJSONString());
        builder.post(requestBody);
        Request request = builder.build();

        Response responseHTML = client.newCall(request).execute();

        return parseHTMLToJSON(responseHTML);
    }
    /** ✅ 이용자 메일에 발송된 코드를 전달 받아 인증 받기 */
    public static Map<String, Object> certifyCode(String API_KEY, String email, String universityName, String code) throws IOException {
        String url = baseURL + "/v1/certifycode";
        Request.Builder builder = new Request.Builder().url(url).get();

        JSONObject postObj = new JSONObject();
        postObj.put("key", API_KEY);
        postObj.put("email", email);
        postObj.put("univName", universityName);
        postObj.put("code", code);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postObj.toJSONString());
        builder.post(requestBody);
        Request request = builder.build();

        Response responseHTML = client.newCall(request).execute();

        return parseHTMLToJSON(responseHTML);
    }

    /** 📂 인증된 이메일인지 확인 기능 */
    public static Map<String, Object> status(String API_KEY, String email) throws IOException {
        String url = baseURL + "/v1/status";
        Request.Builder builder = new Request.Builder().url(url).get();

        JSONObject postObj = new JSONObject();
        postObj.put("key", API_KEY);
        postObj.put("email", email);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postObj.toJSONString());
        builder.post(requestBody);
        Request request = builder.build();

        Response responseHTML = client.newCall(request).execute();

        return parseHTMLToJSON(responseHTML);
    }

    /** 📜 해당 API 키로 인증된 유저 리스트 출력 */
    public static Map<String, Object> list(String API_KEY) throws IOException {
        String url = baseURL + "/v1/certifiedlist";
        Request request = makeRequest(API_KEY, url);
        Response responseHTML = client.newCall(request).execute();

        return parseHTMLToJSON(responseHTML);
    }

    /** ⚠️인증 가능한 대학교 명인지 체킹 */
    public static Map<String, Object> check(String universityName) throws IOException {
        String url = baseURL + "/v1/check";
        Request.Builder builder = new Request.Builder().url(url).get();

        JSONObject postObj = new JSONObject();
        postObj.put("univName", universityName);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postObj.toJSONString());
        builder.post(requestBody);
        Request request = builder.build();

        Response responseHTML = client.newCall(request).execute();

        return parseHTMLToJSON(responseHTML);
    }

    /** 🆕 현재 인증 된 유저목록 초기화 */
    public static Map<String, Object> clear(String API_KEY) throws IOException {
        String url = baseURL + "/v1/clear";
        Request request = makeRequest(API_KEY, url);

        Response responseHTML = client.newCall(request).execute();

        return parseHTMLToJSON(responseHTML);
    }

    /** 📛 현재 인증 된 "특정" 유저 초기화 */
    public static Map<String, Object> clear(String API_KEY, String email) throws IOException {
        String url = baseURL + "/v1/clear/"+email;
        Request request = makeRequest(API_KEY, url);

        Response responseHTML = client.newCall(request).execute();

        return parseHTMLToJSON(responseHTML);
    }

    private static Request makeRequest(String API_KEY, String url) {
        Request.Builder builder = new Request.Builder().url(url).get();

        JSONObject postObj = new JSONObject();
        postObj.put("key", API_KEY);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postObj.toJSONString());
        builder.post(requestBody);
        Request request = builder.build();
        return request;
    }

    /** 무슨 오류인지, 어떤 응답이 오는지 알고 싶으시다면 해당 클래스를 상속 및 재정의 하거나, http로 JSON요청을 직접 진행하시면 됩니다. */
    private static Map<String, Object> parseHTMLToJSON(Response responseHTML) {
        ResponseBody body = responseHTML.body();
        Map map = new HashMap<>();
        try{
            if (body != null) {
                JSONObject response = (JSONObject) parser.parse(body.string());
                response.put("code", responseHTML.code());
                System.out.println(response.toJSONString());
                map = new ObjectMapper().readValue(response.toJSONString(), Map.class) ;
                return map;
            }
        }
        catch(Exception e){
            System.out.println("json 오류");
            return map; /** 오류 시 빈 맵 */
        }
        return map;
    }
}

