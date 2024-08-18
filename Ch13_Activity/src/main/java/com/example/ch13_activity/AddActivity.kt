package com.example.ch13_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.ch13_activity.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // actionbar 의 내용을 toolbar 에 적용
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.save -> {
            // MainActivity 로 돌아가기 + 기능 추가
//            finish()
            Log.d("왜 안보내져: ", binding.etTodo.text.toString())
            val intent = Intent(this@AddActivity, MainActivity::class.java)
            intent.putExtra("Todo", binding.etTodo.text.toString())
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}