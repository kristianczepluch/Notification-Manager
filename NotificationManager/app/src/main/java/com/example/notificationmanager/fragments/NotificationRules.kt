package com.example.notificationmanager.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.notificationmanager.R
import com.example.notificationmanager.activities.RuleWizardActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class NotificationRules : Fragment(R.layout.fragment_notification_rules) {

   lateinit var fab: FloatingActionButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab = view.findViewById(R.id.rules_floating_action_button)
        fab.setOnClickListener{
            startActivity(Intent(context, RuleWizardActivity::class.java))
        }
    }
}
