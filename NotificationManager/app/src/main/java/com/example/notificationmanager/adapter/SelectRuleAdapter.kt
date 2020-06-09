package com.example.notificationmanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.RuleType
import com.example.notificationmanager.fragments.ruleCreation.RadioClickListener
import com.example.notificationmanager.utils.NotificationManagerApplication
import com.example.notificationmanager.utils.Utils

class SelectRuleAdapter(var selectedRuleType: RuleType, val radioClickListener: RadioClickListener) : RecyclerView.Adapter<SelectRuleAdapter.SelectRuleAdapterViewHolder>() {

    val NUMBER_OF_AVAILABLE_RULES = 4

    var lastCheckedBox = Utils.ruleTypeToUIPosition(selectedRuleType)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectRuleAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.select_rule_item, parent, false)



        return SelectRuleAdapterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SelectRuleAdapterViewHolder, position: Int) {

        holder.titleTextView.text = NotificationManagerApplication.appContext.resources.getStringArray(R.array.rule_names)[position].toString()
        holder.descriptionTextView.text = NotificationManagerApplication.appContext.resources.getStringArray(R.array.rule_descriptions)[position].toString()

        if(Utils.ruleTypeToUIPosition(selectedRuleType) == position)holder.checkRadioButton.isChecked = true

        when(position){
            0 -> {
                val drawable = NotificationManagerApplication.appContext.getDrawable(R.drawable.ic_snooze_24px)!!
                DrawableCompat.setTint(drawable, NotificationManagerApplication.appContext.getColor(R.color.rule_short_break))
                holder.ruleImageView.setImageDrawable(drawable)
                holder.titleTextView.setTextColor(NotificationManagerApplication.appContext.getColor(R.color.rule_short_break))}
            1 -> {
                val drawable = NotificationManagerApplication.appContext.getDrawable(R.drawable.ic_today_24px)!!
                DrawableCompat.setTint(drawable, NotificationManagerApplication.appContext.getColor(R.color.rule_schedule))
                holder.ruleImageView.setImageDrawable(drawable)
                holder.titleTextView.setTextColor(NotificationManagerApplication.appContext.getColor(R.color.rule_schedule))}
            2 -> {
                val drawable = NotificationManagerApplication.appContext.getDrawable(R.drawable.ic_filter_9_plus_24px)!!
                DrawableCompat.setTint(drawable, NotificationManagerApplication.appContext.getColor(R.color.rule_limit_numner))
                holder.ruleImageView.setImageDrawable(drawable)
                holder.titleTextView.setTextColor(NotificationManagerApplication.appContext.getColor(R.color.rule_limit_numner))}
            3 -> {
                val drawable = NotificationManagerApplication.appContext.getDrawable(R.drawable.ic_not_interested_24px)!!
                DrawableCompat.setTint(drawable, NotificationManagerApplication.appContext.getColor(R.color.rule_forbid_eternally))
                holder.ruleImageView.setImageDrawable(drawable)
                holder.titleTextView.setTextColor(NotificationManagerApplication.appContext.getColor(R.color.rule_forbid_eternally))}
        }

        holder.checkRadioButton.setOnCheckedChangeListener{ _: CompoundButton, b: Boolean ->
            if(b) {
                radioClickListener.onRadioButtonChecked(position)
                radioClickListener.onRadioButtonUnchecked(lastCheckedBox)
                lastCheckedBox = position
            }
        }
    }

    override fun getItemCount() = NUMBER_OF_AVAILABLE_RULES

    class SelectRuleAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        init {
            itemView.setOnClickListener{
                if(!checkRadioButton.isChecked) checkRadioButton.isChecked = true
            }
        }

        val titleTextView = itemView.findViewById<TextView>(R.id.select_rule_title)
        val descriptionTextView = itemView.findViewById<TextView>(R.id.select_rule_description)
        val ruleImageView = itemView.findViewById<ImageView>(R.id.select_rule_imageView)
        val checkRadioButton = itemView.findViewById<RadioButton>(R.id.select_rule_radioButton)
    }
}