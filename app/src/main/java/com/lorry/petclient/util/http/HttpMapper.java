package com.lorry.petclient.util.http;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class HttpMapper {

    private static String baseUrl = "http://10.0.2.2:9999";

    private static String GET = "GET";

    private static String POST = "POST";

    public static void getPostInfo(String ID, CallbackFunction callback) {
        new Thread() {
            @Override
            public void run() {
                try {
                    if (ID == null) {
                        URL url = new URL(baseUrl + "/post/info");
                        JSONObject jsonObject = new JSONObject();
                        JSONObject response = RequestUtil.sendHttpRequest(url, POST, jsonObject);
                        callback.run(response);
                    } else {
                        URL url = new URL(baseUrl + "/post/info");
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("ID", ID);
                        JSONObject response = RequestUtil.sendHttpRequest(url, POST, jsonObject);
                        callback.run(response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void getPostImage(String ID, String image, CallbackFunction callback) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(baseUrl + "/post/info/img");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("ID", ID);
                    jsonObject.put("image", image);
                    JSONObject response = RequestUtil.sendHttpRequest(url, POST, jsonObject);
                    callback.run(response);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public interface CallbackFunction {
        void run(JSONObject data);
    }

    public static void getUserInfo(String ID, CallbackFunction callback) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(baseUrl + "/user/info");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("ID", ID);
                    JSONObject response = RequestUtil.sendHttpRequest(url, POST, jsonObject);
                    callback.run(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void getUserAvatar(String ID, CallbackFunction callback) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(baseUrl + "/user/info/avatar");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("ID", ID);
                    JSONObject response = RequestUtil.sendHttpRequest(url, POST, jsonObject);
                    callback.run(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void getUserBackground(String ID, CallbackFunction callback) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(baseUrl + "/user/info/background");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("ID", ID);
                    JSONObject response = RequestUtil.sendHttpRequest(url, POST, jsonObject);
                    callback.run(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
