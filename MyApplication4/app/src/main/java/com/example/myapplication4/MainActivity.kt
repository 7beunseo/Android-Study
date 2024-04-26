package com.example.myapplication4

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