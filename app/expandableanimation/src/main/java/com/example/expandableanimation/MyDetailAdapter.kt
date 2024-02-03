package com.example.expandableanimation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expandableanimation.databinding.ItemDetailRecyclerBinding

class MyDetailAdapter: RecyclerView.Adapter<MyDetailAdapter.DetailViewHolder>() {
    var myDetailList = arrayListOf<DetailData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val binding = ItemDetailRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(binding)
    }

    override fun getItemCount(): Int = myDetailList.size

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val data = myDetailList[position]
        holder.bind(data)
    }

    inner class DetailViewHolder(val binding: ItemDetailRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(detail: DetailData) {
            binding.tvRecyclerDetail.text = detail.detail
        }
    }
}

data class DetailData(
    var detail: String
)