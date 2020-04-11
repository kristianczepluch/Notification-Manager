package com.example.notificationmanager.fragments.ruleCreation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.adapter.SelectRuleAdapter
import kotlinx.android.synthetic.main.select_rule_item.view.*

class SelectRuleTypeFragment : Fragment(R.layout.fragment_select_rule_type), RadioClickListener {

    lateinit var selectAppsRecyclerView: RecyclerView
    lateinit var selectedAppsAdapter: SelectRuleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectAppsRecyclerView = view.findViewById(R.id.select_rule_recyclerview)
        selectedAppsAdapter = SelectRuleAdapter(this)
        selectAppsRecyclerView.layoutManager = LinearLayoutManager(context)
        selectAppsRecyclerView.adapter = selectedAppsAdapter
    }

    override fun checkRadioButton(position: Int){
        for(i in 0..3){
            val myView = selectAppsRecyclerView.findViewHolderForAdapterPosition(i)
            if(myView!= null) myView.itemView.select_rule_radioButton.isChecked = false
        }

        val targetView = selectAppsRecyclerView.findViewHolderForAdapterPosition(position)
        targetView?.itemView?.select_rule_radioButton?.isChecked = true

    }
}

interface RadioClickListener {
    fun checkRadioButton(position: Int)
}
