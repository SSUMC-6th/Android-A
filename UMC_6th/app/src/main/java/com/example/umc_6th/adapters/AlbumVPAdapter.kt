package com.example.umc_6th.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.umc_6th.music.SongFragment
import com.example.umc_6th.ui.DetailFragment
import com.example.umc_6th.ui.VideoFragment

class AlbumVPAdapter(fragment:Fragment) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SongFragment()
            1 -> DetailFragment()
            else -> VideoFragment()
        }
    }
}