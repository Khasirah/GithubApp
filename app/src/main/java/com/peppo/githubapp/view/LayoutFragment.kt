package com.peppo.githubapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.peppo.githubapp.databinding.FragmentLayoutBinding
import com.peppo.githubapp.model.response.GithubUsersItem
import com.peppo.githubapp.view.adapter.LayoutAdapter
import com.peppo.githubapp.view.adapter.SectionsPagerAdapter
import com.peppo.githubapp.viewModel.DetailUserViewModel

class LayoutFragment : Fragment() {
    private var _binding: FragmentLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataUsername: String
    private var dataPosition: Int = 0
    private val detailUserViewModel by viewModels<DetailUserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLayoutBinding.inflate(inflater, container, false)

        // setup recycle view
        val layoutManager = LinearLayoutManager(context)
        binding.rvListFoll.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvListFoll.addItemDecoration(itemDecoration)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUsernameData()
        getPosition()
        when(dataPosition){
            0 -> {
                detailUserViewModel.findFollower(dataUsername)
                detailUserViewModel.githubUserFollowers.observe(viewLifecycleOwner) { followers ->
                    setGithubUsers(followers)
                }
            }
            1 -> {
                detailUserViewModel.findFollowing(dataUsername)
                detailUserViewModel.isLoading.observe(viewLifecycleOwner) {
                    showLoading(it)
                }
                detailUserViewModel.githubUserFollowing.observe(viewLifecycleOwner) {
                    following -> setGithubUsers(following)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getUsernameData() {
        if (arguments != null) {
            dataUsername = arguments?.getString(SectionsPagerAdapter.USERNAME).toString()
        }
    }

    private fun getPosition() {
        if (arguments != null) {
            dataPosition = arguments?.getInt(SectionsPagerAdapter.POSITION,0)!!
        }
    }

    private fun setGithubUsers(githubUsers: List<GithubUsersItem?>?) {
        val adapter = LayoutAdapter()
        adapter.submitList(githubUsers)
        binding.rvListFoll.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}