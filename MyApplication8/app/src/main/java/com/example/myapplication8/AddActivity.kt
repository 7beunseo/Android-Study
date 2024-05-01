package com.example.myapplication8

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.preference.PreferenceManager
import com.example.myapplication8.databinding.ActivityAddBinding
import java.io.File
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    lateinit var sharedPPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPPreference = PreferenceManager.getDefaultSharedPreferences(this)
        // 설정에 있는 각각의 값을 가져와야 함 -> 키를 통해 가져오면 됨
        val color = sharedPPreference.getString("color", "#00ff00")
        binding.date.setTextColor(Color.parseColor(color))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var date = intent.getStringExtra("today")
        binding.date.text = date

        binding.btnSave.setOnClickListener {
            val edt_srt = binding.addEditView.text.toString()
            val intent = intent
            intent.putExtra("result", edt_srt)
            setResult(Activity.RESULT_OK, intent)

            // db에 저장하기
            val db = DBHelper(this).writableDatabase
            db.execSQL("insert into todo_tb (todo) values (?)", arrayOf<String>(edt_srt))
            db.close()

            // 파일 저장하기
            val dateformat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss") // 년 월 일 시 분 초
            val file = File(filesDir, "test.txt")
            val writestream: OutputStreamWriter = file.writer()
            writestream.write(dateformat.format(System.currentTimeMillis()))
            writestream.flush()

            finish()
            true
        }
    } // onCreate()

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

    override fun onSupportNavigateUp(): Boolean {
        val intent = intent
        setResult(Activity.RESULT_OK, intent)
        finish()
        return true
    }

    override fun onResume() {
        super.onResume()

        sharedPPreference = PreferenceManager.getDefaultSharedPreferences(this)
        // 설정에 있는 각각의 값을 가져와야 함 -> 키를 통해 가져오면 됨
        val color = sharedPPreference.getString("color", "#00ff00")
        binding.date.setTextColor(Color.parseColor(color))
    }
}