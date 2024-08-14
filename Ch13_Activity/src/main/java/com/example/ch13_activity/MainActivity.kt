package com.example.ch13_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ch13_activity.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SecurityException resolve
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()

        // actionbar 내용을 toolbar 에 적용
        setSupportActionBar(binding.toolbar)

        binding.extendedFab.setOnClickListener {
            // 할 일 등록 화면(AddActivity)로 이동 → intent 사용
            val intent: Intent = Intent(this@MainActivity, AddActivity::class.java) // 컴포넌트의 정보를 intent에 담음, 클래스 타입 레퍼런스 정보
            startActivity(intent) // 해당 함수가 intent 를 시스템에 전달
        }
    }
}