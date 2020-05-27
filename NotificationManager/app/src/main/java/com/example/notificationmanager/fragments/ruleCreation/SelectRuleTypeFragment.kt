package com.example.notificationmanager.fragments.ruleCreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.RuleType
import com.example.notificationmanager.ViewModels.RuleWizardViewModel
import com.example.notificationmanager.adapter.SelectRuleAdapter
import com.example.notificationmanager.utils.Utils
import kotlinx.android.synthetic.main.select_rule_item.view.*

class SelectRuleTypeFragment : Fragment(R.layout.fragment_select_rule_type), RadioClickListener {

    private val STATE_RULETYPE = "state_ruletype"
    private lateinit var selectAppsRecyclerView: RecyclerView
    private lateinit var selectedAppsAdapter: SelectRuleAdapter
    private lateinit var ruleWizardViewModel: RuleWizardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ruleWizardViewModel = ViewModelProvider(requireActivity()).get(RuleWizardViewModel::class.java)
        if(savedInstanceState != null){
            val ruletype = savedInstanceState.getInt(STATE_RULETYPE)
            selectedAppsAdapter = SelectRuleAdapter(Utils.uiPositionToRuleType(ruletype),this)
            ruleWizardViewModel.setSelectedRuleType(ruleType = Utils.uiPositionToRuleType(ruletype))

        } else {
            selectedAppsAdapter = SelectRuleAdapter(ruleWizardViewModel.selectedRuleType.value ?: RuleType.SHORT_BREAK,this)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectAppsRecyclerView = view.findViewById(R.id.select_rule_recyclerview)
        selectAppsRecyclerView.layoutManager = LinearLayoutManager(context)
        selectAppsRecyclerView.hasFixedSize()
        selectAppsRecyclerView.adapter = selectedAppsAdapter
    }

    override fun onRadioButtonChecked(position: Int) {
        ruleWizardViewModel.setSelectedRuleType(Utils.uiPositionToRuleType(position))
    }

    override fun onRadioButtonUnchecked(position: Int) {
        selectAppsRecyclerView.findViewHolderForAdapterPosition(position)?.itemView?.select_rule_radioButton?.isChecked = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val myRuletype = ruleWizardViewModel.selectedRuleType.value
        if(myRuletype != null) outState.putInt(STATE_RULETYPE, Utils.ruleTypeToUIPosition(myRuletype))
    }
}


interface RadioClickListener {
    fun onRadioButtonChecked(position: Int)
    fun onRadioButtonUnchecked(position: Int)
}
