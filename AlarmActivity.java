package com.example.frame;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AlarmActivity extends AppCompatActivity {
    ip ip = new ip();


    final OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity);

        fetchDataFromUrl("http://"+ ip.ipTime + ":3000/hi?description="
                 + "User님이 기상하였습니다." + "&author=" + "testName");

                Intent intent = new Intent(AlarmActivity.this,HomeActivity.class);
                startActivity(intent);
            }
    private void fetchDataFromUrl(String url) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Error", "Request Failed: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    Log.d("Response", jsonResponse);
                } else {
                    Log.e("Error", "Request Failed. Response Code: " + response.code());
                }
            }
        });
    }


}