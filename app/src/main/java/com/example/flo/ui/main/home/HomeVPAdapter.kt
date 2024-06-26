package com.example.flo.ui.main.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeVPAdapter(fragment: Fragment):FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int =3

    override fun createFragment(position: Int): Fragment{
        return when(position){
            0 -> HomePannel1Fragment()
            1 -> HomePannel2Fragment()
            else -> HomePannel3Fragment()
        }
    }
}