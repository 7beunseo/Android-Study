package com.example.myapplication10

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication10.databinding.ItemHinfoBinding

class PhpViewHolder(val binding: ItemHinfoBinding) : RecyclerView.ViewHolder(binding.root)

class PhpAdapter (val context: Context, val itemList: ArrayList<HinfoData>): RecyclerView.Adapter<PhpViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhpViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PhpViewHolder(ItemHinfoBinding.inflate(layoutInflater))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: PhpViewHolder, position: Int) {
        val data = itemList.get(position)

        holder.binding.run {
            //
            tvName.text = data.name
            tvAge.text = data.age.toString()
            tvAddr.text = data.addr
        }
    }
}