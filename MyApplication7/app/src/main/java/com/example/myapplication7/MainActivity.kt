package com.example.myapplication7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.ch13_activity.MyAdapter
import com.example.myapplication7.databinding.ActivityMainBinding
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    // data에 대한 변수 선언
    var datas: MutableList<String>? = null // 처음에는 null로 시작 - ? 붙여주어야 함

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bundle에 값이 있으면 그 값을 가져오겠다
        // savedInstanceState : onCreate의 매개변수로 받은 bundle 값을 쓰겠다는 뜻
        datas = savedInstanceState?.let {
            it.getStringArrayList("datas")?.toMutableList() // bundle의 값을 넣음
        } ?: let {
            mutableListOf<String>() // 값이 없을 경우는 mutableList로 넣겠다
        }

        val adapter = MyAdapter(datas)
        binding.recyclerView.adapter = adapter // 기존에 만들어둔 adapter와 연결함

        val layoutManager =  LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )

        // registerForActivityResult : 결과를 전달받는 호출
        val requestLaunchar: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            // 결과를 돌려받았을 때 처리해주는 부분
            // 전달된 값을 it이라는 변수로 사용
            it.data!!.getStringExtra("result")?.let {  // data에 값이 있다면 -> null이 아니면
                if( it != "") {
                    datas?.add(it)
                    adapter.notifyDataSetChanged()
                }

            }
        }
        binding.mainFab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java) // AddActivity의 intent를 부름

            /**
             * addActivity를 start 하기 전에 데이터 전달
             */
            // 오늘 날짜를 가지고 옴
            val dataFormat = SimpleDateFormat("yyyy-mm-dd") // 년 월 일
            intent.putExtra("today", dataFormat.format(System.currentTimeMillis())) // putExtra를 통해서 date라는 이름으로 string 형태로 넣어줌


            // startActivity(intent)
            requestLaunchar.launch(intent) // launch로 실행함
        }



    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putStringArrayList("datas", ArrayList(datas))
    }


}