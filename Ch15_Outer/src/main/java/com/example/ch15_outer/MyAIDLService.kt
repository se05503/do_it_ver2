package com.example.ch15_outer

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyAIDLService : Service() {
    override fun onBind(intent: Intent?): IBinder {
        /*
        onBind 함수에서 bindService 를 실행한 컴포넌트에 인텐트로 서비스를 전달해야함
        이때, AIDL 파일을 구현한 객체가 아닌 프로세스 간 통신을 대행해주는 Stub를 전달해야함
        즉, 실제 로직이 구현된 객체가 아니라 프로세스 간 통신을 대행해주는 객체를 전달해야함
        -> MyAIDLInterface.Stub() 를 쓴 이유
        */
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