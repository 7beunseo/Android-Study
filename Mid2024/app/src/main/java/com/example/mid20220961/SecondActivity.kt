package com.example.mid20220961

import android.app.Activity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mid20220961.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * 값 받아오기
         */
        val info = intent.getStringExtra("info")
        binding.info.text = info + " 추가하기"

        /**
         * 값 전달
         */
        binding.save.setOnClickListener {
            val intent = intent
            intent.putExtra("result", "[$info]" + binding.first.text.toString() + "\n" + binding.second.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
            true
        }
    }
}