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
import com.example.notificationmanager.ViewModels.RuleWizardViewModel
import com.example.notificationmanager.adapter.SelectAppsAdapter
import com.example.notificationmanager.onAppSelectListener

class SelectApplicationsFragment : Fragment(R.layout.fragment_select_applications),
    onAppSelectListener {

    private val STATE_SELECTED_APPS = "state_selected_apps"
    private lateinit var selectAppsRecyclerView: RecyclerView
    private lateinit var ruleWizardViewModel: RuleWizardViewModel
    private lateinit var selectedAppsAdapter: SelectAppsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ruleWizardViewModel = ViewModelProvider(requireActivity()).get(RuleWizardViewModel::class.java)
        if(savedInstanceState != null){
            val selectedCheckBoxes = savedInstanceState.getBooleanArray(STATE_SELECTED_APPS)
            val restoredList = ruleWizardViewModel.createDefaultAppList()
            selectedCheckBoxes?.forEachIndexed { index, selected ->
                if(selected){
                    restoredList[index].selected = true
                }
            }
            ruleWizardViewModel.setSelectedApplications(restoredList)
            selectedAppsAdapter = SelectAppsAdapter(
                restoredList, this)
        } else{
            selectedAppsAdapter = SelectAppsAdapter(
                ruleWizardViewModel.getSelectedApplications().value ?: ruleWizardViewModel.createDefaultAppList(), this
            )
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectAppsRecyclerView = view.findViewById(R.id.select_apps_recyclerview)
        selectAppsRecyclerView.layoutManager = LinearLayoutManager(context)
        selectAppsRecyclerView.adapter = selectedAppsAdapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val selectedAppsItems = ruleWizardViewModel.getSelectedApplications().value
        val selectedCheckbox: BooleanArray? = selectedAppsItems?.size?.let { BooleanArray(it) }
        selectedAppsItems?.forEachIndexed{ index, it ->
            if(it.selected){
                selectedCheckbox?.set(index, true)
            } else selectedCheckbox?.set(index, false)
        }
        if(selectedCheckbox != null) outState.putBooleanArray(STATE_SELECTED_APPS, selectedCheckbox)
    }

    override fun onApplicationSelected(app: String) {
        ruleWizardViewModel.addAppToList(app)
    }

    override fun onApplicationUnselected(app: String) {
        ruleWizardViewModel.removeAppFromList(app)
    }

    class SelectAppListItem(val packageName: String, var selected: Boolean)

}
