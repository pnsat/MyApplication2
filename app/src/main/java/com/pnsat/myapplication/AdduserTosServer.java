package com.pnsat.myapplication;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Created by ubc15 on 1/2/2017.
 */

public class AdduserTosServer extends AsyncTask<Void, Void, String> {

    // Explisit

    private Context context;
    private static final String urlPHP = "http://swiftcodingthai.com/off/add_user_master.php";
    private String nameString, userString, passwordString, imageString;

    public AdduserTosServer(Context context,
                            String nameString,
                            String userString,
                            String passwordString,
                            String imageString) {
        this.context = context;
        this.nameString = nameString;
        this.userString = userString;
        this.passwordString = passwordString;
        this.imageString = imageString;
    }

    @Override
    protected String doInBackground(Void... params) {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("Name", nameString)
                    .add("User", userString)
                    .add("Password", passwordString)
                    .add("Image", imageString)
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(urlPHP).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();





        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}//Main Class

