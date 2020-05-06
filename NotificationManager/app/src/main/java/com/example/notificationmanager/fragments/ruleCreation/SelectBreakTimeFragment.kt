package com.example.notificationmanager.fragments.ruleCreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.RuleWizardViewModel

class SelectBreakTimeFragment : Fragment(R.layout.fragment_select_break_time) {

    lateinit var minutePicker: NumberPicker
    lateinit var hourPicker: NumberPicker
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
        minutePicker = view.findViewById(R.id.numberpicker_shortbreak_minute_rule)
        hourPicker = view.findViewById(R.id.numberpicker_shortbreak_hour_rule)

        minutePicker.minValue = 0
        minutePicker.maxValue = 59
        minutePicker.value = 0

        hourPicker.minValue = 0
        hourPicker.maxValue = 23
        hourPicker.value = 0

        minutePicker.value = ruleWizardViewModel.selectedBreakTimeMinutes
        hourPicker.value = ruleWizardViewModel.selectedBreakTimeHours

        minutePicker.setOnValueChangedListener { picker, oldVal, newVal ->
            ruleWizardViewModel.selectedBreakTimeMinutes = newVal
        }

        hourPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            ruleWizardViewModel.selectedBreakTimeHours = newVal
        }

    }

}
