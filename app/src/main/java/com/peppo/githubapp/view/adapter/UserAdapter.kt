package com.peppo.githubapp.view.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.peppo.githubapp.databinding.ItemGithubUserBinding
import com.peppo.githubapp.model.response.GithubUsersItem
import com.peppo.githubapp.view.DetailUserActivity

class UserAdapter: ListAdapter<GithubUsersItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ItemGithubUserBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("CheckResult")
        fun setGithubUserData(githubUser: GithubUsersItem){
            Glide.with(binding.cardView)
                .load(githubUser.avatarUrl)
                .into(binding.githubUserPhoto)
            binding.tvUsername.text = githubUser.login.toString()
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemGithubUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val githubUser = getItem(position)
        holder.setGithubUserData(githubUser)
        userClickedHandler(holder, githubUser.login.toString())
    }

    private fun userClickedHandler(holder: MyViewHolder, githubUser: String) {
        holder.itemView.setOnClickListener{
            val intentDetail = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intentDetail.putExtra(DetailUserActivity.GIT_USERNAME, githubUser)
            Intent(holder.itemView.context, SectionsPagerAdapter::class.java).putExtra(SectionsPagerAdapter.USERNAME, githubUser)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubUsersItem>() {
            override fun areItemsTheSame(oldItem: GithubUsersItem, newItem: GithubUsersItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: GithubUsersItem, newItem: GithubUsersItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}