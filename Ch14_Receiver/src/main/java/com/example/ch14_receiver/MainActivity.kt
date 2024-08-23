package com.example.ch14_receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.BatteryManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.example.ch14_receiver.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SecurityException resolve
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()

        // broadcast receiver
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = registerReceiver(null, intentFilter)

        // 사용자 기기의 배터리 충전 상태를 알아보는 경우
        val level = batteryStatus!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        val batteryPct = level / scale.toFloat() * 100
        binding.tvBatteryPercentage.text = "$batteryPct%"

        // 사용자 기기에 전원이 공급되는 지 알아보는 경우
        val status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
            // 전원이 공급되고 있는 상태
            val chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
            when (chargePlug) {
                BatteryManager.BATTERY_PLUGGED_USB -> {
                    // 저속 충전
                    binding.tvPluginStatus.text = "USB Plugged"
                    binding.ivPluginStatus.setImageResource(R.drawable.usb)
                }
                BatteryManager.BATTERY_PLUGGED_AC -> {
                    // 고속 충전
                    binding.tvPluginStatus.text = "AC Plugged"
                    binding.ivPluginStatus.setImageResource(R.drawable.ac)
                }
            }
        } else {
            // 전원이 공급되지 않는 상태
            binding.tvPluginStatus.text = "Not Plugged"
            binding.ivPluginStatus.setImageResource(R.drawable.ic_disconnect)
        }

        // 버튼을 누르면 브로드캐스트 리시버를 실행시켜 알림을 띄운다.
        binding.btnRunReceiver.setOnClickListener {
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

            builder.setSmallIcon(R.drawable.ic_circus)
            builder.setWhen(System.currentTimeMillis())
            builder.setContentTitle("Notification Content Title")
            builder.setContentText("Notification Detail Message")

            manager.notify(24, builder.build())

            // 액티비티 컴포넌트에서 인텐트를 시스템에 전달하여 브로드캐스트 리시버를 실행해달라고 요청함
//            val intent = Intent(this@MainActivity, MyReceiver::class.java)
//            sendBroadcast(intent)
        }
    }
}