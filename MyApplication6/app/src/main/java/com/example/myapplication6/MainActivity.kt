package com.example.myapplication6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication6.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    // FragmentStateAdapter를 상속받음
    /**
     * ViewPagerAdapter 클래스 생성
     */
    class MyFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
        // 어떤 fragement를 다룰 것인지 변수를 선언해둠
        val fragments: List<Fragment>
        init { // 바로 실행됨
            fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment()) // 3개의 Fragment를 리스트로 담고 있음
        }
        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * viewpager의 관리자 연결
         */
        binding.viewpager.adapter = MyFragmentPagerAdapter(this)

        /**
         * tab과 viewpager 연결
         */
        TabLayoutMediator(binding.tabs, binding.viewpager) {
            tab, position ->
                tab.text = "TAB ${position + 1}" // 탭에 쓰여지는 글자를 정할 수 있음
        }.attach()
    }
}