package com.example.notificationmanager.fragments.ruleCreation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.LimitNumberMode
import com.example.notificationmanager.ViewModels.RuleWizardViewModel
import com.example.notificationmanager.utils.Utils

class SelectAllowedNumberFragment : Fragment(R.layout.fragment_select_allowed_number) {

    private val STATE_LIMIT_NUMBER = "state_limit_number"
    private val STATE_LIMIT_MODE = "state_limit_mode"

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
        ruleWizardViewModel = ViewModelProvider(requireActivity()).get(RuleWizardViewModel::class.java)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        numberPicker = view.findViewById(R.id.numberpicker_allowed_number)
        radioButtonDay = view.findViewById(R.id.day_radioButton)
        radioButtonHour = view.findViewById(R.id.hour_radioButton)
        radioButtonWeek = view.findViewById(R.id.week_radioButton)

        numberPicker.maxValue = 1000
        numberPicker.minValue = 1
        numberPicker.value = 1

        if (savedInstanceState != null) {
            val currentNumber = savedInstanceState.getInt(STATE_LIMIT_NUMBER)
            val currentMode = savedInstanceState.getInt(STATE_LIMIT_MODE)
            ruleWizardViewModel.setSelectedLimitNumber(currentNumber)
            ruleWizardViewModel.setSelectedLimitNumberMode(
                Utils.timeSlotTypeToLimitNumberMode(
                    currentMode
                )
            )

        }

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("Kristian: Debug", "Class: ${javaClass.name} called onSaveInstanceState")
        val currentLimit = ruleWizardViewModel.getLimitNumber().value
        val currentMode = ruleWizardViewModel.getLimitNumberMode().value
        if (currentLimit != null) outState.putInt(STATE_LIMIT_NUMBER, currentLimit)
        if (currentMode != null) outState.putInt(
            STATE_LIMIT_MODE,
            Utils.limitNumberModeToTimeSlotType(currentMode)
        )
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
