package com.example.ch15_service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import com.example.ch15_outer.MessengerService
import com.example.ch15_service.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var messenger: Messenger

    val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // bindService() 함수로 서비스를 실행할 때 자동으로 호출됨
            // 두번째 파라미터(service): 서비스에서 넘어온 객체
            messenger = Messenger(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            // unbindService() 함수로 서비스를 종료할 때 자동으로 호출됨
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent("ACTION_SERVICE_Messenger")
        intent.setPackage("com.example.ch15_outer")
        bindService(intent, connection, Context.BIND_AUTO_CREATE)

        // 서비스 실행 후 서비스에 데이터를 전달하고 싶은 경우 -> send 함수를 호출하는 순간 서비스의 handleMessage 가 자동 호출됨
        // IPC 통신에서는 주고받는 데이터는 Parcelable 이나 Bundle 타입이어야 한다.
        // 데이터를 먼저 Bundle에 담고 다시 Message 객체에 담아서 전달해야 한다.
        val bundle = Bundle()
        bundle.putString("string_key", "value1")
        bundle.putInt("int_key", 10)

        val msg = Message()
        msg.what = 10
        msg.obj = bundle
        messenger.send(msg)

        binding.messengerPlayStart.setOnClickListener {

        }

        binding.messengerPlayEnd.setOnClickListener {

        }

        binding.aidlPlayStart.setOnClickListener {

        }

        binding.aidlPlayEnd.setOnClickListener {

        }

    }
}