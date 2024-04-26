package com.example.mid20220961

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mid20220961.databinding.FragmentThreeBinding
import com.example.mid20220961.ui.MyTimeAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ThreeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThreeFragment : Fragment() {
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
    var datas = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentThreeBinding.inflate(inflater, container, false)
        var prevTime = 0L

        datas = savedInstanceState?.let {
            it.getStringArrayList("datas")?.toMutableList()
        } ?: mutableListOf<String>()

        val adapter = MyTimeAdapter(datas)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(activity, LinearLayoutManager.VERTICAL)
        )

        binding.start.setOnClickListener {
            binding.chronometer.base = SystemClock.elapsedRealtime() + prevTime
            binding.chronometer.start()
            binding.stop.isEnabled = true
            binding.reset.isEnabled = true
            binding.start.isEnabled = false
        }

        binding.stop.setOnClickListener {
            binding.chronometer.stop() // chromoter 중지
            // 언제 stop 버튼을 눌렀는지 확인 필요
            prevTime = binding.chronometer.base - SystemClock.elapsedRealtime() // start - stop을 하면 시간 차 간격 값이 담기게 됨

            binding.stop.isEnabled = false
            binding.reset.isEnabled = true
            binding.start.isEnabled = true
        }

        binding.reset.setOnClickListener {
            binding.chronometer.stop()
            binding.chronometer.base = SystemClock.elapsedRealtime()
            prevTime = 0L
            binding.stop.isEnabled = true
            binding.reset.isEnabled = false
            binding.start.isEnabled = true
            datas.clear()
            adapter.notifyDataSetChanged()
        }


        binding.save.setOnClickListener {
            datas.add(binding.chronometer.text.toString())
            adapter.notifyDataSetChanged()
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
         * @return A new instance of fragment ThreeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ThreeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("datas", ArrayList(datas))
    }
}