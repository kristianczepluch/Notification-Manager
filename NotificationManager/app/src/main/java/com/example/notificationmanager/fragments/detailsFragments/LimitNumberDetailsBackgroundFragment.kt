package com.example.notificationmanager.fragments.detailsFragments

import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.DetailActivityViewModel
import com.example.notificationmanager.algs.NotificationRuleMachine
import com.example.notificationmanager.detoxRules.LimitNumberRule

class LimitNumberDetailsBackgroundFragment : Fragment(R.layout.fragment_limit_number_details_background) {

    private var ruleId: Int? = null
    private val detailActivityViewModel: DetailActivityViewModel by viewModels()
    private lateinit var numberPicker: NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ruleId = it.getInt(BUNDLE_RULE_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        numberPicker = view.findViewById(R.id.rule_details_left_number)

        numberPicker.isFocusable = false
        numberPicker.isClickable = false
        numberPicker.isActivated = false

        ruleId?.let {
            detailActivityViewModel.getDetoxRuleEntity(it).observe(viewLifecycleOwner, Observer { rule ->
                val launches = (NotificationRuleMachine.entitiyToDetoxRule(rule, activity?.application!!) as LimitNumberRule).getRemainingLaunches()
                val display = if(launches<0) 0 else launches
                numberPicker.maxValue = display
                numberPicker.minValue = display
                numberPicker.value = display

            })
        }
    }

    companion object {
        private val BUNDLE_RULE_ID = "bundle_rule_id"

        @JvmStatic
        fun newInstance(ruleId: Int): Fragment =
            LimitNumberDetailsBackgroundFragment().apply {
                arguments = Bundle().apply {
                    putInt(BUNDLE_RULE_ID, ruleId)
                }
            }

    }
}
