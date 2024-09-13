package com.example.ch15_service

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.SystemClock
import android.util.Log

class MyJobService: JobService() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartJob(jobParameters: JobParameters): Boolean {
        /*
        백그라운드에서 처리할 작업 구현
        false: 백그라운드 작업이 완전히 종료되었음 -> 시스템은 onStopJob 을 호출하지 않고 onDestroy 를 호출해 서비스 종료함
        true: 백그라운드 작업이 아직 끝나지 않았음 -> 오랜 시간 살아있는 서비스.
        오래 걸리는 작업을 스레드 등에서 처리하고 끝날 때 jobFinish() 함수를 호출함.
        아래 예시: onStartJob 함수는 끝났지만 스레드가 종료될 때까지 서비스는 종료되지 않으며,
         */

        // 전달된 데이터 받아오기
        jobParameters.extras.getString("key")

        Thread(Runnable {
            var sum = 0
            for(i in 1..10) {
                sum += i
                SystemClock.sleep(1000)
            }
            Log.d("result","thread result : $sum")
            jobFinished(jobParameters, false)
        }).start()
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        /*
        작업이 정상으로 처리되지 않았을 때 호출된다.
        (정상적으로 처리되지 않은) 작업을 다시 시스템에 등록하거나 취소할지를 onStopJob 함수의 반환값으로 지정한다.
        false: 잡 스케줄러 등록을 취소한다.
        true: 잡 스케줄러를 재등록한다.
         */
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}