package com.example.ch13_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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

        // ActivityResultLauncher
        val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            // Contract 객체 (실제 작업자)
            ActivityResultContracts.StartActivityForResult()
        ) {
            // Callback 객체 (결과 처리)
            val todoList = it.data?.getStringExtra("Todo")
            Log.d("왜 안왔지?", todoList!!)
            binding.tvTemp.text = "result: $todoList"
        }

        binding.extendedFab.setOnClickListener {
            // 할 일 등록 화면(AddActivity)로 이동 → intent 사용
            val intent = Intent(this@MainActivity, AddActivity::class.java) // 컴포넌트의 정보를 intent에 담음, 클래스 타입 레퍼런스 정보
            requestLauncher.launch(intent) // 해당 함수가 intent 를 시스템에 전달
        }

    }
}