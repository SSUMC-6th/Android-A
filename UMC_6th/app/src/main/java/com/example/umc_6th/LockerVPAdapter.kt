package com.example.umc_6th

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myfirstapp.SavedAlbumFragment

class LockerVPAdapter (fragment: Fragment) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SavedSongFragment()
            else -> MusicFileFragment()
        }
    }


}