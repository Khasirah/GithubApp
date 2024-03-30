package com.peppo.githubapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.peppo.githubapp.model.response.DetailUserResponse

class FavouriteDiffCallback (private val oldFavList: List<DetailUserResponse>, private val newFavList: List<DetailUserResponse>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavList.size

    override fun getNewListSize(): Int = newFavList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavList[oldItemPosition].id == newFavList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFav = oldFavList[oldItemPosition]
        val newFav = newFavList[newItemPosition]
        return oldFav.id == newFav.id
    }

}