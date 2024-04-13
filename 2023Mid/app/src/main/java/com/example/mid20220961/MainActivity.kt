package com.example.mid20220961

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mid20220961.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    class MyFragmentAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
        val fragments: List<Fragment>

        init {
            fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment())
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

        // view 등록
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // toggle 연결
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.menu_opened, R.string.menu_closed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        // adapter로 ViewPaper 연결
        binding.viewpager.adapter = MyFragmentAdapter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_navigation, menu)
        val searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView // androidx.appcompat.widget.SearchView 임포트하기

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                AlertDialog.Builder(this@MainActivity).run() {
                    setTitle("검색어 입력 확인")
                    setMessage("검색어 " + query +"을/를 입력했습니다.")
                    setPositiveButton("닫기", null)
                    show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // toggle을 클릭했을 경우
        if(toggle.onOptionsItemSelected(item))
            return true

        when(item.itemId) {
            R.id.login -> {
                Toast.makeText(applicationContext, "개발 중 입니다.", Toast.LENGTH_LONG).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}