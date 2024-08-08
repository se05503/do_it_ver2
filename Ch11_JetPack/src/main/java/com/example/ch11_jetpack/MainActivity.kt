package com.example.ch11_jetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // SecurityException resolve
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // 액티비티가 실행되면서 처음에 한 번만 호출됨
        menuInflater.inflate(R.menu.menu_main, menu)
        val menuItem = menu?.findItem(R.id.menu_search)
        val searchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // 키보드의 검색 버튼을 클릭한 순간의 이벤트
                Log.d("search result : ", query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                // 검색어 변경 이벤트
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}