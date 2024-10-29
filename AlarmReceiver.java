package com.example.frame;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.app.PendingIntent;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "YOUR_CHANNEL_ID";
    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive 호출됨"); // 알람 수신 여부 확인

        Intent activityIntent = new Intent(context, AlarmActivity.class);
        activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                activityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE  // 업데이트와 수정 불가 플래그 설정
        );



        // 알림 권한 체크
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "알림 권한이 있음"); // 권한이 있을 때 로그 출력

            // 알림 생성
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.logo_with_transparent_background)
                    .setContentTitle("일어나실 시간이에요.")
                    .setContentText("일어나셨나요?")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)  // 알림 클릭 시 앱으로 이동
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(1, builder.build());
            Log.d(TAG, "알림이 생성되었습니다"); // 알림 생성 성공 로그
        } else {
            Log.d(TAG, "알림 권한이 없음"); // 권한이 없을 때 로그 출력
        }
    }
}
//
//<uses-permission android:name="android.permission.VIBRATE"/>
//<uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>
//<receiver android:name=".AlarmReceiver" />
//<uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM"/>
//<uses-permission android:name="android.permission.USE_EXACT_ALARM"/>
//    추가하기