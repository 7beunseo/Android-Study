package com.example.myapplication7

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication7.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_add)

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * MainActivity에서 보낸 값을 get{타입}Extra 로 가져옴 (getStringExtra)
         */
        var date = intent.getStringExtra("today") // string 형태로 된 today를 가져오겠다
        binding.date.text = date

        /**
         * addActivity의 home 화면에 뒤로가기 버튼 표시
         */
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.btnSave.setOnClickListener {
            /**
             * 돌아가기 전에 값 넣음 - putExtra
             */
            val intent = intent
            intent.putExtra("result", binding.addEditView.text.toString())

            /**
             * intent를 전달, RESULT_OK
             */
            setResult(Activity.RESULT_OK, intent) // ok 상태로 전달 -> 자신을 불렀던 곳으로 되돌아간다

            finish() // 종료
            true
        }
    }

    /* 아님
    override fun isLocalVoiceInteractionSupported(): Boolean {
        return super.isLocalVoiceInteractionSupported()
    }

     */

    /**
     * 업 버튼(뒤로가기) 클릭 시 자동으로 호출되는 함수 재정의 
     */
    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }
}