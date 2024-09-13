package com.example.ch15_service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.Messenger
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ch15_outer.MyAIDLInterface
import com.example.ch15_service.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var messenger: Messenger
    lateinit var aidlService: MyAIDLInterface

    val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // bindService() 함수로 서비스를 실행할 때 자동으로 호출됨
            // 두번째 파라미터(service): 서비스에서 전달한 객체
            // 해당 객체는 AIDL이 목적이므로 서비스를 포함하는 앱(Outer)과 프로세스 간 통신을 대행해주는 Stub임
            // 해당 객체를 AIDL 파일에 선언한 인터페이스 타입으로 받으면 됨
            aidlService = MyAIDLInterface.Stub.asInterface(service)
            // AIDL을 제공하는 앱과 연동해야 할 때 인터페이스의 함수만 호출하면 됨
            aidlService.funB()
            aidlService.funA("Service!")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            // unbindService() 함수로 서비스를 종료할 때 자동으로 호출됨
            Log.d("Service","disconnected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Android Version 8 부터 서비스에서 백그라운드 제약 발생
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent("ACTION_SERVICE_AIDL")
            intent.setPackage("com.example.ch15_outer")
            // 앱이 백그라운드 상황일 때도 서비스를 실행하겠다!
            startForegroundService(intent)
        } else {
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }

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