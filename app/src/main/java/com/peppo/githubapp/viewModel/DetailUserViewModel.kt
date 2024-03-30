package com.peppo.githubapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peppo.githubapp.model.repository.FavouriteRepository
import com.peppo.githubapp.model.response.DetailUserResponse
import com.peppo.githubapp.model.response.GithubUsersItem
import com.peppo.githubapp.model.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application): ViewModel() {
    companion object{
        private const val TAG = "DetailUserViewModel"
    }

    private val mFavouriteRepository: FavouriteRepository = FavouriteRepository(application)

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _githubUserFollowers = MutableLiveData<List<GithubUsersItem?>?>()
    val githubUserFollowers : LiveData<List<GithubUsersItem?>?> = _githubUserFollowers

    private val _githubUserFollowing = MutableLiveData<List<GithubUsersItem?>?>()
    val githubUserFollowing : LiveData<List<GithubUsersItem?>?> = _githubUserFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun funDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object: Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun findFollower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListFollowers(username)
        client.enqueue(object: Callback<List<GithubUsersItem?>?> {
            override fun onResponse(
                call: Call<List<GithubUsersItem?>?>,
                response: Response<List<GithubUsersItem?>?>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _githubUserFollowers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GithubUsersItem?>?>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun findFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListFollowing(username)
        client.enqueue(object: Callback<List<GithubUsersItem?>?> {
            override fun onResponse(
                call: Call<List<GithubUsersItem?>?>,
                response: Response<List<GithubUsersItem?>?>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _githubUserFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GithubUsersItem?>?>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun insert(user: DetailUserResponse) {
        mFavouriteRepository.insert(user)
    }

    fun update(user: DetailUserResponse) {
        mFavouriteRepository.update(user)
    }

    fun delete(user: DetailUserResponse) {
        mFavouriteRepository.delete(user)
    }

    fun getAllFavourite(): LiveData<List<DetailUserResponse>> = mFavouriteRepository.getAllFavourite()

    fun getFavoriteUserByUsername(username: String): LiveData<DetailUserResponse> = mFavouriteRepository.getFavoriteUserByUsername(username)
}