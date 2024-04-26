package com.example.myapplication5

import android.app.DatePickerDialog
import android.app.ProgressDialog.show
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
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
import com.google.android.material.navigation.NavigationView

/**
 * arrayOf<T>() -> 초기값 선언 가능
 * typeArrayOf() ex) booleanArrayOf()
 * mutableListOf<T>() <-> MuTableList<T>
 * listOf<T>() <-> List<T>
 */
/**
 * navigation에 이벤트 리스너 등록하기 위해 NavigationView.OnNavigationItemSelectedListener 추가 상속
 */
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle // 토굴 변수 생성 -> OnCreate 함수에서 초기화해야 함
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // drawer와 toggle 연결 - 클릭했을 때 동작하는 부분은 빠져 있음
        /**
         * drawer와 toggle 연결
         */
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawaer_opened, R.string.drawer_closed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 화면에 보여짐
        toggle.syncState()

        /**
         * DatePickerDialog
         */
        binding.btnDate.setOnClickListener {
            DatePickerDialog(this, object: DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    Toast.makeText(applicationContext, "${year}년 ${month+1}월 ${dayOfMonth}일", Toast.LENGTH_LONG).show()
                    binding.btnDate.text = "${year}년 ${month+1}월 ${dayOfMonth}일"
                }

            }, 2024, 3, 6).show()
        }

        /**
         * TimePickerDialog
         */
        binding.btnTime.setOnClickListener {
            TimePickerDialog(this, object: TimePickerDialog.OnTimeSetListener{
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    Toast.makeText(applicationContext, "$hourOfDay : $minute", Toast.LENGTH_LONG).show()
                }

            }, 14, 5, true)
        }

        /**
         * eventListener
         */
        val eventListener = object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when(which) {
                    DialogInterface.BUTTON_POSITIVE -> Log.d("mobileapp", "BUTTON_POSITIVE")
                    DialogInterface.BUTTON_NEGATIVE -> Log.d("mobileapp", "BUTTON_NEGATIVE")
                }
            }

        }

        /**
         * AlertDialog - SetMessage
         */
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

        /**
         * AlertDialog - setItems
         * items는 arrayOf<String>() 으로 선언한다
         */
        val items = arrayOf<String>("사과", "배", "수박", "딸기")
        binding.btnAlertItem.setOnClickListener {
            AlertDialog.Builder(this).run() {
                setItems(items, object: DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        /**
                         * 아이템이 선택될때마다 리스너 동작
                         */
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
                        /**
                         * 최종 선택된 결과를 출력
                         */
                        binding.btnAlertSingle.text = "${items[checked]} 선택됨"
                    }

                    DialogInterface.BUTTON_NEGATIVE -> {

                    }
                }
            }

        }

        /**
         * AlertDialog - setSingleChoiceItems
         * 미리 선택되어 보일 값 지정해주어야 함
         */
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

        /**
         * AlertDialog - setMultiChoiceItems
         * booleanArrayOf()로 각각 선택되는지를 모두 지정해줌
         * 이벤트 리스너는 OnMultiChoiceClickListener -> 선택되었는지, 해제되었는지 정보를 제공해줌
         */
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

        /**
         * 화면에 보여질 dialog를 inflate
         */
        val dialogBinding = DialogCustomBinding.inflate(layoutInflater)

        /**
         * customDialog 이벤트 리스너 (그러나 본질은 버튼에 관한 리스너라는 거 까먹지 말기)
         * which : 무슨 버튼을 눌렀는가 -> POSITIVE_BUTTON or NEGATIVE_BUTTON ...
         * 선택된 값을 확인하기 위해서는 isChecked로 하나하나 확인해야 함
         */
        val eventListener3 = object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                /**
                 * positive 버튼이 눌렸는지 1차 확인
                 */
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        /**
                         * 각각의 버튼이 눌린 것인지 하나하나 확인
                         */
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

        /**
         * AlertDialog - customDialog
         * 처음 선언할 때는 create()로 미리 만들어둔다 선언
         */
        val customDialog = AlertDialog.Builder(this).run() {
            setTitle("알림창 - 사용자 화면")
            setIcon(android.R.drawable.ic_dialog_alert)
            setView(dialogBinding.root) // DialogCustomBinding으로 뷰를 내보냄

            setPositiveButton("확인", eventListener3)
            setNegativeButton("취소", eventListener3)
            create() // 만들기만 하겠다
        }

        binding.btnAlertCustom.setOnClickListener {
            /**
             * setView를 사용하지 않고 이미 만들어둔 다이얼로그를 보여준다
             */
            customDialog.show() // setView를 하지 않게 됨
        }

        // 네비게이션 연결
        // setNavigationItemSelectedListener를 쓰기 위해서는 상속받는 클래스가 하나 더 추가되어야 함
        // NavigationView.OnNavigationItemSelectedListener 상속
        /**
         * drawer 네비게이션 이벤트리스너 설정 - 1
         */
        binding.mainDrawerView.setNavigationItemSelectedListener(this)

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

        /**
         * setOnQueryTextListener
         */
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


    // 토굴이 선택되었을 어떤 onOptionsItemSelected 호출됨
    /**
     * onOptionsItemSelected - 아이템이 선택되었을 때 수행할 기능 정의
     */
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

    /**
     * navigation 이벤트 리스너 처리
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // 전달받은 아이템의 아이디에 따라 처리하면 된다
        when(item.itemId) {
            // 브라우저 연동
            R.id.item_info -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://m.duksung.ac.kr"))
                startActivity(intent)
                true
            }
            // 지도 연동
            R.id.item_map -> {
                // val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.566292, 126.9779751"))
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/덕성여대/선바위역/"));
                startActivity(intent)
                true
            }
            // 갤러리 앱 연동
            R.id.item_gallery -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"))
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
            // 메일 앱 연동
            R.id.item_mail -> {
                val intent = Intent(Intent.ACTION_SEND, Uri.parse("mailto:kimes0403@gmail.com"))
                startActivity(intent)
                true
            }
        }
        return false // 디폴트 false
    }
}