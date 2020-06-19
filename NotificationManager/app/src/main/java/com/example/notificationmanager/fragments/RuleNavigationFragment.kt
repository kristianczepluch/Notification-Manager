package com.example.notificationmanager.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.RuleWizardViewModel


class RuleNavigationFragment : Fragment(R.layout.fragment_rule_navigation) {

    lateinit var nextButton: Button
    lateinit var prevButton: Button
    private val ruleWizardViewModel: RuleWizardViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nextButton = view.findViewById(R.id.next_button_wizard)
        prevButton = view.findViewById(R.id.back_button_wizard)

        ruleWizardViewModel.getCurrentSteps().observe(viewLifecycleOwner, Observer { item ->
            if(item == 0) { prevButton.visibility = View.INVISIBLE }
            else if(item > 0) {
                prevButton.visibility = View.VISIBLE
                nextButton.visibility = View.VISIBLE
            }
        })

        ruleWizardViewModel.getEnableNextButton().observe(viewLifecycleOwner, Observer{enable ->
            if(enable){
                nextButton.isClickable = true
                nextButton.focusable = View.FOCUSABLE
                nextButton.text = resources.getString(R.string.next)
                nextButton.setBackgroundColor(resources.getColor(R.color.white, null))
                nextButton.setTextColor(resources.getColor(R.color.black, null))
                nextButton.setOnClickListener(){
                    ruleWizardViewModel.stepForward()
                }
            }
            else{
                nextButton.isClickable = false
                nextButton.focusable = View.NOT_FOCUSABLE
                nextButton.text = resources.getString(R.string.next)
                nextButton.setBackgroundColor(resources.getColor(R.color.white, null))
                nextButton.setTextColor(resources.getColor(R.color.lightgrey, null))
            }
        })

        ruleWizardViewModel.getEnableCreateButton().observe(viewLifecycleOwner, Observer{enable ->
            if(enable){
                nextButton.isClickable = true
                nextButton.focusable = View.FOCUSABLE
                nextButton.text = resources.getString(R.string.create)
                nextButton.setBackgroundColor(resources.getColor(R.color.lightgreen, null))
                nextButton.setTextColor(resources.getColor(R.color.white, null))
                nextButton.setOnClickListener(){
                    ruleWizardViewModel.saveDetoxRule()
                    activity?.finish()
                }
            }
        })

        prevButton.setOnClickListener(){
            ruleWizardViewModel.stepBackwards()
        }

    }
}
