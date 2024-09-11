package com.example.ch15_service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.ch15_outer.MessengerService
import com.example.ch15_service.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        외부 앱(outer)이 백그라운드 상태이면 서비스를 실행할 수 없음
        method 1) startService()로 서비스 실행
        서비스 종료: stopService(intent)

        val intent = Intent("ACTION_SERVICE_Messenger")
        intent.setPackage("com.example.ch15_outer")
        startService(intent)
         */

        /*
        method 2 - A) bindService() 함수로 실행
        third parameter : Int type flags 으로 대부분 Context.BIND_AUTO_CREATE 를 사용
        Context.BIND_AUTO_CREATE : 서비스가 실행 상태가 아니더라도 객체를 생성해서 실행하라는 의미
        만약 해당 flags 로 지정하지 않으면 인텐트를 시스템에 전달해도 서비스가 실행 상태가 아니면 동작하지 않음
        unbindService(connection) -> 서비스 종료
        */
        val connection: ServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                // bindService() 함수로 서비스를 실행할 때 자동으로 호출됨
                // 두번째 파라미터(service): 서비스의 onBind() 함수에서 바인딩된 Binder 객체
                val serviceBinder = service as MessengerService.MessengerBinder
                serviceBinder.temp(20)
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                // unbindService() 함수로 서비스를 종료할 때 자동으로 호출됨
            }
        }

        /*
        method 2 - B) API에서 제공하는 Messenger 객체 바인딩
        Messenger 객체를 이용하는 방법은 IPC(inter-process communication) 할 때 사용할 수 있다.
        외부 앱과 연동하여 프로세스끼리 통신할 때도 사용한다.
        프로세스간 통신 방법: 1) AIDL 2) Messenger -> AIDL 보다 간단한 코드
         */

        val intent = Intent("ACTION_SERVICE_Messenger")
        intent.setPackage("com.example.ch15_outer")

        bindService(intent, connection, Context.BIND_AUTO_CREATE)

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