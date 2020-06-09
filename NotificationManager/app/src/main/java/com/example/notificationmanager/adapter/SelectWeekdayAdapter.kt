package com.example.notificationmanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.Weekdays
import com.example.notificationmanager.fragments.ruleCreation.OnWeekdaySelectedListener
import com.example.notificationmanager.utils.NotificationManagerApplication


class SelectWeekdayAdapter(val selectedArray: BooleanArray, val listener: OnWeekdaySelectedListener? = null, val readOnly: Boolean = false): RecyclerView.Adapter<SelectWeekdayAdapter.SelectedWeekViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedWeekViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.weekday_listitem, parent, false)
        return SelectedWeekViewHolder(itemView)
    }

    override fun onViewRecycled(holder: SelectedWeekViewHolder) {
        holder.checkbox.setOnCheckedChangeListener(null)
        super.onViewRecycled(holder)
    }

    override fun onBindViewHolder(holder: SelectedWeekViewHolder, position: Int) {
        holder.textView.text = NotificationManagerApplication.appContext.resources.getStringArray(R.array.weekdays)[position].toString()
        if(selectedArray[position]) holder.checkbox.isChecked = true

        if(!readOnly){
            holder.checkbox.setOnCheckedChangeListener{ _: CompoundButton, selected: Boolean ->
                if(selected) {
                    listener?.onWeekdaySelected(positionToWeekday(position), position)
                }
                else {
                    listener?.onWeekdayUnSelected(positionToWeekday(position), position)
                }
            }
        } else {
            holder.checkbox.focusable = View.NOT_FOCUSABLE
            holder.checkbox.isClickable = false
        }
    }

    override fun getItemCount(): Int = 7

    inner class SelectedWeekViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init {
            if(!readOnly) {
                itemView.setOnClickListener{
                    checkbox.isChecked = !checkbox.isChecked
                }
            }
        }
        val textView = itemView.findViewById<TextView>(R.id.select_weekday_textView)
        val checkbox = itemView.findViewById<CheckBox>(R.id.select_weekday_checkbox)
    }

    private fun positionToWeekday(position: Int) =
        when(position){
            0 -> Weekdays.MO
            1 -> Weekdays.TU
            2 -> Weekdays.WE
            3 -> Weekdays.TH
            4 -> Weekdays.FR
            5 -> Weekdays.SA
            6 -> Weekdays.SU
            else -> Weekdays.MO
        }

}
