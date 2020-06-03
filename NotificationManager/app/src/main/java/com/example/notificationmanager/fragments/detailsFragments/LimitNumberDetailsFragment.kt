package com.example.notificationmanager.fragments.detailsFragments

import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.DetailActivityViewModel
import com.example.notificationmanager.ViewModels.LimitNumberMode
import com.example.notificationmanager.utils.Utils


class LimitNumberDetailsFragment : Fragment(R.layout.fragment_limit_number_details) {

    private val detailActivityViewModel: DetailActivityViewModel by viewModels()
    private lateinit var numberPicker: NumberPicker
    private lateinit var radioButtonDay: RadioButton
    private lateinit var radioButtonWeek: RadioButton
    private lateinit var radioButtonHour: RadioButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        numberPicker = view.findViewById(R.id.rule_details_allowed_number)
        radioButtonDay = view.findViewById(R.id.day_radioButton)
        radioButtonHour = view.findViewById(R.id.hour_radioButton)
        radioButtonWeek = view.findViewById(R.id.week_radioButton)

        radioButtonHour.isClickable = false
        radioButtonHour.isFocusable = false
        radioButtonWeek.isClickable = false
        radioButtonWeek.isFocusable = false
        radioButtonDay.isFocusable = false
        radioButtonDay.isClickable = false

        numberPicker.isFocusable = false
        numberPicker.isClickable = false
        numberPicker.isActivated = false

        arguments?.getInt(BUNDLE_RULE_ID)?.let {
            detailActivityViewModel.getDetoxRuleEntity(it).observe(viewLifecycleOwner, Observer{ rule ->
                val limitNumber = rule.ruleLimitNumberAllowedNumber
                numberPicker.maxValue = limitNumber
                numberPicker.minValue = limitNumber
                numberPicker.value = limitNumber

                when(Utils.timeSlotTypeToLimitNumberMode(rule.ruleLimitNumberTimeSlotType)){
                    LimitNumberMode.HOUR -> {
                        radioButtonHour.isChecked = true
                    }
                    LimitNumberMode.WEEK -> {
                        radioButtonWeek.isChecked = true
                    }
                    LimitNumberMode.DAY -> {
                        radioButtonDay.isChecked = true
                    }
                    else -> throw Exception("Timeslot Mode not found")
                }
            })
        }

    }

    companion object {
        private val BUNDLE_RULE_ID = "bundle_rule_id"

        fun getInstance(ruleId: Int): Fragment{
            val bundle = Bundle()
            bundle.putInt(BUNDLE_RULE_ID, ruleId)
            val fragment = LimitNumberDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
