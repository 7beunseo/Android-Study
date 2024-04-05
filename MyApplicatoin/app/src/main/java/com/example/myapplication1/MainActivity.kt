package com.example.myapplication1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.myapplication1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submit.setOnClickListener {
            val name = binding.name.getText()
            val password = binding.password.getText()
            AlertDialog.Builder(this).run() {
                setTitle("선택 내용")
                setMessage("name : $name \npassword :$password")
                show()
            }
        }
    }
}