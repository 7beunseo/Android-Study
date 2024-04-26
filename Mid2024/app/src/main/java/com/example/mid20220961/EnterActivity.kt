package com.example.mid20220961

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mid20220961.databinding.ActivityEnterBinding

class EnterActivity : AppCompatActivity() {
    lateinit var binding: ActivityEnterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_move)

        val binding = ActivityEnterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.button.setOnClickListener {
            finish()
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }
}