package com.peppo.githubapp.view

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.peppo.githubapp.R
import com.peppo.githubapp.databinding.ActivityDetailUserBinding
import com.peppo.githubapp.helper.ViewModelFactory
import com.peppo.githubapp.model.response.DetailUserResponse
import com.peppo.githubapp.view.adapter.SectionsPagerAdapter
import com.peppo.githubapp.viewModel.DetailUserViewModel

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var username: String
    private lateinit var detailUser: DetailUserResponse
    private var isFav: Boolean = false
    private lateinit var detailUserViewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(application)
        detailUserViewModel = ViewModelProvider(this, factory)[DetailUserViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val username = intent.getStringExtra(GIT_USERNAME)

        if (username != null) {
            detailUserViewModel.funDetailUser(username)
        }

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailUserViewModel.detailUser.observe(this) { detailUser ->
            setDetailUserData(detailUser)
            this@DetailUserActivity.username = detailUser.login
            this.detailUser = detailUser
        }

        // setup fragment
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        if (username != null) {
            sectionsPagerAdapter.setUsername(username)
        }
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        // fab listener
        binding.fab.setOnClickListener {
            detailUserViewModel.insert(this.detailUser)
        }
    }

    private fun setDetailUserData(detailUser: DetailUserResponse) {
        Glide.with(binding.main)
            .load(detailUser.avatarUrl)
            .into(binding.civUserImage)
        binding.tvUsername.text = detailUser.name
        binding.tvUsernameItalic.text = detailUser.login
        binding.tvFollowers.text =
            resources.getString(R.string.followers, detailUser.followers.toString())
        binding.tvFollowing.text =
            resources.getString(R.string.following, detailUser.following.toString())
    }

    companion object {
        const val GIT_USERNAME = "GIT_USERNAME"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

//    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
//
//    }
}