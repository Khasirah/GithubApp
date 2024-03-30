package com.peppo.githubapp.view.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.peppo.githubapp.view.LayoutFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    companion object{
        const val USERNAME = "username"
        const val POSITION = "0"
    }
    private lateinit var username: String
    override fun getItemCount(): Int {
        return 2
    }
    override fun createFragment(position: Int): Fragment {
        val mBundle = Bundle()
        mBundle.putString(USERNAME, getUsername())
        mBundle.putInt(POSITION, if (position==0) 0 else 1)
        val fragment = LayoutFragment()
        fragment.arguments = mBundle
        return fragment
    }

    fun setUsername(dataUsername: String) {
        username = dataUsername
    }

    private fun getUsername() : String = username
}