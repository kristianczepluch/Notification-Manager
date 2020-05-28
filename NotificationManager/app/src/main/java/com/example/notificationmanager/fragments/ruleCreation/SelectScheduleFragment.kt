package com.example.notificationmanager.fragments.ruleCreation

import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.RuleWizardViewModel


class SelectScheduleFragment : Fragment(R.layout.fragment_select_schedule) {

    lateinit var timePickerStart: TimePicker
    lateinit var timePickerEnd: TimePicker
    private val ruleWizardViewModel: RuleWizardViewModel by activityViewModels()

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
            ruleWizardViewModel.setScheduleStartHour(hourOfDay)
            ruleWizardViewModel.setScheduleStartMinute(minute)
        }

        timePickerEnd.setOnTimeChangedListener { _, hourOfDay, minute ->
            ruleWizardViewModel.setScheduleEndHour(hourOfDay)
            ruleWizardViewModel.setScheduleEndMinute(minute)
        }

    }
}
