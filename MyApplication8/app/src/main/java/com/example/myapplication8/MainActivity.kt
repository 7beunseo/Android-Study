package com.example.myapplication8

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch17_storage2.MyAdapter
import com.example.myapplication8.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.File
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    // var datas: MutableList<String>? = null
    lateinit var adapter: MyAdapter
    lateinit var sharedPPreference: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // sharedPreferecne에 저장되어 있는 값을 불러옴
        sharedPPreference = PreferenceManager.getDefaultSharedPreferences(this)
        // 설정에 있는 각각의 값을 가져와야 함 -> 키를 통해 가져오면 됨
        val color = sharedPPreference.getString("color", "#00ff00")
        binding.lastsaved.setBackgroundColor(Color.parseColor(color))

        val idStr = sharedPPreference.getString("id", "")
        binding.totoTitle.text = idStr

        val size = sharedPPreference.getString("size", "16.0f")
        binding.lastsaved.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size!!.toFloat())



        // 필요 x
        /*
        datas  = savedInstanceState?.let {
            it.getStringArrayList("datas")?.toMutableList()
        }
            ?: let {
                mutableListOf<String>()
            }

         */
        val datas = mutableListOf<String>()

        val db = DBHelper(this).readableDatabase
        val cursor = db.rawQuery("select * from todo_tb", null)
        // Log.d("mobileapp", cursor.toString())
        while(cursor.moveToNext()) {
            datas?.add(cursor.getString(1))
        }
        db.close()

        // 파일 읽기 작업 진행

        val file = File(filesDir, "test.txt")
        val readstream: BufferedReader = file.reader().buffered() // 읽을 준비
        binding.lastsaved.text = "마지막 저장시간 : " + readstream.readLine()



        adapter= MyAdapter(datas)
        binding.recyclerView.adapter=adapter
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager=layoutManager
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            it.data?.getStringExtra("result")?.let {// "result"에 값이 저장되어 있으면(non-null)
                if(it != "") {
                    datas?.add(it)
                    adapter.notifyDataSetChanged()

                    // 파일 읽기 작업 진행
                    val file = File(filesDir, "test.txt")
                    val readstream: BufferedReader = file.reader().buffered() // 읽을 준비
                    binding.lastsaved.text = "마지막 저장시간 : " + readstream.readLine()
                }
            }
        }

        binding.mainFab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)

            val dateFormat = SimpleDateFormat("yyyy-MM-dd") // 년 월 일
            intent.putExtra("today",dateFormat.format(System.currentTimeMillis()))

            requestLauncher.launch(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_main_setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /*
        override fun onSaveInstanceState(outState: Bundle) {
            super.onSaveInstanceState(outState)
            outState.putStringArrayList("datas", ArrayList(datas))  // 지금까지의 datas 저장
        }

         */


    override fun onResume() {
        super.onResume()

        // sharedPreferecne에 저장되어 있는 값을 불러옴
        sharedPPreference = PreferenceManager.getDefaultSharedPreferences(this)
        // 설정에 있는 각각의 값을 가져와야 함 -> 키를 통해 가져오면 됨
        val color = sharedPPreference.getString("color", "#00ff00")
        binding.lastsaved.setBackgroundColor(Color.parseColor(color))

        val idStr = sharedPPreference.getString("id", "")
        binding.totoTitle.text = idStr

        val size = sharedPPreference.getString("size", "16.0f")
        binding.lastsaved.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size!!.toFloat())

    }
}