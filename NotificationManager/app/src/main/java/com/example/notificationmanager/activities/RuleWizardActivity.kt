package com.example.notificationmanager.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.notificationmanager.R

class RuleWizardActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rule_wizzard)

        // Setup the viewPager
        viewPager = findViewById(R.id.main_viewpager)

        //Todo: Setup the viewPager pager and pages
    }
}
