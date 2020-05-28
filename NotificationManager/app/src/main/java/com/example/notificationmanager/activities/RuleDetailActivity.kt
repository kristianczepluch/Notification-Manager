package com.example.notificationmanager.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.Observer
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.DetailActivityViewModel
import com.example.notificationmanager.ViewModels.RuleType
import com.example.notificationmanager.utils.Utils

class RuleDetailActivity : AppCompatActivity() {

    private lateinit var mainToolbar: Toolbar
    private lateinit var ruletypeTitle: TextView
    private lateinit var ruletypeDescription: TextView
    private lateinit var ruletypeImageView: ImageView
    private val detailActivityViewModel: DetailActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rule_detail)

        // Setup the toolbar with backbutton
        mainToolbar = findViewById(R.id.ruleDetails_toolbar)
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true);

        // reference views
        ruletypeTitle = findViewById(R.id.rule_details_title)
        ruletypeDescription = findViewById(R.id.rule_details_description)
        ruletypeImageView = findViewById(R.id.rule_details_imageView)


        // Get ruleId and request data
        val ruleId = intent.getIntExtra(RULE_ID_EXTRA, RULE_ID_DEFAULT_VALUE)

        detailActivityViewModel.getDetoxRuleEntity(ruleId).observe(this, Observer { rule ->
            when (Utils.uiPositionToRuleType(rule.ruleType)) {

                RuleType.SHORT_BREAK -> {
                    ruletypeTitle.text =
                        resources.getStringArray(R.array.rule_names)[rule.ruleType].toString()
                    ruletypeDescription.text =
                        resources.getStringArray(R.array.rule_descriptions)[rule.ruleType].toString()

                    val drawable = getDrawable(R.drawable.ic_snooze_24px)!!
                    DrawableCompat.setTint(
                        drawable,
                        getColor(R.color.rule_short_break)
                    )
                    ruletypeImageView.setImageDrawable(drawable)
                }

                RuleType.SCHEDULE -> {
                    ruletypeTitle.text =
                        resources.getStringArray(R.array.rule_names)[rule.ruleType].toString()
                    ruletypeDescription.text =
                        resources.getStringArray(R.array.rule_descriptions)[rule.ruleType].toString()

                    val drawable = getDrawable(R.drawable.ic_today_24px)!!
                    DrawableCompat.setTint(
                        drawable,
                        getColor(R.color.rule_schedule)
                    )
                    ruletypeImageView.setImageDrawable(drawable)
                }

                RuleType.LIMIT_NUMBER -> {
                    ruletypeTitle.text =
                        resources.getStringArray(R.array.rule_names)[rule.ruleType].toString()
                    ruletypeDescription.text =
                        resources.getStringArray(R.array.rule_descriptions)[rule.ruleType].toString()


                    val drawable = getDrawable(R.drawable.ic_filter_9_plus_24px)!!
                    DrawableCompat.setTint(
                        drawable,
                        getColor(R.color.rule_limit_numner)
                    )
                    ruletypeImageView.setImageDrawable(drawable)
                }

                RuleType.ETERNALLY -> {
                    ruletypeTitle.text =
                        resources.getStringArray(R.array.rule_names)[rule.ruleType].toString()
                    ruletypeDescription.text =
                        resources.getStringArray(R.array.rule_descriptions)[rule.ruleType].toString()


                    val drawable = getDrawable(R.drawable.ic_not_interested_24px)!!
                    DrawableCompat.setTint(
                        drawable,
                        getColor(R.color.rule_forbid_eternally)
                    )
                    ruletypeImageView.setImageDrawable(drawable)
                }

            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        val RULE_ID_EXTRA = "rule_id"
        val RULE_ID_DEFAULT_VALUE = -1
    }


}
