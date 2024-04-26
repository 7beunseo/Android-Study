package com.example.mid20220961

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog.show
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.example.mid20220961.databinding.FragmentTwoBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TwoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TwoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentTwoBinding.inflate(inflater, container, false)

        binding.date.setOnClickListener {
            DatePickerDialog(requireContext(), object: DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    binding.date.text = "${year}년 ${month + 1}월 ${dayOfMonth}일"
                }

            },2024, 3, 27).show()
        }

        binding.time.setOnClickListener {
            TimePickerDialog(requireContext(), object:TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    binding.time.text = "${if (hourOfDay > 12) "오후" else "오전"} ${hourOfDay}시 ${minute}분"
                }

            }, 14, 0, true ).show()
        }
        var selected = 0
        val rooms = arrayOf<String>("room1", "room2", "room3")

        val eventListener = object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when(which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        Toast.makeText(requireContext(), "${rooms[selected]}이 최종 선택되었습니다", Toast.LENGTH_LONG).show()
                        binding.room.text = "${rooms[selected]}"
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        Toast.makeText(requireContext(), "예약룸 선택이 취소되었습니다.", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
        binding.room.setOnClickListener {
            AlertDialog.Builder(requireContext()).run() {
                setSingleChoiceItems(rooms, 0, object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        selected = which
                    }
                })
                setTitle("예약룸 선택하기")
                setPositiveButton("OK", eventListener)
                setNegativeButton("CANCEL", eventListener)
                show()
            }
        }

        binding.finish.setOnClickListener {
            val name = binding.name.text.toString()
            val date = binding.date.text.toString()
            val time = binding.time.text.toString()
            val room = binding.room.text.toString()
            binding.result.text = """
예약자는 $name
예약 날짜는 $date $time
예약룸은 $room
            """.trimIndent()
            binding.result.visibility = View.VISIBLE
        }


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TwoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TwoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}