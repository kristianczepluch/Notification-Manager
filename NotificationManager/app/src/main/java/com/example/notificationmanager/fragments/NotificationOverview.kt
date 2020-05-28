package com.example.notificationmanager.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.MainActivityViewModel
import com.example.notificationmanager.adapter.Notification_overview_adapter
import com.example.notificationmanager.data.NotificationListItem

class NotificationOverview : Fragment(R.layout.fragment_notification_overview) {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewAdapter : Notification_overview_adapter
    private val model: MainActivityViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup the recyclerview for the notification overview
        recyclerView = view.findViewById(R.id.notifications_overview_recyclerView)
        recyclerViewAdapter = Notification_overview_adapter(ArrayList())
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        val myData: LiveData<List<NotificationListItem>> = model.getNotificationOverview()
        myData.observe(viewLifecycleOwner,Observer { newList ->
            recyclerViewAdapter.setData(newList)
        })
    }
}
