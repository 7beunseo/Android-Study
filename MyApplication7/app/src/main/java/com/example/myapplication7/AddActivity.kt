package com.example.myapplication7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication7.databinding.ActivityAddBinding
import com.example.myapplication7.databinding.ActivityMainBinding

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_add)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            finish() // 종료
            true
        }
    }
}