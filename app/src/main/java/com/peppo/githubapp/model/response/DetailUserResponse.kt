package com.peppo.githubapp.model.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favourites")
@Parcelize
data class DetailUserResponse(

	@ColumnInfo(name = "login")
	@field:SerializedName("login")
	val login: String,

	@PrimaryKey(autoGenerate = false)
	@ColumnInfo(name = "id")
	@field:SerializedName("id")
	val id: Int,

	@ColumnInfo(name = "followers")
	@field:SerializedName("followers")
	val followers: Int,

	@ColumnInfo(name = "avatar_url")
	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@ColumnInfo(name = "following")
	@field:SerializedName("following")
	val following: Int,

	@ColumnInfo(name = "name")
	@field:SerializedName("name")
	val name: String,

): Parcelable
