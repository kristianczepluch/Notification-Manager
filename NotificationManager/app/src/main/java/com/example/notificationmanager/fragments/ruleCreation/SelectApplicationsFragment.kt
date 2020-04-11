package com.example.notificationmanager.fragments.ruleCreation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.adapter.SelectAppsAdapter
import com.example.notificationmanager.utils.Utils

class SelectApplicationsFragment : Fragment(R.layout.fragment_select_applications) {

    lateinit var selectAppsRecyclerView: RecyclerView
    lateinit var selectedAppsAdapter: SelectAppsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectAppsRecyclerView = view.findViewById(R.id.select_apps_recyclerview)
        selectedAppsAdapter = SelectAppsAdapter(Utils.getAllInstalledApps())
        selectAppsRecyclerView.layoutManager = LinearLayoutManager(context)
        selectAppsRecyclerView.adapter = selectedAppsAdapter

    }
}
