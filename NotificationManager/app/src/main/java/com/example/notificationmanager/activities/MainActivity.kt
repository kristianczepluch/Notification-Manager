package com.example.notificationmanager.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.MainActivityViewModel
import com.example.notificationmanager.fragments.NotificationOverview
import com.example.notificationmanager.fragments.NotificationRules
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {

    lateinit var mainToolbar: Toolbar
    lateinit var viewPager: ViewPager2
    lateinit var myTabLayout: TabLayout
    private var doubleBackToExitPressedOnce = false
    private val mainActivityViewModel: MainActivityViewModel by viewModels()


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

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                //optionsMenu?.findItem(R.id.time_selection_menu1)?.isVisible = mainActivityViewModel.getCurrentPage().value == 1
                mainActivityViewModel.setCurrentPage(position)
            }
        })

        myTabLayout = findViewById(R.id.main_tablayout)
        TabLayoutMediator(myTabLayout, viewPager){ tab, position ->
            when(position){
                0 -> tab.text = getString(R.string.tab_name_0)
                1 -> tab.text = getString(R.string.tab_name_1)
            }
        }.attach()

    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            val homeIntent = Intent(Intent.ACTION_MAIN)
            homeIntent.addCategory(Intent.CATEGORY_HOME)
            homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(homeIntent)
        } else {
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, getString(R.string.tab_to_exit), Toast.LENGTH_SHORT).show()
            Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
        }
    }
}

class MainViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle){

    private val fragement_list = listOf(NotificationOverview(), NotificationRules())

    override fun getItemCount() = fragement_list.size

    override fun createFragment(position: Int): Fragment {
        return fragement_list[position]
    }
}
