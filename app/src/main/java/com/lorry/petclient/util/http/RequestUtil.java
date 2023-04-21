package com.lorry.petclient.util.http;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RequestUtil {

    public static JSONObject sendHttpRequest(URL url, String method, JSONObject requests) {
        JSONObject jsonObject = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setConnectTimeout(6000);
            connection.setReadTimeout(6000);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.getOutputStream().write(requests.toString().getBytes(StandardCharsets.UTF_8));
            connection.connect();
            if (connection.getResponseCode() == -1) throw new Exception("请求错误");
            if (connection.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder res = new StringBuilder();
                String line = null;
                while (true) {
                    line = reader.readLine();
                    if (line == null) break;
                    res.append(line);
                }
                jsonObject = new JSONObject(res.toString());
                reader.close();
            }
            connection.disconnect();
            if (jsonObject != null && jsonObject.getJSONObject("message").getInt("code") == 200)
                return jsonObject.getJSONObject("message");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
