package com.example.notificationmanager.fragments.ruleCreation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.RuleWizardViewModel
import com.example.notificationmanager.adapter.SelectAppsAdapter
import com.example.notificationmanager.onAppSelectListener

class SelectApplicationsFragment : Fragment(R.layout.fragment_select_applications),
    onAppSelectListener {

    private lateinit var selectAppsRecyclerView: RecyclerView
    private lateinit var selectedAppsAdapter: SelectAppsAdapter
    private val ruleWizardViewModel: RuleWizardViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectAppsRecyclerView = view.findViewById(R.id.select_apps_recyclerview)
        selectAppsRecyclerView.layoutManager = LinearLayoutManager(context)
        selectedAppsAdapter = SelectAppsAdapter(
            ruleWizardViewModel.getSelectedApplications().value ?: ruleWizardViewModel.createDefaultAppList(), this
        )
        selectAppsRecyclerView.adapter = selectedAppsAdapter

    }


    override fun onApplicationSelected(app: String) {
        ruleWizardViewModel.addAppToList(app)
    }

    override fun onApplicationUnselected(app: String) {
        ruleWizardViewModel.removeAppFromList(app)
    }

    class SelectAppListItem(val packageName: String, var selected: Boolean)

}
