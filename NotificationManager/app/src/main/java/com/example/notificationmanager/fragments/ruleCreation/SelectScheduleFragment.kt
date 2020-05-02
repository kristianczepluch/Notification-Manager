package com.example.notificationmanager.fragments.ruleCreation

import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import com.example.notificationmanager.R


class SelectScheduleFragment : Fragment(R.layout.fragment_select_schedule) {

    lateinit var timePickerStart: TimePicker
    lateinit var timePickerEnd: TimePicker

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timePickerStart = view.findViewById(R.id.timePicker_start)
        timePickerEnd = view.findViewById(R.id.timePicker_end)

        timePickerStart.setIs24HourView(true)
        timePickerEnd.setIs24HourView(true)

    }
}
