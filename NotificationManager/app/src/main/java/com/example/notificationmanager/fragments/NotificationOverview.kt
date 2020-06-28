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
    private val ORDER_FILTER_STATE = "order_filter"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(savedInstanceState != null){
            val timeFilterState = savedInstanceState.getInt(TIME_FILTER_STATE)
            val orderFilterState = savedInstanceState.getInt(ORDER_FILTER_STATE)
            model.setCurrentTimeFilter(timeFilterState)
            model.setCurrentOrder(orderFilterState)
        }

        // Setup the recyclerview for the notification overview
        recyclerView = view.findViewById(R.id.notifications_overview_recyclerView)
        recyclerViewAdapter = NotificationOverviewAdapter(ArrayList(),requireContext())
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        setHasOptionsMenu(true)

        model.getCurrentTimeFilter().observe(viewLifecycleOwner, Observer{timefilter ->
            myData?.removeObservers(viewLifecycleOwner)
            myData = model.getCurrentOrder().value?.let { model.getNotifications(timefilter, it) }
            myData?.observe(viewLifecycleOwner, Observer {
                recyclerViewAdapter.setData(it, timefilter)
            })
        })

        model.getCurrentOrder().observe(viewLifecycleOwner, Observer { order ->
            myData?.removeObservers(viewLifecycleOwner)
            myData = model.getCurrentTimeFilter().value?.let { model.getNotifications(it, order) }
            myData?.observe(viewLifecycleOwner, Observer {
                model.getCurrentTimeFilter().value?.let { it1 ->
                    recyclerViewAdapter.setData(it,
                        it1
                    )
                }
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
        val currentOrder = model.getCurrentOrder().value
        if(currentTimeFilter != null){ outState.putInt(TIME_FILTER_STATE, currentTimeFilter)}
        if(currentOrder != null){ outState.putInt(ORDER_FILTER_STATE, currentOrder)}
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
            R.id.filter_selection_most -> {
                item.isChecked = true
                model.setCurrentOrder(0)
                true
            }
            R.id.filter_selection_name -> {
                item.isChecked = true
                model.setCurrentOrder(1)
                true
            }
            else -> false
        }
    }
}
