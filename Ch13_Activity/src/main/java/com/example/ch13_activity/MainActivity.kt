package com.example.ch13_activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.ch13_activity.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var requestLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SecurityException resolve
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()

        // actionbar 내용을 toolbar 에 적용
        setSupportActionBar(binding.toolbar)

        // ActivityResultLauncher
        requestLauncher = registerForActivityResult(
            // Contract 객체 (실제 작업자)
            ActivityResultContracts.StartActivityForResult()
        ) {
            // Callback 객체 (결과 처리)
            if (it.resultCode == Activity.RESULT_OK) {
                val todoList = it.data?.getStringExtra("Todo")
                binding.tvTemp.text = "result: $todoList"
            }
        }

        binding.extendedFab.setOnClickListener {
            // 할 일 등록 화면(AddActivity)로 이동 → intent 사용
            val intent = Intent(
                this@MainActivity,
                AddActivity::class.java
            ) // 컴포넌트의 정보를 intent에 담음, 클래스 타입 레퍼런스 정보
            requestLauncher.launch(intent)
        }
    }
}