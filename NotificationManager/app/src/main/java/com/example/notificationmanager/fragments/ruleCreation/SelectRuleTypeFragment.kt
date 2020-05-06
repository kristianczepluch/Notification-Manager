package com.example.notificationmanager.fragments.ruleCreation

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.RuleType
import com.example.notificationmanager.ViewModels.RuleWizardViewModel
import com.example.notificationmanager.adapter.SelectRuleAdapter
import kotlinx.android.synthetic.main.select_rule_item.view.*

class SelectRuleTypeFragment : Fragment(R.layout.fragment_select_rule_type), RadioClickListener {

    lateinit var selectAppsRecyclerView: RecyclerView
    lateinit var selectedAppsAdapter: SelectRuleAdapter
    lateinit var ruleWizardViewModel: RuleWizardViewModel



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectAppsRecyclerView = view.findViewById(R.id.select_rule_recyclerview)
        ruleWizardViewModel = ViewModelProviders.of(activity!!).get(RuleWizardViewModel::class.java)
        selectedAppsAdapter = SelectRuleAdapter(this)
        selectAppsRecyclerView.layoutManager = LinearLayoutManager(context)
        selectAppsRecyclerView.adapter = selectedAppsAdapter

        ruleWizardViewModel.getSelectedRuleType().observe(viewLifecycleOwner, Observer{ruleType ->
            selectAppsRecyclerView.doOnPreDraw {
                val uiPosition = ruleTypeToUIPosition(ruleType)
                for(i in 0..3){
                    val myView = selectAppsRecyclerView.findViewHolderForAdapterPosition(i)
                    if(myView!= null) myView.itemView.select_rule_radioButton.isChecked = false
                }
                val targetView = selectAppsRecyclerView.findViewHolderForAdapterPosition(uiPosition)
                if(targetView != null) targetView.itemView.select_rule_radioButton.isChecked = true
            }
        })
    }

    override fun checkRadioButton(position: Int){
        ruleWizardViewModel.setSelectedRuleType(uiPositionToRuleType(position))
    }

    private fun uiPositionToRuleType(position: Int) =
        when(position){
            0 -> RuleType.SHORT_BREAK
            1 -> RuleType.SCHEDULE
            2 -> RuleType.LIMIT_NUMBER
            3 -> RuleType.ETERNALLY
            else -> throw IllegalArgumentException("Unkown Ruletype")
        }

    private fun ruleTypeToUIPosition(ruleType: RuleType) =
        when(ruleType){
            RuleType.SHORT_BREAK -> 0
            RuleType.SCHEDULE -> 1
            RuleType.LIMIT_NUMBER -> 2
            RuleType.ETERNALLY -> 3
        }
}

interface RadioClickListener {
    fun checkRadioButton(position: Int)
}
