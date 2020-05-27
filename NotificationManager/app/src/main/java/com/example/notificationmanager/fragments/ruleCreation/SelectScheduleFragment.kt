package com.example.notificationmanager.fragments.ruleCreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.RuleWizardViewModel


class SelectScheduleFragment : Fragment(R.layout.fragment_select_schedule) {

    private val SATE_START_MINUTE = "state_start_minute"
    private val SATE_END_MINUTE = "state_end_minute"
    private val SATE_START_HOUR = "state_start_hour"
    private val SATE_END_HOUR = "state_end_hour"

    private lateinit var timePickerStart: TimePicker
    private lateinit var timePickerEnd: TimePicker
    private lateinit var ruleWizardViewModel: RuleWizardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ruleWizardViewModel = ViewModelProvider(requireActivity()).get(RuleWizardViewModel::class.java)

        if(savedInstanceState != null){
            ruleWizardViewModel.setScheduleStartHour(savedInstanceState.getInt(SATE_START_HOUR))
            ruleWizardViewModel.setScheduleStartMinute(savedInstanceState.getInt(SATE_START_MINUTE))
            ruleWizardViewModel.setScheduleEndHour(savedInstanceState.getInt(SATE_END_HOUR))
            ruleWizardViewModel.setScheduleEndMinute(savedInstanceState.getInt(SATE_END_MINUTE))
        }
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
            ruleWizardViewModel.setScheduleStartHour(hourOfDay)
            ruleWizardViewModel.setScheduleStartMinute(minute)
        }

        timePickerEnd.setOnTimeChangedListener { _, hourOfDay, minute ->
            ruleWizardViewModel.setScheduleEndHour(hourOfDay)
            ruleWizardViewModel.setScheduleEndMinute(minute)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SATE_START_MINUTE, ruleWizardViewModel.selectedScheduleStartMinute)
        outState.putInt(SATE_START_HOUR, ruleWizardViewModel.selectedScheduleStartHour)
        outState.putInt(SATE_END_MINUTE, ruleWizardViewModel.selectedScheduleEndMinute)
        outState.putInt(SATE_END_HOUR, ruleWizardViewModel.selectedScheduleEndHour)
    }
}
