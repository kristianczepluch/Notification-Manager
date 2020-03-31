package com.example.notificationmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.MainActivityViewModel
import com.example.notificationmanager.adapter.Notification_overview_adapter
import com.example.notificationmanager.data.NotificationListItem

class NotificationOverview : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewAdapter : Notification_overview_adapter
    lateinit var model: MainActivityViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        model = ViewModelProviders.of(activity!!).get(MainActivityViewModel::class.java)
        return inflater.inflate(R.layout.fragment_notification_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup the recyclerview for the notification overview
        recyclerView = view.findViewById(R.id.notifications_overview_recyclerView)
        recyclerViewAdapter = Notification_overview_adapter(ArrayList<NotificationListItem>())
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        val myData: LiveData<List<NotificationListItem>> = model.getNotificationOverview()
        myData.observe(this,Observer { newList ->
            recyclerViewAdapter.setData(newList)
        })
    }
}
