package com.zilleyy.jda.bridge;

import okhttp3.*;
import okhttp3.Request.Builder;

import java.io.IOException;

import static com.zilleyy.jda.console.ConsoleLogger.send;

/**
 * Author: Zilleyy
 * <br>
 * Date: 1/04/2021 @ 2:09 pm AEST
 */
public class Rest {

    private static OkHttpClient client = new OkHttpClient.Builder()
            .build();

    private static final String URL = "http://localhost:8080/";

    public static String post(String path) {
        try {
            MediaType JSON = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(JSON, "");
            Request request = new Request.Builder().url(URL + path).post(body).build();
            Response response = Rest.client.newCall(request).execute();
            try {
                return response.message();
            } finally {
                // Close the response after the message has been returned.
                response.close();
            }
        } catch(Exception exception) {
            send("An error occurred whilst trying to post a message to the RestAPI with the path " + path + ", either could not connect or path was invalid.");
            return null;
        }
    }

    public static String get(String path) {
        Request request = new Builder().url(URL + path).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                // ... handle failed request
            }
            String responseBody = response.body().string();
            return responseBody;

        } catch (IOException e) {
            // ... handle IO exception
        }
        return "";
    }

}
