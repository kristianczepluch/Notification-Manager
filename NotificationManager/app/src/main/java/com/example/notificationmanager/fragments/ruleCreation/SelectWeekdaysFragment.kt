package com.example.notificationmanager.fragments.ruleCreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.RuleWizardViewModel
import com.example.notificationmanager.ViewModels.Weekdays
import com.example.notificationmanager.adapter.SelectWeekdayAdapter
import kotlinx.android.synthetic.main.weekday_listitem.view.*

class SelectWeekdaysFragment : Fragment(R.layout.fragment_select_weekdays), OnWeekdaySelectedListener  {

    private val SAVED_STATE_BOOLEAN_ARRAY = "selected_weekdays_array"
    lateinit var recyclerView: RecyclerView
    lateinit var adapter : SelectWeekdayAdapter
    private lateinit var ruleWizardViewModel: RuleWizardViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ruleWizardViewModel = ViewModelProvider(requireActivity()).get(RuleWizardViewModel::class.java)
        return inflater.inflate(R.layout.fragment_select_weekdays, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.weekdays_recyclerview)
        adapter = SelectWeekdayAdapter(ruleWizardViewModel.selectedWeekdayCheckboxes,this)
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        if(savedInstanceState != null){
            val savedBooleanArrayWeekdays: BooleanArray = savedInstanceState.get(SAVED_STATE_BOOLEAN_ARRAY) as BooleanArray
            savedBooleanArrayWeekdays.forEachIndexed { index, b ->
                if(b){
                    ruleWizardViewModel.addWeekDay(uiPositionToWeekday(index) as Weekdays, index )
                    recyclerView.findViewHolderForAdapterPosition(index)?.itemView?.select_weekday_checkbox?.isChecked = true
                }
            }
        }
    }


    override fun onWeekdaySelected(weekday: Weekdays, position: Int) {
       ruleWizardViewModel.addWeekDay(weekday, position)
    }

    override fun onWeekdayUnSelected(weekday: Weekdays, position: Int) {
        ruleWizardViewModel.removeWeekDay(weekday, position)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBooleanArray(SAVED_STATE_BOOLEAN_ARRAY, ruleWizardViewModel.selectedWeekdayCheckboxes)
    }

    private fun uiPositionToWeekday(position: Int)  =
        when(position){
            0 -> Weekdays.MO
            1 -> Weekdays.TU
            2 -> Weekdays.WE
            3 -> Weekdays.TH
            4 -> Weekdays.FR
            5 -> Weekdays.SA
            6 -> Weekdays.SU
            else -> -1
        }

}
interface OnWeekdaySelectedListener {
    fun onWeekdaySelected(weekday: Weekdays, position: Int)
    fun onWeekdayUnSelected(weekday: Weekdays, position: Int)
}
