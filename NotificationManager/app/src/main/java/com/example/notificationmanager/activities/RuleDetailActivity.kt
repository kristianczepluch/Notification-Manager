package com.example.notificationmanager.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.Observer
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.DetailActivityViewModel
import com.example.notificationmanager.ViewModels.LimitNumberMode
import com.example.notificationmanager.ViewModels.RuleType
import com.example.notificationmanager.utils.Utils

class RuleDetailActivity : AppCompatActivity() {

    private lateinit var mainToolbar: Toolbar
    private lateinit var ruletypeTitle: TextView
    private lateinit var ruletypeDescription: TextView
    private lateinit var ruletypeImageView: ImageView
    private lateinit var applicationsName: TextView
    private lateinit var applicationsImage: ImageView
    private lateinit var numberPicker: NumberPicker
    private lateinit var radioButtonDay: RadioButton
    private lateinit var radioButtonHour: RadioButton
    private lateinit var radioButtonWeek: RadioButton
    private lateinit var perText: TextView
    private lateinit var configurationsTextView: TextView
    private lateinit var configurationsLine: View

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
        applicationsName = findViewById(R.id.rule_details_app_name)
        applicationsImage = findViewById(R.id.rule_details_app_imageView)

        numberPicker = findViewById(R.id.rule_details_allowed_number)
        radioButtonDay = findViewById(R.id.day_radioButton)
        radioButtonHour = findViewById(R.id.hour_radioButton)
        radioButtonWeek = findViewById(R.id.week_radioButton)
        perText = findViewById(R.id.per_text_view)
        configurationsTextView = findViewById(R.id.rule_details_settings)
        configurationsLine = findViewById(R.id.line03)

        setLimitNumberConfigurationsVisibile(false)


        // Get ruleId and request data
        val ruleId = intent.getIntExtra(RULE_ID_EXTRA, RULE_ID_DEFAULT_VALUE)

        detailActivityViewModel.getDetoxRuleEntity(ruleId).observe(this, Observer { rule ->

            applicationsName.text = rule.appName
            applicationsImage.setImageDrawable(Utils.getAppIconFromPackageName(rule.packageName))

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
                    setLimitNumberConfigurationsVisibile(true)
                    val limitnumber = rule.ruleLimitNumberAllowedNumber
                    numberPicker.maxValue = limitnumber
                    numberPicker.minValue = limitnumber
                    numberPicker.value = limitnumber

                    val mode = Utils.timeSlotTypeToLimitNumberMode(rule.ruleLimitNumberTimeSlotType)

                    when(mode){
                        LimitNumberMode.HOUR -> {
                            radioButtonHour.isChecked = true
                        }
                        LimitNumberMode.WEEK -> {
                            radioButtonWeek.isChecked = true
                        }
                        LimitNumberMode.DAY -> {
                            radioButtonDay.isChecked = true
                        }
                        else -> throw Exception("Timeslot Mode not found")
                    }

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
                    removeConfigurationsSection()
                }

            }
        })

    }

    private fun setLimitNumberConfigurationsVisibile(visible: Boolean){
        if(!visible) {
            numberPicker.visibility = View.GONE
            radioButtonDay.visibility = View.GONE
            radioButtonHour.visibility = View.GONE
            radioButtonWeek.visibility = View.GONE
            perText.visibility = View.GONE
        }else{
            numberPicker.visibility = View.VISIBLE
            radioButtonDay.visibility = View.VISIBLE
            radioButtonHour.visibility = View.VISIBLE
            radioButtonWeek.visibility = View.VISIBLE
            perText.visibility = View.VISIBLE

            radioButtonHour.isClickable = false
            radioButtonHour.isFocusable = false
            radioButtonWeek.isClickable = false
            radioButtonWeek.isFocusable = false
            radioButtonDay.isFocusable = false
            radioButtonDay.isClickable = false

            numberPicker.isFocusable = false
            numberPicker.isClickable = false
            numberPicker.isActivated = false
        }
    }

    private fun removeConfigurationsSection(){
        configurationsLine.visibility = View.GONE
        configurationsTextView.visibility = View.GONE
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
