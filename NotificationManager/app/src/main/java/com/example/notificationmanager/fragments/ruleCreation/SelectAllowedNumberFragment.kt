package com.example.notificationmanager.fragments.ruleCreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.LimitNumberMode
import com.example.notificationmanager.ViewModels.RuleWizardViewModel

class SelectAllowedNumberFragment : Fragment(R.layout.fragment_select_allowed_number) {

    private lateinit var numberPicker: NumberPicker
    private lateinit var radioButtonDay: RadioButton
    private lateinit var radioButtonHour: RadioButton
    private lateinit var radioButtonWeek: RadioButton

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

        numberPicker = view.findViewById(R.id.numberpicker_allowed_number)
        radioButtonDay = view.findViewById(R.id.day_radioButton)
        radioButtonHour = view.findViewById(R.id.hour_radioButton)
        radioButtonWeek = view.findViewById(R.id.week_radioButton)

        numberPicker.maxValue = 1000
        numberPicker.minValue = 0
        numberPicker.value = 0

        initRadioButtons(
            ruleWizardViewModel.selectedLimitNumberMode ?: LimitNumberMode.NOT_SELECTED
        )
        initNumberPicker(ruleWizardViewModel.selectedLimitNumber)

        radioButtonDay.setOnClickListener {
            ruleWizardViewModel.selectedLimitNumberMode = LimitNumberMode.DAY
        }

        radioButtonHour.setOnClickListener {
            ruleWizardViewModel.selectedLimitNumberMode = LimitNumberMode.HOUR
        }

        radioButtonWeek.setOnClickListener {
            ruleWizardViewModel.selectedLimitNumberMode = LimitNumberMode.WEEK
        }

        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            ruleWizardViewModel.selectedLimitNumber = newVal
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
