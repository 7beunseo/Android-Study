package com.example.ch13_activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mid20220961.databinding.ItemRecyclerviewBinding
import java.text.SimpleDateFormat

class MyViewHolder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)


class MyAdapter(val datas: MutableList<String>?): RecyclerView.Adapter<RecyclerView.ViewHolder>(){ // 2-1 -> 물음표를 넣어 내가 전달하는 값은 null일수도 있다 지정
    override fun getItemCount(): Int {
        // datas의 크기를 전달해주어야 하는데 null인경우 0을 리턴하겠다는 뜻 (?:0 붙여서 넣어야 함)
        return datas?.size ?:0;     // 2-2 -> 앞으로 쓸 datas에 물음표를 붙여주어야 함
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        val data = datas!![position]      // 2-3 -> null이 아닌 경우에만 바인딩해주겠다는 뜻
        binding.first.text = data.split("\n")[0]
        binding.second.text = data.split("\n")[1]
        /**
         * 현재 날짜 출력
         */
        val dataFormat = SimpleDateFormat("yyyy-MM-dd")
        binding.date.text = dataFormat.format(System.currentTimeMillis())
    }
}
