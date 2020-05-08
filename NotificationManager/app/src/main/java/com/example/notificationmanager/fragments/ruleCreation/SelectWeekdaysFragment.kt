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
import com.example.notificationmanager.ViewModels.Weekdays
import com.example.notificationmanager.adapter.SelectWeekdayAdapter

class SelectWeekdaysFragment : Fragment(R.layout.fragment_select_weekdays), OnWeekdaySelectedListener  {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter : SelectWeekdayAdapter
    private lateinit var ruleWizardViewModel: RuleWizardViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ruleWizardViewModel = ViewModelProviders.of(activity!!).get(RuleWizardViewModel::class.java)
        return inflater.inflate(R.layout.fragment_select_weekdays, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.weekdays_recyclerview)
        adapter = SelectWeekdayAdapter(ruleWizardViewModel.selectedWeekdayCheckboxes,this)
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

    }

    override fun onWeekdaySelected(weekday: Weekdays, position: Int) {
       ruleWizardViewModel.addWeekDay(weekday, position)
    }

    override fun onWeekdayUnSelected(weekday: Weekdays, position: Int) {
        ruleWizardViewModel.removeWeekDay(weekday, position)
    }

}
interface OnWeekdaySelectedListener {
    fun onWeekdaySelected(weekday: Weekdays, position: Int)
    fun onWeekdayUnSelected(weekday: Weekdays, position: Int)
}
