package com.example.notificationmanager.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.notificationmanager.R
import com.example.notificationmanager.fragments.ManipulatedNotificationsFragment
import com.example.notificationmanager.fragments.NotificationOverview
import com.example.notificationmanager.fragments.NotificationRules
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var mainToolbar: Toolbar
    lateinit var viewPager: ViewPager2
    lateinit var myTabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup the toolbar
        mainToolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(mainToolbar)

        // Setup the viewPager
        viewPager = findViewById(R.id.main_viewpager)
        viewPager.adapter =
            MainViewPagerAdapter(
                supportFragmentManager,
                lifecycle
            )

        myTabLayout = findViewById(R.id.main_tablayout)
        TabLayoutMediator(myTabLayout, viewPager){ tab, position ->
            when(position){
                0 -> tab.text = getString(R.string.tab_name_0)
                1 -> tab.text = getString(R.string.tab_name_1)
                2 -> tab.text = getString(R.string.tab_name_2)
            }
        }.attach()

    }
}

class MainViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle){

    private val fragement_list = listOf(NotificationOverview(), NotificationRules(), ManipulatedNotificationsFragment())

    override fun getItemCount() = fragement_list.size

    override fun createFragment(position: Int): Fragment {
        return fragement_list[position]
    }
}
