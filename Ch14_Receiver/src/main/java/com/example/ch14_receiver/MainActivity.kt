package com.example.ch14_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        val batteryPct = level/scale.toFloat() * 100
        binding.tvBatteryPercentage.text = "$batteryPct%"

        // 사용자 기기에 전원이 공급되는 지 알아보는 경우
        val status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        if(status == BatteryManager.BATTERY_STATUS_CHARGING) {
            // 전원이 공급되고 있는 상태
            val chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
            when(chargePlug) {
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
            // 액티비티 컴포넌트에서 인텐트를 시스템에 전달하여 브로드캐스트 리시버를 실행해달라고 요청함
            val intent = Intent(this@MainActivity, MyReceiver::class.java)
            sendBroadcast(intent)
        }
    }
}