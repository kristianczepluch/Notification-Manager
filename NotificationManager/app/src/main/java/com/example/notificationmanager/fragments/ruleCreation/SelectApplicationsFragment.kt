package com.example.notificationmanager.fragments.ruleCreation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.RuleWizardViewModel
import com.example.notificationmanager.adapter.SelectAppsAdapter
import com.example.notificationmanager.onAppSelectListener
import com.example.notificationmanager.utils.Utils

class SelectApplicationsFragment : Fragment(R.layout.fragment_select_applications), onAppSelectListener {

    lateinit var selectAppsRecyclerView: RecyclerView
    lateinit var ruleWizardViewModel: RuleWizardViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        ruleWizardViewModel = ViewModelProviders.of(activity!!).get(RuleWizardViewModel::class.java)
        selectAppsRecyclerView = view.findViewById(R.id.select_apps_recyclerview)
        selectAppsRecyclerView.layoutManager = LinearLayoutManager(context)

        val selectedAppsAdapter = SelectAppsAdapter(createDefaultList(),this)
        selectAppsRecyclerView.adapter = selectedAppsAdapter


        ruleWizardViewModel.getSelectedApplications().observe(this, Observer { data ->
            if(!data.equals(selectedAppsAdapter.data)){
                selectedAppsAdapter.updateData(data)
            }
        })
    }


    override fun onApplicationSelected(app: String) {
        ruleWizardViewModel.addAppToList(app)
    }

    override fun onApplicationUnselected(app: String) {
        ruleWizardViewModel.removeAppFromList(app)
    }

    fun createDefaultList():ArrayList<SelectAppListItem> {
        val dataList = ArrayList<SelectApplicationsFragment.SelectAppListItem>()
        Utils.getAllInstalledApps().forEach{
            dataList.add(SelectApplicationsFragment.SelectAppListItem(it, false))
        }
        return dataList
    }

    class SelectAppListItem(val packageName: String, var selected: Boolean)

}
