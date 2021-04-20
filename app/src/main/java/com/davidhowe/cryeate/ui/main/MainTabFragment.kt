package com.davidhowe.cryeate.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.davidhowe.cryeate.R
import com.davidhowe.cryeate.ui.main.watchlist.WatchListFragment
import kotlinx.android.synthetic.main.fragment_main_tab.*
import timber.log.Timber

class MainTabFragment : Fragment() {
    private lateinit var mainAdapter: MainAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainAdapter = MainAdapter(this)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = mainAdapter

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            Timber.d("id=${item.itemId}")
                when (item.itemId) {
                    R.id.page_1 -> {
                        Timber.d("page_1")
                        viewPager.currentItem = 0
                        true
                    }
                    R.id.page_2 -> {
                        Timber.d("page_2")
                        viewPager.currentItem = 1
                        true
                    }
                    else -> false
                }
        }
    }

    class MainAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        private val tabCount = 2

        override fun getItemCount(): Int = tabCount

        override fun createFragment(position: Int): Fragment {
            // Return a NEW fragment instance in createFragment(int)
            Timber.d("createFragment position=$position")
            return when(position) {
                0 -> WatchListFragment()
                else -> WatchListFragment()
            }
        }
    }
}