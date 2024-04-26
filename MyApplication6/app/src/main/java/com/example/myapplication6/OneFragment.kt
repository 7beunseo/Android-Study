package com.example.myapplication6

import android.app.ProgressDialog.show
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication6.databinding.FragmentOneBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OneFragment : Fragment() {
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
        /**
         * view에서 필요한 동작을 넣어줌
         */
        // Inflate the layout for this fragment
        val binding = FragmentOneBinding.inflate(inflater, container, false)
        binding.fragButton.setOnClickListener {
            /**
             * setBackgroundColor, Color.parseColor(RGB) - ragment의 배경색을 바꿈
             */
            binding.oneFragment.setBackgroundColor(Color.parseColor("#ffddff"))

            /**
             * fragment에서 Toast -> this가 아니라 context로 지정
             */
            Toast.makeText(context, "OneFragment", Toast.LENGTH_LONG).show() // this -> context로 변경

            /**
             * context가 존재할 때만 alert 창을 띄우는 코드
             */
            // androidx.appcompat.app. 경로에 있는 AlertDialog 임포트 시 context 도 에러 -> null 일 경우 처리 필요 -> let으로 묶음
            context?.let { it1 ->
                AlertDialog.Builder(it1).run() {
                    setTitle("알림")
                    setIcon(android.R.drawable.ic_dialog_alert)
                    setMessage("fragment에서 dialog 테스트 입니다.")
                    setPositiveButton("예", null)
                    setNegativeButton("아니오", null)
                    show()
                }
            }
        }

        return binding.root // binding 리턴
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OneFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OneFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}