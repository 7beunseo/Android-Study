package com.example.myapplication9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication9.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jsonfragment = JsonFragment() // 각각의 클래스를 변수로 선언
        val xmlfragment = XmlFragment()
        val bundle = Bundle() // fragmet에 전달 -

        binding.btnSearch.setOnClickListener { // 클릭했을 때
            bundle.putString("searchYear", binding.edtYear.text.toString()) // 입력한 값을 string으로 전달 putString

            if (binding.rGroup.checkedRadioButtonId == R.id.rbJson) {
                jsonfragment.arguments = bundle // arguemtn에 bundle을 넣어줌
                // fragment 쪽에 데이터를 전달해줌 - 년도 값을 각각의 fragment에 전달해주고 싶다
                supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_content, jsonfragment) // activity_content 부분을 대체하겠다
                    .commit()
            } else if (binding.rGroup.checkedRadioButtonId == R.id.rbXml) {
                xmlfragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_content, xmlfragment) // xmlfragment로 대체하겠다
                    .commit()
            }
        }
    }
}