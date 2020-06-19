package com.example.notificationmanager.fragments.ruleCreation

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.RuleWizardViewModel

class SelectBreakTimeFragment : Fragment(R.layout.fragment_select_break_time) {

    lateinit var minutePicker: NumberPicker
    lateinit var hourPicker: NumberPicker
    lateinit var hoursTextView: TextView
    lateinit var minutesTextView: TextView
    private val ruleWizardViewModel: RuleWizardViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        minutePicker = view.findViewById(R.id.numberpicker_shortbreak_minute_rule)
        hourPicker = view.findViewById(R.id.numberpicker_shortbreak_hour_rule)
        hoursTextView = view.findViewById(R.id.minTextView)
        minutesTextView = view.findViewById(R.id.hrsTextView)

        /* Because there isnt much space hide the descriptions texts in landscape mode*/
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            hoursTextView.visibility = View.INVISIBLE
            minutesTextView.visibility = View.INVISIBLE
        } else {
            hoursTextView.visibility = View.VISIBLE
            minutesTextView.visibility = View.VISIBLE
        }

        minutePicker.minValue = 0
        minutePicker.maxValue = 59
        minutePicker.value = 0

        hourPicker.minValue = 0
        hourPicker.maxValue = 23
        hourPicker.value = 0

        minutePicker.value = ruleWizardViewModel.selectedBreakTimeMinutes
        hourPicker.value = ruleWizardViewModel.selectedBreakTimeHours


        minutePicker.setOnValueChangedListener { _, _, newVal ->
            ruleWizardViewModel.setSelectedBreakTimeMinute(newVal)
        }

        hourPicker.setOnValueChangedListener { _, _, newVal ->
            ruleWizardViewModel.setSelectedBreakTimeHour(newVal)
        }

    }

}
