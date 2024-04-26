package com.example.mid20220961

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch13_activity.MyAdapter
import com.example.mid20220961.databinding.FragmentOneBinding
import java.util.Arrays

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

    var datas: MutableList<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentOneBinding.inflate(inflater, container, false)

        /**
         * 데이터 초기화
         */
        datas = savedInstanceState?.let {
            it.getStringArrayList("datas")?.toMutableList()
        } ?: mutableListOf()

        /**
         * adapter, viewholder, layoutManager 설정
         */
        val adapter = MyAdapter(datas)
        binding.recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager

        /**
         * 반환 값 처리
         */
        val requestLaunchar: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            it.data!!.getStringExtra("result")?.let {  // data에 값이 있다면 -> null이 아니면
                if( it != "") {
                    datas?.add(it)
                    adapter.notifyDataSetChanged()
                }

            }
        }

        /**
         * intent 호출
         */
        binding.addBtn.setOnClickListener {
            val intent = Intent(requireContext(), SecondActivity::class.java)
            var selected: String
            if(binding.friend.isChecked) selected = binding.friend.text.toString()
            else selected = binding.place.text.toString()

            intent.putExtra("info", selected)
            requestLaunchar.launch(intent)
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

    /**
     * instanceState로 관리
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("datas", ArrayList(datas))
    }
}