package com.example.notificationmanager.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.notificationmanager.R
import com.example.notificationmanager.fragments.ruleCreation.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class RuleWizardActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager2
    lateinit var myTabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rule_wizzard)

        // Setup the viewPager
        viewPager = findViewById(R.id.maincontent_fragment_container)
        viewPager.adapter =
            RuleCreationViewPagerAdapter(
                supportFragmentManager,
                lifecycle
            )

        myTabLayout = findViewById(R.id.tablayout_rulecreation)
        TabLayoutMediator(myTabLayout, viewPager){ _, _ ->
            // some implementation
        }.attach()
    }

    class RuleCreationViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle){

        private val fragement_list = listOf(SelectApplicationsFragment(), SelectRuleTypeFragment(), SelectBreakTimeFragment(), SelectAllowedNumberFragment(), SelectScheduleFragment())

        override fun getItemCount() = fragement_list.size

        override fun createFragment(position: Int): Fragment {
            return fragement_list[position]
        }
    }
}
