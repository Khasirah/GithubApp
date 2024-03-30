package com.peppo.githubapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peppo.githubapp.model.response.GithubResponse
import com.peppo.githubapp.model.response.GithubUsersItem
import com.peppo.githubapp.model.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    companion object{
        private const val TAG = "MainViewModel"
    }
    private val _githubUsers = MutableLiveData<List<GithubUsersItem?>?>()
    val githubUsers : LiveData<List<GithubUsersItem?>?> = _githubUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun findGithubUsers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListGithub(username)
        client.enqueue(object: Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _githubUsers.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


}