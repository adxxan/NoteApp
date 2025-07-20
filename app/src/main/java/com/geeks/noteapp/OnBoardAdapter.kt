package com.geeks.noteapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return PageFragment().apply {
            arguments = Bundle().apply {
                putInt(PageFragment.ON_BOARD_KEY, position)
            }
        }
    }
}
