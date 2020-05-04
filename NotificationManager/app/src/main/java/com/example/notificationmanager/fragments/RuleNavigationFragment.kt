package com.example.notificationmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.RuleWizardViewModel


class RuleNavigationFragment : Fragment(R.layout.fragment_rule_navigation) {

    lateinit var ruleWizardViewModel: RuleWizardViewModel
    lateinit var nextButton: Button
    lateinit var prevButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ruleWizardViewModel = ViewModelProviders.of(activity!!).get(RuleWizardViewModel::class.java)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

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
                nextButton.setTextColor(resources.getColor(R.color.black)) }
            else{
                nextButton.isClickable = false
                nextButton.focusable = View.NOT_FOCUSABLE
                nextButton.setTextColor(resources.getColor(R.color.lightgrey))}
        })

        nextButton.setOnClickListener(){
            ruleWizardViewModel.stepForward()
        }

        prevButton.setOnClickListener(){
            ruleWizardViewModel.stepBackwards()
        }

    }
}
