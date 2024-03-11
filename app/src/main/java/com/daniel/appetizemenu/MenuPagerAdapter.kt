package com.daniel.appetizemenu

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MenuPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val fragments: List<Fragment> = listOf(
        FragmentEntradas(),
        FragmentPratosPrincipais(),
        FragmentBebidas(),
        FragmentSobremesas()
    )

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

}