package com.example.mid20220961

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mid20220961.databinding.ActivityMainBinding
import com.example.mid20220961.databinding.CustomDialogBinding
import com.example.mid20220961.databinding.LoginLayoutBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    /**
     * fragment 관리 클래스
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
         * toolbar 연결
         */
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar);

        /**
         * 토굴 연결
         */
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawaer_opened, R.string.drawer_closed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 화면에 보여짐
        toggle.syncState()

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

        /**
         * navigation 이벤트 리스너 연결
         */
        binding.navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        /**
         * 메뉴 불러옴
         */
        menuInflater.inflate(R.menu.menu_navigation, menu) // resource/menu/menu_navigation 파일을 menu에 등록하겠다

        /**
         * searchView
         */
        val searchView = menu?.findItem(R.id.menu_search)?.actionView as androidx.appcompat.widget.SearchView // id가 menu_search인 Item의 ActionView를 가져오겠다.

        /**
         * custom dialog
         */
        val customDialog = CustomDialogBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this).run() {
            setView(customDialog.root)
            setTitle("검색어 입력 확인")
            setPositiveButton("확인", null)
            create()
        }

        /**
         * setOnQueryTextListener
         */
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                customDialog.input.text = query
                dialog.show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // 텍스트가 바뀔 때마다 -> 검색어에 키보드를 입력할 때마다
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /**
         * 토굴 선택 시 drawer 열림
         */
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        // 추가 - 로그인 선택 시 커스텀 다이얼로그
        val loginBinding = LoginLayoutBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this).run() {
            setView(loginBinding.root)
            setTitle("로그인")
            setPositiveButton("로그인", null)
            setNegativeButton("취소", null)
            setNeutralButton("회원가입", null)
            create()
        }

        /**
         * 로그인 선택 시 토스트
         */
        when(item.itemId) {
            R.id.login -> {
                Toast.makeText(applicationContext, "개발 중 입니다", Toast.LENGTH_LONG).show()
                dialog.show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            // 브라우저 연동
            R.id.item_info -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://m.duksung.ac.kr"))
                startActivity(intent)
                true
            }
            // 지도 연동
            R.id.item_map -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/덕성여대/선바위역/"));
                startActivity(intent)
                true
            }
            // 전화걸기 앱 연동
            R.id.item_call -> {
                // ACION_DIAL은 전화번호만 표시, ACTION_CALL은 전화를 바로 건다
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:02-911"))
                // val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:02-911"))
                startActivity(intent)
                true
            }
            R.id.move_activity -> {
                val intent = Intent(this, EnterActivity::class.java)
                startActivity(intent)
            }
        }
        return false
    }
}