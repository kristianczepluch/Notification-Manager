package com.example.notificationmanager.fragments.detailsFragments

import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.DetailActivityViewModel


class ScheduleDetailsFragment : Fragment(R.layout.fragment_schedule_details) {

    private var ruleId: Int? = null
    lateinit var timePickerStart: TimePicker
    lateinit var timePickerEnd: TimePicker
    private val detailActivityViewModel: DetailActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ruleId = it.getInt(BUNDLE_RULE_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timePickerStart = view.findViewById(R.id.details_timePicker_start)
        timePickerEnd = view.findViewById(R.id.details_timePicker_end)

        timePickerStart.setIs24HourView(true)
        timePickerEnd.setIs24HourView(true)

        timePickerEnd.isEnabled = false
        timePickerStart.isEnabled = false

        ruleId?.let { detailActivityViewModel.getDetoxRuleEntity(it) }?.observe(viewLifecycleOwner, Observer{ detoxRule ->

            val startMinute = detoxRule.ruleScheduleStartMinute
            val startHour = detoxRule.ruleScheduleStartHour
            val endHour = detoxRule.ruleScheduleEndHour
            val endMinute = detoxRule.ruleScheduleEndMinute

            timePickerStart.hour = if(startHour >= 0) startHour else 0
            timePickerStart.minute = if(startMinute >= 0) startMinute else 0

            timePickerEnd.hour = if(endHour >= 0) endHour else 0
            timePickerEnd.minute = if(endMinute >= 0) endMinute else 0
        })

    }

    companion object {

        private val BUNDLE_RULE_ID = "bundle_rule_id"

        @JvmStatic
        fun newInstance(ruleId: Int): Fragment =
            ScheduleDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(BUNDLE_RULE_ID, ruleId)
                }
            }
    }
}
