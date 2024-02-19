package com.example.repository.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.repository.databinding.ItemRepoBinding
import com.example.repository.model.Repo

class RepoAdapter(private val onClick: (Repo) -> Unit) :
    ListAdapter<Repo, RepoAdapter.RepoViewHolder>(diffUtil) {

    inner class RepoViewHolder(private val viewBinding: ItemRepoBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(item: Repo) {
            viewBinding.tvReponame.text = item.name
            viewBinding.tvDescription.text = item.description
            viewBinding.tvStarCount.text = item.starCount.toString()
            viewBinding.tvForkCount.text = "${item.forkCount}"

            viewBinding.root.setOnClickListener {
                onClick(item)
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            ItemRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}