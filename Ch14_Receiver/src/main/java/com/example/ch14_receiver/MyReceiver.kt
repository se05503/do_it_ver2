package com.example.ch14_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // 컴포넌트에서 리시버를 실행하려고 인텐트를 시스템에 전달하면 해당 함수가 자동으로 호출된다.
        // 두번째 매개변수: 자신을 호출한 인텐트 객체
        // onReceive() 함수는 실행한 후 10초 이내에 완료할 것을 권장하므로 오래 걸리는 작업을 담기에는 부적절하다.
        // 알림 띄우는 코드 작성하기
    }
}