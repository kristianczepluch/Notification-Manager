package com.example.notificationmanager.fragments.ruleCreation

import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.LimitNumberMode
import com.example.notificationmanager.ViewModels.RuleWizardViewModel

class SelectAllowedNumberFragment : Fragment(R.layout.fragment_select_allowed_number) {

    private lateinit var numberPicker: NumberPicker
    private lateinit var radioButtonDay: RadioButton
    private lateinit var radioButtonHour: RadioButton
    private lateinit var radioButtonWeek: RadioButton
    private val ruleWizardViewModel: RuleWizardViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        numberPicker = view.findViewById(R.id.numberpicker_allowed_number)
        radioButtonDay = view.findViewById(R.id.day_radioButton)
        radioButtonHour = view.findViewById(R.id.hour_radioButton)
        radioButtonWeek = view.findViewById(R.id.week_radioButton)

        numberPicker.maxValue = 1000
        numberPicker.minValue = 1
        numberPicker.value = 1

        initRadioButtons(
            ruleWizardViewModel.getLimitNumberMode().value ?: LimitNumberMode.DAY
        )
        initNumberPicker(ruleWizardViewModel.getLimitNumber().value ?: 1)

        radioButtonDay.setOnClickListener {
            ruleWizardViewModel.setSelectedLimitNumberMode(LimitNumberMode.DAY)
        }

        radioButtonHour.setOnClickListener {
            ruleWizardViewModel.setSelectedLimitNumberMode(LimitNumberMode.HOUR)
        }

        radioButtonWeek.setOnClickListener {
            ruleWizardViewModel.setSelectedLimitNumberMode(LimitNumberMode.WEEK)
        }

        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            ruleWizardViewModel.setSelectedLimitNumber(newVal)
        }

    }

    private fun initRadioButtons(limitNumberMode: LimitNumberMode) {
        when (limitNumberMode) {
            LimitNumberMode.HOUR -> radioButtonHour.isChecked = true
            LimitNumberMode.WEEK -> radioButtonWeek.isChecked = true
            LimitNumberMode.DAY -> radioButtonDay.isChecked = true
            LimitNumberMode.NOT_SELECTED -> {
                radioButtonDay.isChecked = false
                radioButtonHour.isChecked = false
                radioButtonWeek.isChecked = false
            }
        }
    }

    private fun initNumberPicker(number: Int) {
        numberPicker.value = number
    }
}
