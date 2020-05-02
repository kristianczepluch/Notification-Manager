package com.example.notificationmanager.fragments.ruleCreation

import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.notificationmanager.R

class SelectAllowedNumberFragment : Fragment(R.layout.fragment_select_allowed_number) {

    lateinit var numberPicker: NumberPicker
    lateinit var radioGroup: RadioGroup


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        numberPicker = view.findViewById(R.id.numberpicker_allowed_number)
        radioGroup = view.findViewById(R.id.select_number_radioGroup)

        // Setup the numberpickers default
        numberPicker.maxValue = 1000
        numberPicker.minValue = 0
        numberPicker.value = 0


        radioGroup.setOnCheckedChangeListener{ _: RadioGroup, i: Int ->
            Toast.makeText(context, "Selected: $i", Toast.LENGTH_SHORT).show()
        }

    }
}
