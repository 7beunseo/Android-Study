package com.example.mid20220961.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mid20220961.databinding.TimeRecyclerviewBinding

class MyTimeViewHolder(val binding: TimeRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)


class MyTimeAdapter(val datas: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){ // 2-1 -> 물음표를 넣어 내가 전달하는 값은 null일수도 있다 지정
    override fun getItemCount(): Int {
        // datas의 크기를 전달해주어야 하는데 null인경우 0을 리턴하겠다는 뜻 (?:0 붙여서 넣어야 함)
        return datas.size;     // 2-2 -> 앞으로 쓸 datas에 물음표를 붙여주어야 함
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyTimeViewHolder(TimeRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyTimeViewHolder).binding
        val data = datas[position]      // 2-3 -> null이 아닌 경우에만 바인딩해주겠다는 뜻
        binding.time.text = data
    }
}
