package com.example.notificationmanager.fragments.detailsFragments

import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.DetailActivityViewModel

class ShortBreakDetailsFragment : Fragment(R.layout.fragment_short_break_details) {

    private var ruleId: Int? = null
    private val detailActivityViewModel: DetailActivityViewModel by viewModels()
    private lateinit var minutePicker: NumberPicker
    private lateinit var hourPicker: NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ruleId = it.getInt(BUNDLE_RULE_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        minutePicker = view.findViewById(R.id.numberpicker_shortbreak_minute_rule)
        hourPicker = view.findViewById(R.id.numberpicker_shortbreak_hour_rule)

        ruleId?.let {
            detailActivityViewModel.getDetoxRuleEntity(it)
                .observe(viewLifecycleOwner, Observer { rule ->

                    val hour = rule.ruleBreakTimeHours
                    val minute = rule.ruleBreakTimeMinutes

                    minutePicker.minValue = minute
                    minutePicker.maxValue = minute
                    minutePicker.value = minute

                    hourPicker.minValue = hour
                    hourPicker.maxValue = hour
                    hourPicker.value = hour

                    minutePicker.isFocusable = false
                    minutePicker.isClickable = false
                    minutePicker.isActivated = false

                    hourPicker.isFocusable = false
                    hourPicker.isClickable = false
                    hourPicker.isActivated = false

                })
        }

    }

    companion object {
        private val BUNDLE_RULE_ID = "bundle_rule_id"

        @JvmStatic
        fun newInstance(ruleId: Int): Fragment  {

            return ShortBreakDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(BUNDLE_RULE_ID, ruleId)
                }
            }
        }

    }
}
