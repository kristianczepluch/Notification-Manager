package com.example.notificationmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.notificationmanager.R
import com.example.notificationmanager.adapter.Notification_entry
import com.example.notificationmanager.adapter.Notification_overview_adapter

class NotificationOverview : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewAdapter : Notification_overview_adapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup the recyclerview for the notification overview
        recyclerView = view.findViewById(R.id.notifications_overview_recyclerView)

        val exampleEntry1 = Notification_entry("WhatsApp", 1,1,1)
        val exampleEntry2 = Notification_entry("Facebook", 1,1,1)
        val exampleEntry3 = Notification_entry("Twitter", 1,1,1)


        recyclerViewAdapter = Notification_overview_adapter(listOf(exampleEntry1,exampleEntry2,exampleEntry3))

        recyclerView.adapter = recyclerViewAdapter

        recyclerView.layoutManager = LinearLayoutManager(context)


    }
}
