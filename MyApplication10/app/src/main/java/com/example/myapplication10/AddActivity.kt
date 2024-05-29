package com.example.myapplication10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication10.databinding.ActivityAddBinding
import java.text.SimpleDateFormat

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_add)

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvId.text = MyApplication.email
        binding.saveButton.setOnClickListener {
            // 저장함
            if(binding.input.text.isNotEmpty()) { // 사용자가 입력한 경우에만 save를 함
                val dateFormat = SimpleDateFormat("yyyy-mm-dd hh:mm:ss")
                // 로그인 이메일, 스타, 한줄평, 입력 시간
                val data = mapOf( // 키와 쌍 형태로 저장함
                    "email" to MyApplication.email, // 필드명 자것ㅇ
                    "stars" to binding.ratingBar.rating.toFloat(),
                    "comments" to binding.input.text.toString() ,
                    "data_time" to dateFormat.format(System.currentTimeMillis())
                )
                MyApplication.db.collection("comments")
                    .add(data) // data에 추가한 갓을 comments 에 넣겠다
                // add가 잘 되었는지 확인
                    .addOnCompleteListener {
                        Toast.makeText(this, "데이터 저장 성공", Toast.LENGTH_LONG).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "데이터 저장 실패", Toast.LENGTH_LONG).show()
                    }


            } else {
                Toast.makeText(this, "한줄평을 먼저 입력해주세요", Toast.LENGTH_LONG).show()
            }
        }
    }


}