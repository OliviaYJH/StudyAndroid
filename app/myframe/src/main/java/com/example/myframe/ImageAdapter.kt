package com.example.myframe

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myframe.databinding.ItemImageBinding
import com.example.myframe.databinding.ItemLoadMoreBinding

class ImageAdapter(private val itemClickListener: ItemClickListener) : androidx.recyclerview.widget.ListAdapter<ImageItems, RecyclerView.ViewHolder>(
    // ListAdapter에서 반드시 구현해야 하는 것: DiffUtil
    object : DiffUtil.ItemCallback<ImageItems>() {
        /*
        - RecyclerAdapter vs ListAdapter: 변경사항에 대한 처리

        ListAdapter는 DiffUtil이 data가 변경되었음을 check
        -> ListAdapter가 알아서 어느 부분이 수정되었는지 check하고 알아서 notifyDataSetChanged() 등을 알아서 call해줌
           & 해당 내용이 MainThread에서 불릴 수 있는 처리 해줌

        RecyclerAdapter는 개발자가 직접 지정해주어야 함
         */

        override fun areItemsTheSame(oldItem: ImageItems, newItem: ImageItems): Boolean {
            // 같은 값을 참조하고 있는지 확인
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ImageItems, newItem: ImageItems): Boolean {
            return oldItem == newItem // equals()
        }

    }
) {
    // ListAdapter를 사용하고 다양한 type의 Item를 넣어보기

    override fun getItemCount(): Int {
        val originSize = currentList.size
        return if (originSize == 0) 0 else originSize.inc() // 1 추가
    }

    // 2가지의 item type을 사용할 예정!
    override fun getItemViewType(position: Int): Int {
        return if (itemCount.dec() == position) ITEM_LOAD_MORE // footer인 경우
        else ITEM_IMAGE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // type별로 ViewHolder 만들어주기

        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        return when (viewType) {
            ITEM_IMAGE -> {
                val binding = ItemImageBinding.inflate(inflater, parent, false)
                ImageViewHolder(binding)
            }

            else -> {
                val binding = ItemLoadMoreBinding.inflate(inflater, parent, false)
                ItemLoadMoreViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // 화면과 연결해주기

        when(holder) { // sealedClass이므로 잊지 않고 개발 가능
            is ImageViewHolder -> {
                holder.bind(currentList[position] as ImageItems.FrameImage)
            }
            is ItemLoadMoreViewHolder -> {
                holder.bind(itemClickListener)
            }
        }
    }

    // click listener
    interface ItemClickListener {
        fun onLoadMoreClick()
    }

    companion object {
        const val ITEM_IMAGE = 0 // 이미지
        const val ITEM_LOAD_MORE = 1 // 추가하기 버튼 이미지
    }

}

/*
ImageItems를 다른 곳에서 선언했을 때,
ImageItems 자체가 FrameImage와 LoadMore을 자식으로 가지고 있음 인지 가능
=> else 문을 사용하지 않아도 됨!
 */
sealed class ImageItems {
    data class FrameImage( // FrameImage와 LoadMore을 묶는 SealedClass
        val uri: Uri
    ) : ImageItems()

    /*
    하나만 들어갈 것이기 때문에 object 사용
    singleton으로 바로 만들어지는 것이 특징
     */
    object LoadMore : ImageItems()
}

class ImageViewHolder(private val binding: ItemImageBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ImageItems.FrameImage) {
        binding.ivPreview.setImageURI(item.uri)
    }
}

class ItemLoadMoreViewHolder(binding: ItemLoadMoreBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(itemClickListener: ImageAdapter.ItemClickListener) {
        itemView.setOnClickListener { itemClickListener.onLoadMoreClick() }
    }
}