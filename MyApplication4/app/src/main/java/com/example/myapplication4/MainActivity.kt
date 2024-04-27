package com.example.myapplication4

/*
<!--

배열 선언


layout_weight : 여백 채우기
layout_weight = 1 / layout_weight = 3 -> 여백을 각각 1, 3의 비율로 나우어 차지

RelativeLayout
(layout을 치면 나오는 속성들 이용하기) - 어디에 배치할 것인가
- layout_above
- layout_below
- layout_toLeftOf
- layout_toRightOf

(align 검색) - 어느쪽 기준으로 맞출 것인가
layout_alignTop
layout_alignBottom
layout_alignLeft
layout_alignRight
layout_alignBaseline
layout_centerHorizental - 부모의 가로 세로 방향 중앙에 맞춤
layout_centerVertical - 부모의 가로 방향 중앙에 맞춤
layout_centerParent - 부모의 가로 세로 중앙에 맞춤

GridLayout
oriengtation
rowCount : 세로로 나열할 뷰 개수
columnCount : 가로로 나열할 뷰 개수
layout_gravity = fill_horizental : 뷰 크기 확장
layout_row=1, layout_row=1 위치를 특정지을 수 있음
layout_columnSpan : 가로로 열 병합
layout_rowSpan : 세로로 행 병합


for(i in 1..10)
for (i in 2..10 step 2)
for(i in 10 downTo 1)
for(i in data indices) // data의 길이만큼 자동으로, i로 인덱스 접근

android:autoLine="web"
<TextView
    android:text="이지스퍼블리싱 - 웹페이지 : https://~~ , 전화번호 : 01082302512, 이메일 : kimes0403@gmail.com"
    android:autoLink="web|mail|phone"
/>

android:maxLines="3" -> 특정 줄까지만 나오도록 하는 속성
android:ellipsize="end" -> 문자열이 더 있다는 줄임표

이미지
android:adjustViewBounds -> 뷰의 크기를 맞춤

EditText
- maxLines -> 처음에는 한줄 입력 크기로 출력되다 지정한 크기까지 늘어남
- lines -> 처음에 여러 줄 입력 크기로 나오게 해줌
- inputType -> 글을 입력할 때 올라오는 키보드를 지정하는 속성
- none, text, textCapCharacters, textMultipleLine, textNoSuggesstions, textUri, textEmailAddress, textPasswoard, textVisiblePassward, number, numberSigned, numberDecimal,
numberPassward, phone

터치와 키 이벤트
onTouchEvent() -> 터치의 종류와 발생 지점이 담김 (evnet.x, event.y, event.rowX, event.rowY)
- ACTION_DOWN : 소가락으로 누른 순간의 이벤트, ACTION_UP : 화면에서 손가락을 떼는 순간의 이벤트, ACTION_MOVE : 화면을 손가락으론 누른 채로 이동하는 순간의 이벤트

키 이벤트
- onKeyDown
- onKeyUp
- onKeyLongPress
```
when(keyCode) {
    KeyEvent.KEYCODE_0 -> // 어떤 키를 눌렀는지 확인
``
백 키 = onBackPressed = KeyEvent.KEYCODE_BACK

배열 - Array클래스
arrayOf, booleanArrayOf, intArrayOf() -> ()안에 초기값 설정 가능
arrayOf<T>(초기값)

리스트 - List, MutableList 클래스
listOf<T>(), mutableListOf<T>() 로 초기값 설정

-->
 */
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication4.databinding.ActivityMainBinding
import java.util.EventListener

class MainActivity : AppCompatActivity() {
    var initTime = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        var prevTime = 0L
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * chronomter 시작
         */
        binding.btnStart.setOnClickListener {

            binding.chronometer.base = SystemClock.elapsedRealtime() + prevTime
            binding.chronometer.start()
            binding.btnStop.isEnabled = true
            binding.btnReset.isEnabled = true
            binding.btnStart.isEnabled = false
        }

        /**
         * chronometer 멈춤
         */
        binding.btnStop.setOnClickListener {
            binding.chronometer.stop() // chromoter 중지
            // 언제 stop 버튼을 눌렀는지 확인 필요
            prevTime = binding.chronometer.base - SystemClock.elapsedRealtime() // start - stop을 하면 시간 차 간격 값이 담기게 됨

            binding.btnStop.isEnabled = false
            binding.btnReset.isEnabled = true
            binding.btnStart.isEnabled = true
        }

        /**
         * chromometer reset
         */
        binding.btnReset.setOnClickListener {
            binding.chronometer.stop()
            binding.chronometer.base = SystemClock.elapsedRealtime()
            prevTime = 0L
            binding.btnStop.isEnabled = true
            binding.btnReset.isEnabled = false
            binding.btnStart.isEnabled = true
        }
    }

    val eventListener = object: DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface?, which: Int) {
            when(which) {
                DialogInterface.BUTTON_POSITIVE -> Log.d("mobileapp", "positive")
            }
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode) {
            /**
             * 백 키 눌림
             * SystemClock.elapsedRealtime() : 현재 시간
             */
            KeyEvent.KEYCODE_BACK -> {
                if(SystemClock.elapsedRealtime() - initTime > 3000) {
                    initTime = SystemClock.elapsedRealtime()
                    Toast.makeText(this, "종료하려면 한번 더 누르세요", Toast.LENGTH_LONG).show()
                    return true;
                }
            }
            /**
             * 볼륨 키 눌림
             */
            KeyEvent.KEYCODE_VOLUME_UP -> {
                AlertDialog.Builder(this).run {
                    setTitle("볼륨업 버튼 눌림")
                    setPositiveButton("확인", eventListener)
                    show()
                }

            }
        }
        return super.onKeyDown(keyCode, event)
    }
}