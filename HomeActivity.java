package com.example.frame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import android.graphics.Color;


public class HomeActivity extends AppCompatActivity {

    ip ip = new ip();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        ImageButton makeText = findViewById(R.id.makeText);
        ImageButton NavAlarm = findViewById(R.id.nav_alarm);

        //des = findViewById(R.id.des);

        fetchDataFromUrl("http://"+ ip.ipTime + ":3000/");


        makeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,MakeTextActivity.class);
                startActivity(intent);
            }
        });
        NavAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,SeeAlarmActivity.class);
                startActivity(intent);
            }
        });
    }

    private void makeTextView(String description, String created) {
        // LinearLayout을 동적으로 생성 (이전에는 RelativeLayout이었음)
        LinearLayout dynamicLayout = new LinearLayout(this);
        dynamicLayout.setOrientation(LinearLayout.VERTICAL);
        dynamicLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        dynamicLayout.setPadding(16, 16, 16, 16);

        // Profile 이미지 생성 (예시로 고정된 이미지 사용)
        ImageView profileImage = new ImageView(this);
        LinearLayout.LayoutParams profileImageParams = new LinearLayout.LayoutParams(100, 100);
        profileImageParams.setMargins(0, 10, 16, 0);
        profileImage.setLayoutParams(profileImageParams);
        profileImage.setImageResource(R.drawable.testprofile);  // 너의 프로필 이미지로 설정
        profileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // 작성자 이름 텍스트뷰 생성 (예시로 고정된 이름 사용)
        TextView authorName = new TextView(this);
        LinearLayout.LayoutParams authorNameParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        authorNameParams.setMargins(16, 10, 0, 0);
        authorName.setLayoutParams(authorNameParams);
        authorName.setText("testName");  // 예시로 작성자 이름 고정
        authorName.setTextSize(16);
        authorName.setTypeface(null, android.graphics.Typeface.BOLD);

        // 글 내용 텍스트뷰 생성 (description 부분만 서버에서 받아옴)
        TextView postTitle = new TextView(this);
        LinearLayout.LayoutParams postTitleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        postTitleParams.setMargins(16, 10, 0, 0);
        postTitle.setLayoutParams(postTitleParams);
        postTitle.setText(description);  // 서버에서 받아온 description 설정
        postTitle.setTextSize(14);
        postTitle.setTextColor(Color.BLACK); // 텍스트 색상 기본 검정 사용

        // 작성 시간 텍스트뷰 생성 (예시로 고정된 시간 사용)
        TextView postTime = new TextView(this);
        LinearLayout.LayoutParams postTimeParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        postTimeParams.setMargins(16, 10, 0, 0);
        postTime.setLayoutParams(postTimeParams);
        postTime.setText(created);  // 예시로 작성 시간 고정
        postTime.setTextSize(12);
        postTime.setTextColor(Color.GRAY); // 기본 회색 사용

        // LinearLayout에 모든 뷰를 추가
        dynamicLayout.addView(profileImage);
        dynamicLayout.addView(authorName);
        dynamicLayout.addView(postTitle);
        dynamicLayout.addView(postTime);

        // 메인 레이아웃에 동적으로 생성한 LinearLayout을 가장 위에 추가
        LinearLayout mainLayout = findViewById(R.id.makePost);
        mainLayout.addView(dynamicLayout, 0);  // 0번째 위치에 뷰를 추가하여 가장 위에 표시되도록 함
    }


    private void fetchDataFromUrl(String url) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //des.setText("Failed to fetch data");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                //Log.d("ResponseData", responseData);
                try {
                    JSONArray jsonArray = new JSONArray(responseData);
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //Log.d("ResponseData", String.valueOf(jsonObject));

                        String description = jsonObject.getString("description");
                        String author = jsonObject.getString("author");
                        String created = jsonObject.getString("created");
                        //Log.d("ResponseData", description);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //textView.setText(description);
                                makeTextView(description, created);
                            }

                        });

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //des.setText("Failed to parse JSON");
                        }
                    });
                }


            }
        });
    }



}