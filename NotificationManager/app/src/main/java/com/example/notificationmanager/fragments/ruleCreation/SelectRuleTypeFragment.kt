package com.example.notificationmanager.fragments.ruleCreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.RuleType
import com.example.notificationmanager.ViewModels.RuleWizardViewModel
import com.example.notificationmanager.adapter.SelectRuleAdapter
import com.example.notificationmanager.utils.Utils

class SelectRuleTypeFragment : Fragment(R.layout.fragment_select_rule_type), RadioClickListener {

    private lateinit var selectAppsRecyclerView: RecyclerView
    private lateinit var selectedAppsAdapter: SelectRuleAdapter
    private lateinit var ruleWizardViewModel: RuleWizardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ruleWizardViewModel = ViewModelProviders.of(activity!!).get(RuleWizardViewModel::class.java)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectAppsRecyclerView = view.findViewById(R.id.select_rule_recyclerview)
        selectedAppsAdapter = SelectRuleAdapter(ruleWizardViewModel.getSelectedRuleTypeList().value ?: ruleWizardViewModel.createDefaultRuleList(),this)
        selectAppsRecyclerView.layoutManager = LinearLayoutManager(context)
        selectAppsRecyclerView.hasFixedSize()
        selectAppsRecyclerView.adapter = selectedAppsAdapter

        ruleWizardViewModel.getSelectedRuleTypeList().observe(viewLifecycleOwner, Observer {
            selectedAppsAdapter.updateData(it)
        })

    }

    override fun onRadioButtonChecked(position: Int) {
        ruleWizardViewModel.setSelectedRuleTypeInList(Utils.uiPositionToRuleType(position))
    }
}

class RuleTypeListItem(val ruleType: RuleType, var selected: Boolean)

interface RadioClickListener {
    fun onRadioButtonChecked(position: Int)
}
