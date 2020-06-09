package com.example.notificationmanager.fragments.detailsFragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.DetailActivityViewModel
import com.example.notificationmanager.adapter.SelectWeekdayAdapter

class ScheduleDetailsBackgroundFragment : Fragment(R.layout.fragment_schedule_details_background) {

    private var ruleId: Int? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var weekdaysAdapter: SelectWeekdayAdapter
    private val detailActivityViewModel: DetailActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ruleId = it.getInt(BUNDLE_RULE_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.details_recyclerView_weekdays)

        ruleId?.let { detailActivityViewModel.getDetoxRuleEntity(it) }?.observe(viewLifecycleOwner, Observer{ detoxRule ->

            val selectedWeekdayArray = BooleanArray(7)
            selectedWeekdayArray[0] = detoxRule.ruleScheduleMO
            selectedWeekdayArray[1] = detoxRule.ruleScheduleTU
            selectedWeekdayArray[2] = detoxRule.ruleScheduleWE
            selectedWeekdayArray[3] = detoxRule.ruleScheduleTH
            selectedWeekdayArray[4] = detoxRule.ruleScheduleFR
            selectedWeekdayArray[5] = detoxRule.ruleScheduleSA
            selectedWeekdayArray[6] = detoxRule.ruleScheduleSU

            weekdaysAdapter = SelectWeekdayAdapter(selectedArray = selectedWeekdayArray, readOnly = true)

            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = weekdaysAdapter


        })
    }

    companion object {

        private val BUNDLE_RULE_ID = "bundle_rule_id"

        @JvmStatic
        fun newInstance(ruleId: Int): Fragment =
            ScheduleDetailsBackgroundFragment().apply {
                arguments = Bundle().apply {
                    putInt(BUNDLE_RULE_ID, ruleId)
                }
            }
    }
}
