package com.example.notificationmanager.fragments


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.MainActivityViewModel
import com.example.notificationmanager.activities.RuleDetailActivity
import com.example.notificationmanager.activities.RuleWizardActivity
import com.example.notificationmanager.adapter.RulesOverviewAdapter
import com.example.notificationmanager.data.DetoxRuleEntity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class NotificationRules : Fragment(R.layout.fragment_notification_rules),
    ActionMode.Callback,
    RulesOverviewAdapter.OnRulesItemListener {

    lateinit var fab: FloatingActionButton

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewAdapter: RulesOverviewAdapter
    private var actionMode: ActionMode? = null
    private val model: MainActivityViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab = view.findViewById(R.id.rules_floating_action_button)
        recyclerView = view.findViewById(R.id.rules_recyclerView)
        recyclerViewAdapter = RulesOverviewAdapter(ArrayList(), this)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)


        // restore actionmode state after screenrotation
        if (model.actionMode) {
            startActionMode()
            recyclerViewAdapter.selectedSparseArray = model.selectedItems
            recyclerViewAdapter.notifyDataSetChanged()
        }

        // if the user navigates aways stop the action mode
        model.getCurrentPage().observe(viewLifecycleOwner, Observer{step ->
            if(step!=1 && model.actionMode){
                actionMode?.finish()
                recyclerViewAdapter.setActionModeActivated(false)
                model.actionMode = false
            }
        })

        fab.setOnClickListener {
            startActivity(Intent(context, RuleWizardActivity::class.java))
        }

        val myData: LiveData<List<DetoxRuleEntity>> = model.getRulesOverview()

        myData.observe(viewLifecycleOwner, Observer { newList ->
            recyclerViewAdapter.setData(newList)
        })
    }


    override fun onActionItemClicked(
        mode: ActionMode?,
        item: MenuItem?
    ): Boolean {
        return when (item?.itemId) {
            R.id.delete_option -> {
                model.deleteSelectedItems()
                mode?.finish()
                true
            }
            else -> {
                false
            }
        }
    }

    override fun onCreateActionMode(
        mode: ActionMode?,
        menu: Menu?
    ): Boolean {
        mode?.menuInflater?.inflate(R.menu.menu_cab, menu)
        mode?.title = context?.resources?.getString(R.string.choose_one_or_more)
        model.actionMode = true
        return true
    }

    override fun onPrepareActionMode(
        mode: ActionMode?,
        menu: Menu?
    ): Boolean {
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        actionMode = null
        model.actionMode = false
        recyclerViewAdapter.setActionModeActivated(false)
    }

    override fun onRulesItemClicked(position: Int, isSelected: Boolean, id: Int) {
        if (actionMode != null) {
            model.setItemSelect(position, isSelected, id)
            recyclerViewAdapter.setItemSelected(position, isSelected)
        } else{
            val detailsActivityIntent = Intent(activity, RuleDetailActivity::class.java)
            detailsActivityIntent.putExtra(RuleDetailActivity.RULE_ID_EXTRA, id)
            startActivity(detailsActivityIntent)
        }
    }

    override fun onRulesItemLongClicked(position: Int, id: Int) {
        if (actionMode == null) {
            startActionMode()
            recyclerViewAdapter.clearItemSelectedArray()
            onRulesItemClicked(position, true, id)
        } else {
            actionMode?.finish()
        }
    }

    private fun startActionMode() {
        (activity as AppCompatActivity).setSupportActionBar(null)
        actionMode = (activity as AppCompatActivity).startSupportActionMode(this)
        recyclerViewAdapter.setActionModeActivated(true)
    }


}
