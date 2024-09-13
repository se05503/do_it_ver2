package com.example.ch15_outer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class MyAIDLService : Service() {

    override fun onBind(intent: Intent?): IBinder {
        /*
        onBind 함수에서 bindService 를 실행한 컴포넌트에 인텐트로 서비스를 전달해야함
        이때, AIDL 파일을 구현한 객체가 아닌 프로세스 간 통신을 대행해주는 Stub를 전달해야함
        즉, 실제 로직이 구현된 객체가 아니라 프로세스 간 통신을 대행해주는 객체를 전달해야함
        -> MyAIDLInterface.Stub() 를 쓴 이유
        */

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder

        // 알림 띄우기
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 채널 생성
            val channelId = "channel's id"
            val channelName = "channel's name"
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )

            // 채널에 대한 추가적인 정보 설정
            channel.description = "channel's description"
            channel.setShowBadge(true)
            val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()
            channel.setSound(uri, audioAttributes)
            channel.enableLights(true) // 불빛 표시
            channel.lightColor = Color.RED // enableLights 값이 true 일 경우, 표시할 불빛의 색상
            channel.enableVibration(true) // 진동을 울릴 것인가?
            channel.vibrationPattern = longArrayOf(100, 200, 100, 200) // 1초 쉬고 2초 울리고 1초 쉬고 2초 울리고

            // 채널을 NotificationManager에 등록
            manager.createNotificationChannel(channel)

            // 채널을 이용한 빌더 생성
            builder = NotificationCompat.Builder(this, channelId)
        } else {
            builder = NotificationCompat.Builder(this)
        }

        builder.setSmallIcon(R.drawable.ic_android)
        builder.setWhen(System.currentTimeMillis())
        builder.setContentTitle("Notification Content Title")
        builder.setContentText("Notification Detail Message")

        val notification = builder.build()
        startForeground(1, notification)

        return object : MyAIDLInterface.Stub() {
            override fun funA(data: String?) {
                TODO("Not yet implemented")
            }
            override fun funB(): Int {
                TODO("Not yet implemented")
            }

        }
    }
}