package com.example.myapplication5

import android.app.DatePickerDialog
import android.app.ProgressDialog.show
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import com.example.myapplication5.databinding.ActivityMainBinding
import com.example.myapplication5.databinding.DialogCustomBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle // 토굴 변수 생성 -> OnCreate 함수에서 초기화해야 함
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // drawer와 toggle 연결 - 클릭했을 때 동작하는 부분은 빠져 있음
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawaer_opened, R.string.drawer_closed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 화면에 보여짐
        toggle.syncState()


        binding.btnDate.setOnClickListener {
            DatePickerDialog(this, object: DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    Toast.makeText(applicationContext, "${year}년 ${month+1}월 ${dayOfMonth}일", Toast.LENGTH_LONG).show()
                    binding.btnDate.text = "${year}년 ${month+1}월 ${dayOfMonth}일"
                }

            }, 2024, 3, 6).show()
        }

        binding.btnTime.setOnClickListener {
            TimePickerDialog(this, object: TimePickerDialog.OnTimeSetListener{
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    Toast.makeText(applicationContext, "$hourOfDay : $minute", Toast.LENGTH_LONG).show()
                }

            }, 14, 5, true)
        }

        val eventListener = object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when(which) {
                    DialogInterface.BUTTON_POSITIVE -> Log.d("mobileapp", "BUTTON_POSITIVE")
                    DialogInterface.BUTTON_NEGATIVE -> Log.d("mobileapp", "BUTTON_NEGATIVE")
                }
            }

        }


        binding.btnAlert.setOnClickListener {
            AlertDialog.Builder(this).run() {
                setTitle("알림창 - 기본")
                setIcon(android.R.drawable.ic_menu_search)
                setMessage("기본 알림창이 눌렸습니다")
                setPositiveButton("확인", eventListener)
                setNegativeButton("취소", eventListener)
                show()
            }
        }


        val items = arrayOf<String>("사과", "배", "수박", "딸기")
        binding.btnAlertItem.setOnClickListener {
            AlertDialog.Builder(this).run() {
                setItems(items, object: DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        when(which) {
                            DialogInterface.BUTTON_POSITIVE -> {
                                var item = items[which]
                            }
                        }
                    }

                })
                setTitle("알림창 - 아이템 선택")
                setPositiveButton("확인", eventListener)
                setNegativeButton("취소", eventListener)
                show()
            }
        }

        var checked = 0;

        val eventListener2 = object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when(which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        Log.d("mobileapp", "BUTTON POSITIVE")
                        binding.btnAlertSingle.text = "${items[checked]} 선택됨"
                    }

                    DialogInterface.BUTTON_NEGATIVE -> {

                    }
                }
            }

        }


        binding.btnAlertSingle.setOnClickListener {
            AlertDialog.Builder(this).run() {
                // 미리 선택되어 출력될 값 지정해두기
                setSingleChoiceItems(items, 3, object: DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Log.d("mobileapp", "${items[which]} 선택됨")
                        checked = which; // 사용자의 값 저장
                    }
                })
                setTitle("알림창 - 하나만 선택")
                setPositiveButton("확인", eventListener2)
                setNegativeButton("취소", eventListener2)
                show()
            }
        }

        binding.btnAlertMulti.setOnClickListener {
            AlertDialog.Builder(this).run() {
                setTitle("알림창 - 다수 선택")
                // 개별적으로 각각이 선택되었는지 배열으로 넣어줌
                setMultiChoiceItems(items, booleanArrayOf(false, true, true, false), object: DialogInterface.OnMultiChoiceClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int, isChecked: Boolean) {
                        // 체크를 위한 클릭인지, 체크 해제를 위한 클릭인지도 전달됨
                        Log.d("mobileapp", "${items[which]} ${if (isChecked) "선택됨" else "해제됨"}") // String에 조건문 주기
                    }

                } )
                setPositiveButton("확인", eventListener2)
                setNegativeButton("취소", eventListener2)
                show()
            }
        }

        val dialogBinding = DialogCustomBinding.inflate(layoutInflater)

        val eventListener3 = object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        if (dialogBinding.rbtn1.isChecked) {
                            binding.btnAlertCustom.text = dialogBinding.rbtn1.text.toString() // String 형태로 변경해주기 위해 toString()
                        }

                        else if (dialogBinding.rbtn2.isChecked) {
                            binding.btnAlertCustom.text = dialogBinding.rbtn2.text.toString() // String 형태로 변경해주기 위해 toString()
                        }

                        else if (dialogBinding.rbtn3.isChecked) {
                            binding.btnAlertCustom.text = dialogBinding.rbtn3.text.toString() // String 형태로 변경해주기 위해 toString()
                        }

                        else if (dialogBinding.rbtn4.isChecked) {
                            binding.btnAlertCustom.text = dialogBinding.rbtn4.text.toString() // String 형태로 변경해주기 위해 toString()
                        }
                    }
                }
            }
        }



        binding.btnAlertCustom.setOnClickListener {
            AlertDialog.Builder(this).run() {
                setTitle("알림창 - 사용자 화면")
                setIcon(android.R.drawable.ic_dialog_alert)
                setView(dialogBinding.root) // DialogCustomBinding으로 뷰를 내보냄

                setPositiveButton("확인", eventListener3)
                setNegativeButton("취소", eventListener3)
                show()
            }
        }
    }

    // Option Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_navigation, menu) // resource/menu/menu_navigation 파일을 menu에 등록하겠다
        // 아이템을 클릭하면 onOptionsItemSelected 함수가 자동으로 호출된다

        /**
         * 메뉴 불러오기
         */
        // menu에 null 값이 들어갈 수 있음. menu를 사용할 때는 ?를 붙인다
        val searchView = menu?.findItem(R.id.menu_search)?.actionView as androidx.appcompat.widget.SearchView // id가 menu_search인 Item의 ActionView를 가져오겠다.
        // as로 캐스팅 할 클래스 정의 - android.appconpact.widget 안에 있는 SearchView를 임포트

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(applicationContext, "${query} 검색합니다.", Toast.LENGTH_LONG).show() // this가 아님
                // 검색어를 입력하고 엔터를 눌렀을 경우
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // 텍스트가 바뀔 때마다 -> 검색어에 키보드를 입력할 때마다
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    // 아이템이 선택되었을 때 수행할 기능 정의
    // 토굴이 선택되었을 어떤 onOptionsItemSelected 호출됨
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) { // 토굴이 클릭될때마다 이 함수가 실행됨
            return true // 토굴의 본래 기능을 실행해라
        }
        when(item.itemId) { // item id로 구분
            R.id.item1 -> {
                Log.d("mobileapp", "Option Menu : 메뉴 1")
                // 함수 밖이므로 binding에 접근할 수 없음 -> 전역 변수로 선언
                binding.btnDate.setTextColor(Color.parseColor("#ffff00"))
                true
            }
            R.id.item2 -> {}
            R.id.item3 -> {}
            R.id.item4 -> {}
        }
        return super.onOptionsItemSelected(item)
    }
}