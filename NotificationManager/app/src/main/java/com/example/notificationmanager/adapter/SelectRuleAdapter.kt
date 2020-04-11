package com.example.notificationmanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.fragments.ruleCreation.RadioClickListener
import com.example.notificationmanager.utils.NotificationManagerApplication

class SelectRuleAdapter(val radioClickListener: RadioClickListener) : RecyclerView.Adapter<SelectRuleAdapter.SelectRuleAdapterViewHolder>() {

    // Fixed number of rules atm
    val NUMBER_OF_AVAILABLE_RULES = 4

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectRuleAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.select_rule_item, parent, false)
        return SelectRuleAdapterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SelectRuleAdapterViewHolder, position: Int) {

        holder.titleTextView.text = NotificationManagerApplication.appContext.resources.getStringArray(R.array.rule_names)[position].toString()
        holder.descriptionTextView.text = NotificationManagerApplication.appContext.resources.getStringArray(R.array.rule_descriptions)[position].toString()

        when(position){
            0 -> holder.ruleImageView.setImageResource(R.drawable.ic_snooze_24px)
            1 -> holder.ruleImageView.setImageResource(R.drawable.ic_today_24px)
            2 -> holder.ruleImageView.setImageResource(R.drawable.ic_filter_9_plus_24px)
            3 -> holder.ruleImageView.setImageResource(R.drawable.ic_not_interested_24px)
        }

        holder.checkRadioButton.setOnClickListener(){
            radioClickListener.checkRadioButton(position)
        }
    }


    override fun getItemCount() = NUMBER_OF_AVAILABLE_RULES

    class SelectRuleAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTextView = itemView.findViewById<TextView>(R.id.select_rule_title)
        val descriptionTextView = itemView.findViewById<TextView>(R.id.select_rule_description)
        val ruleImageView = itemView.findViewById<ImageView>(R.id.select_rule_imageView)
        val checkRadioButton = itemView.findViewById<RadioButton>(R.id.select_rule_radioButton)
    }
}