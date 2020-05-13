package com.example.notificationmanager.fragments

import android.content.Intent
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
import com.example.notificationmanager.activities.RuleWizardActivity
import com.example.notificationmanager.adapter.RulesOverviewAdapter
import com.example.notificationmanager.data.DetoxRuleEntity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class NotificationRules : Fragment(R.layout.fragment_notification_rules) {

   lateinit var fab: FloatingActionButton

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewAdapter :RulesOverviewAdapter
    lateinit var model: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model = ViewModelProviders.of(activity!!).get(MainActivityViewModel::class.java)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab = view.findViewById(R.id.rules_floating_action_button)
        recyclerView = view.findViewById(R.id.rules_recyclerView)
        recyclerViewAdapter = RulesOverviewAdapter(ArrayList<DetoxRuleEntity>())
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        fab.setOnClickListener{
            startActivity(Intent(context, RuleWizardActivity::class.java))
        }

        val myData: LiveData<List<DetoxRuleEntity>> = model.getRulesOverview()

        myData.observe(this, Observer { newList ->
            recyclerViewAdapter.setData(newList)
        })
    }
}
