package com.example.notificationmanager.fragments.ruleCreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.RuleWizardViewModel
import com.example.notificationmanager.adapter.SelectAppsAdapter
import com.example.notificationmanager.onAppSelectListener

class SelectApplicationsFragment : Fragment(R.layout.fragment_select_applications),
    onAppSelectListener {

    private lateinit var selectAppsRecyclerView: RecyclerView
    private lateinit var ruleWizardViewModel: RuleWizardViewModel
    private lateinit var selectedAppsAdapter: SelectAppsAdapter


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
