package com.example.notificationmanager.fragments.detailsFragments

import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.DetailActivityViewModel

class LimitNumberDetailsBackgroundFragment : Fragment(R.layout.fragment_limit_number_details_background) {

    private val detailActivityViewModel: DetailActivityViewModel by viewModels()
    private lateinit var numberPicker: NumberPicker

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        numberPicker = view.findViewById(R.id.rule_details_left_number)

        numberPicker.isFocusable = false
        numberPicker.isClickable = false
        numberPicker.isActivated = false

        // Todo: Calculate remaining tries for given timeslot

        numberPicker.maxValue = 3
        numberPicker.minValue = 3
        numberPicker.value = 3
    }

    companion object{

        private val BUNDLE_RULE_ID = "bundle_rule_id"

        fun getInstance(ruleId: Int): Fragment{
            val bundle = Bundle()
            bundle.putInt(BUNDLE_RULE_ID, ruleId)
            val fragment = LimitNumberDetailsBackgroundFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
