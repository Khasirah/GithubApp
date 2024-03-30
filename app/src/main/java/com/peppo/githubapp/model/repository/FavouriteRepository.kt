package com.peppo.githubapp.model.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.peppo.githubapp.model.database.FavDao
import com.peppo.githubapp.model.database.FavouriteRoomDatabase
import com.peppo.githubapp.model.response.DetailUserResponse
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavouriteRepository(application: Application) {
    private val mFavDao: FavDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavouriteRoomDatabase.getDatabase(application)
        mFavDao = db.favDao()
    }

    fun getAllFavourite(): LiveData<List<DetailUserResponse>> = mFavDao.getAllFav()

    fun insert(user: DetailUserResponse) {
        executorService.execute { mFavDao.insert(user) }
    }

    fun update(user: DetailUserResponse) {
        executorService.execute { mFavDao.update(user) }
    }

    fun delete(user: DetailUserResponse) {
        executorService.execute { mFavDao.delete(user) }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<DetailUserResponse> = mFavDao.getFavoriteUserByUsername(username)
}