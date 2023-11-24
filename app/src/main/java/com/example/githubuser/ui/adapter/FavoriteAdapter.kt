package com.example.githubuser.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.data.local.entity.FavoriteEntity
import com.example.githubuser.databinding.ItemRowBinding

class FavoriteAdapter : ListAdapter<FavoriteEntity, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(
                getItem(position)
            )
        }
    }

    class MyViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(favorite: FavoriteEntity) {
            with(binding) {
                tvUsername.text = favorite.username

                Glide.with(itemView.context)
                    .load(favorite.avatarUrl)
                    .skipMemoryCache(true)
                    .circleCrop()
                    .into(ivAvatar)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<FavoriteEntity> =
            object : DiffUtil.ItemCallback<FavoriteEntity>() {
                override fun areItemsTheSame(
                    oldItem: FavoriteEntity,
                    newItem: FavoriteEntity
                ): Boolean {
                    return oldItem.username == newItem.username
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: FavoriteEntity,
                    newItem: FavoriteEntity
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}