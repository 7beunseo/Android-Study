package com.example.myapplication10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication10.databinding.ActivityHumanBinding

class HumanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityHumanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvName.text = intent.getStringExtra("name")
        binding.tvAge.text = intent.getStringExtra("age")
        binding.tvAddr.text = intent.getStringExtra("add")

    }
}