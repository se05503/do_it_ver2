package com.example.ch11_jetpack

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentViewPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
    val fragments: List<Fragment>
    init {
        fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment())
    }

    // 프래그먼트 항목 개수 반환
    override fun getItemCount(): Int = fragments.size

    // 항목을 구성하는 프래그먼트 객체 반환
    override fun createFragment(position: Int): Fragment = fragments[position]
}