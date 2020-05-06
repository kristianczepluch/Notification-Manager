package com.example.notificationmanager.fragments.ruleCreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.RuleWizardViewModel


class SelectScheduleFragment : Fragment(R.layout.fragment_select_schedule) {

    lateinit var timePickerStart: TimePicker
    lateinit var timePickerEnd: TimePicker
    lateinit var ruleWizardViewModel: RuleWizardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ruleWizardViewModel = ViewModelProviders.of(activity!!).get(RuleWizardViewModel::class.java)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timePickerStart = view.findViewById(R.id.timePicker_start)
        timePickerEnd = view.findViewById(R.id.timePicker_end)

        timePickerStart.setIs24HourView(true)
        timePickerEnd.setIs24HourView(true)

        timePickerStart.hour = ruleWizardViewModel.selectedScheduleStartHour
        timePickerStart.minute = ruleWizardViewModel.selectedScheduleStartMinute

        timePickerEnd.hour = ruleWizardViewModel.selectedScheduleEndHour
        timePickerEnd.minute = ruleWizardViewModel.selectedScheduleEndMinute

        timePickerStart.setOnTimeChangedListener { _, hourOfDay, minute ->
            ruleWizardViewModel.selectedScheduleStartHour = hourOfDay
            ruleWizardViewModel.selectedScheduleStartMinute = minute
        }

        timePickerEnd.setOnTimeChangedListener { _, hourOfDay, minute ->
            ruleWizardViewModel.selectedScheduleEndHour = hourOfDay
            ruleWizardViewModel.selectedScheduleStartMinute = minute
        }

    }
}
