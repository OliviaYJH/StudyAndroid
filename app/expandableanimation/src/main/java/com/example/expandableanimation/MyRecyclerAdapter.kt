package com.example.expandableanimation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.expandableanimation.databinding.ItemNewRecyclerBinding

class MyRecyclerAdapter : RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>() {
    var myList = arrayListOf<MyData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemNewRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mine = myList[position]
        holder.bind(mine)
    }

    inner class MyViewHolder(val binding: ItemNewRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(mine: MyData) {
            Log.i("bindView", "start")
            binding.tvRecyclerTitle.text = mine.title

            val layoutExpand = binding.linearLayout2
            binding.linearLayout1.setOnClickListener {
                val show = downButtonLayout(
                    !layoutExpand.isVisible,
                    it.findViewById(binding.btnDropDown.id),
                    layoutExpand
                )
                layoutExpand.isVisible = show
            }
        }

        private fun downButtonLayout(
            isExpanded: Boolean,
            view: View,
            layoutExpand: LinearLayout
        ): Boolean {
            MyAnimation.downButtonArrow(view, isExpanded)
            if (isExpanded) {
                MyAnimation.expandView(layoutExpand)
            } else {
                MyAnimation.collapseView(layoutExpand)
            }

            return isExpanded
        }
    }
}

data class MyData(
    var title: String,
    var detail: String
)