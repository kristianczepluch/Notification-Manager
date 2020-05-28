package com.example.notificationmanager.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.notificationmanager.R

class RuleDetailActivity : AppCompatActivity() {

    private lateinit var mainToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rule_detail)

        // Setup the toolbar with backbutton
        mainToolbar = findViewById(R.id.ruleDetails_toolbar)
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true);

        // Get RuleId to request details about the rule
        val ruleId = intent.getIntExtra(RULE_ID_EXTRA, RULE_ID_DEFAULT_VALUE)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object{
        val RULE_ID_EXTRA = "rule_id"
        val RULE_ID_DEFAULT_VALUE = -1
    }


}
