package com.peppo.githubapp.view

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.peppo.githubapp.R
import com.peppo.githubapp.databinding.ActivityMainBinding
import com.peppo.githubapp.model.response.GithubUsersItem
import com.peppo.githubapp.view.adapter.UserAdapter
import com.peppo.githubapp.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.topBar.title = getString(R.string.app_name)

        // setup recycle view layout
        val layoutManager = LinearLayoutManager(this)
        binding.rvListGithubUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvListGithubUser.addItemDecoration(itemDecoration)

        // search bar handler
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text.toString())
                    searchView.hide()
                    binding.tvNothing.visibility = View.GONE
                    mainViewModel.findGithubUsers(searchView.text.toString())
                    false
                }
        }

        // setup loading on screen
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        // set data to recycle view
        mainViewModel.githubUsers.observe(this) { githubUsers ->
            setGithubUsers(githubUsers)
        }
    }

    private fun setGithubUsers(githubUsers: List<GithubUsersItem?>?) {
        val adapter = UserAdapter()
        adapter.submitList(githubUsers)
        binding.rvListGithubUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}