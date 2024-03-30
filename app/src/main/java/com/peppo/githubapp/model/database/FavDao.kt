package com.peppo.githubapp.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.peppo.githubapp.model.response.DetailUserResponse

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: DetailUserResponse)

    @Update
    fun update(user: DetailUserResponse)

    @Delete
    fun delete(user: DetailUserResponse)

    @Query("SELECT * FROM favourites")
    fun getAllFav(): LiveData<List<DetailUserResponse>>

    @Query("SELECT * FROM favourites WHERE login = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<DetailUserResponse>
}