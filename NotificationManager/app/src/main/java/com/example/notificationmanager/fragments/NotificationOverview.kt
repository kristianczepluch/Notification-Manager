package com.example.notificationmanager.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.MainActivityViewModel
import com.example.notificationmanager.adapter.NotificationOverviewAdapter
import com.example.notificationmanager.data.NotificationListItem

class NotificationOverview : Fragment(R.layout.fragment_notification_overview) {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewAdapter : NotificationOverviewAdapter
    var myData: LiveData<List<NotificationListItem>>? = null
    private val model: MainActivityViewModel by activityViewModels()

    // Instance State Keys
    private val TIME_FILTER_STATE = "time_filter"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(savedInstanceState != null){
            val timeFilterState = savedInstanceState.getInt(TIME_FILTER_STATE)
            model.setCurrentTimeFilter(timeFilterState)
        }

        // Setup the recyclerview for the notification overview
        recyclerView = view.findViewById(R.id.notifications_overview_recyclerView)
        recyclerViewAdapter = NotificationOverviewAdapter(ArrayList(),requireContext())
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        setHasOptionsMenu(true)

        model.getCurrentTimeFilter().observe(viewLifecycleOwner, Observer{timefilter ->
            myData?.removeObservers(viewLifecycleOwner)
            myData = model.getNotificationsWithTimefilter(timefilter)
            myData?.observe(viewLifecycleOwner, Observer {
                recyclerViewAdapter.setData(it, timefilter)
            })
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notification_overview_menu, menu)
        model.getCurrentTimeFilter().observe(viewLifecycleOwner, Observer{
            when(it){
                0 -> menu.findItem(R.id.time_selection_today).isChecked = true
                1 -> menu.findItem(R.id.time_selection_week).isChecked = true
                2 -> menu.findItem(R.id.time_selection_month).isChecked = true
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val currentTimeFilter = model.getCurrentTimeFilter().value
        if(currentTimeFilter != null){ outState.putInt(TIME_FILTER_STATE, currentTimeFilter)}
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.time_selection_today -> {
                item.isChecked = true
                model.setCurrentTimeFilter(0)
                true
            }
            R.id.time_selection_week -> {
                item.isChecked = true
                model.setCurrentTimeFilter(1)
                true
            }
            R.id.time_selection_month -> {
                item.isChecked = true
                model.setCurrentTimeFilter(2)
                true
            }
            else -> false
        }
    }
}
