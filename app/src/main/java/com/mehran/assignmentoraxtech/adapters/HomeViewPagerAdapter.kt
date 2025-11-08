package com.mehran.assignmentoraxtech.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.mehran.assignmentoraxtech.fragments.AddNoteFrag
import com.mehran.assignmentoraxtech.fragments.ViewNotesFrag

class HomeViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    val fragments = listOf<Fragment>(AddNoteFrag(), ViewNotesFrag())

    override fun getItem(position: Int): Fragment {
        if (position == 0)
            return fragments[0]
        else
            return fragments[1]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}