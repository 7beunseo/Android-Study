package com.example.myapplication7

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.myapplication7.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_add)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // today 가져옴
        var date = intent.getStringExtra("today") // string 형태로 된 today를 가져오겠다
        binding.date.text = date

        supportActionBar?.setDisplayHomeAsUpEnabled(true) // addActivity의 home 화면에 표시하겠다는 뜻 ㅐ
        binding.btnSave.setOnClickListener {
            // 돌아가기 전에 값을 넣음
            val intent = intent
            intent.putExtra("result", binding.addEditView.text.toString())
            // intent 만든 것을 전달
            setResult(Activity.RESULT_OK, intent) // ok 상태로 전달 -> 자신을 불렀던 곳으로 되돌아간다

            finish() // 종료
            true
        }
    }

    override fun isLocalVoiceInteractionSupported(): Boolean {
        return super.isLocalVoiceInteractionSupported()
    }
}