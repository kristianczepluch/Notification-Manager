package com.example.notificationmanager.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.RuleType
import com.example.notificationmanager.ViewModels.RuleWizardViewModel
import com.example.notificationmanager.fragments.ruleCreation.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class RuleWizardActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager2
    lateinit var myTabLayout: TabLayout
    lateinit var myAdapter: RuleCreationViewPagerAdapter

    val ruleWizardViewModel by lazy { ViewModelProvider(this).get(RuleWizardViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rule_wizzard)

        // Setup the viewPager
        viewPager = findViewById(R.id.maincontent_fragment_container)
        viewPager.isUserInputEnabled = false

        myAdapter = RuleCreationViewPagerAdapter(
            supportFragmentManager,
            lifecycle
        )

        viewPager.adapter = myAdapter
        myAdapter.hasStableIds()

        myTabLayout = findViewById(R.id.tablayout_rulecreation)
        TabLayoutMediator(myTabLayout, viewPager){ _, _ -> }.attach()

        ruleWizardViewModel.getCurrentSteps().observe(this, Observer{ step ->
            viewPager.setCurrentItem(step)
        })

        ruleWizardViewModel.getSelectedRuleType().observe(this, Observer { ruleType ->
            myAdapter.removeRuleFragments()
            myAdapter.addFragmentForRuleType(ruleType)
        })

    }

    inner class RuleCreationViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle){

        val selectApplicationsFragment = SelectApplicationsFragment()
        val selectRuleTypeFragment = SelectRuleTypeFragment()
        val fragement_list = mutableListOf(selectApplicationsFragment, selectRuleTypeFragment)
        val fragment_ids = mutableListOf(selectApplicationsFragment.hashCode().toLong(), selectRuleTypeFragment.hashCode().toLong())

        fun addFragmentForRuleType(ruleType: RuleType){

            val reviewFragment = ReviewFragment()

            when(ruleType){
                RuleType.LIMIT_NUMBER -> {
                    val fragmentToAdd1 = SelectAllowedNumberFragment()
                    fragement_list.add(fragmentToAdd1)
                    fragement_list.add(reviewFragment)
                    fragment_ids.add(fragmentToAdd1.hashCode().toLong())
                    fragment_ids.add(reviewFragment.hashCode().toLong())
                    notifyDataSetChanged()
                }
                RuleType.ETERNALLY -> {
                    fragement_list.add(reviewFragment)
                    fragment_ids.add(reviewFragment.hashCode().toLong())
                    notifyDataSetChanged()
                }
                RuleType.SHORT_BREAK -> {
                    val fragmentToAdd1 = SelectBreakTimeFragment()
                    fragement_list.add(fragmentToAdd1)
                    fragement_list.add(reviewFragment)
                    fragment_ids.add(fragmentToAdd1.hashCode().toLong())
                    fragment_ids.add(reviewFragment.hashCode().toLong())
                    notifyDataSetChanged()
                }
                RuleType.SCHEDULE -> {
                    val fragmentToAdd1 = SelectScheduleFragment()
                    fragement_list.add(fragmentToAdd1)
                    fragement_list.add(reviewFragment)
                    fragment_ids.add(fragmentToAdd1.hashCode().toLong())
                    fragment_ids.add(reviewFragment.hashCode().toLong())
                    notifyDataSetChanged()
                }
            }
        }

        fun removeRuleFragments(){
            fragement_list.clear()
            fragment_ids.clear()
            fragement_list.add(selectApplicationsFragment)
            fragement_list.add(selectRuleTypeFragment)
            fragment_ids.add(selectApplicationsFragment.hashCode().toLong())
            fragment_ids.add(selectRuleTypeFragment.hashCode().toLong())

        }

        override fun containsItem(itemId: Long): Boolean {
            return fragment_ids.contains(itemId)
        }

        override fun getItemId(position: Int): Long {
            return fragment_ids.get(position)
        }

        override fun getItemCount() = fragement_list.size

        override fun createFragment(position: Int) = fragement_list[position]

    }

}
