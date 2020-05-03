package com.example.notificationmanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.fragments.ruleCreation.SelectApplicationsFragment
import com.example.notificationmanager.onAppSelectListener
import com.example.notificationmanager.utils.Utils

class SelectAppsAdapter (var data: List<SelectApplicationsFragment.SelectAppListItem>, val appSelectListener: onAppSelectListener) : RecyclerView.Adapter<SelectAppsAdapter.SelectedAppsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedAppsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.selected_apps_item, parent, false)
        return SelectedAppsViewHolder(itemView)
    }

    override fun onViewRecycled(holder: SelectedAppsViewHolder) {
        holder.checkbox.setOnCheckedChangeListener(null)
        super.onViewRecycled(holder)
    }

    override fun onBindViewHolder(holder: SelectedAppsViewHolder, position: Int) {


        val currentItem = data.get(position)

        holder.checkbox.isChecked = currentItem.selected

        holder.checkbox.setOnCheckedChangeListener(){ _: CompoundButton, selected: Boolean ->



            if(selected) {
                currentItem.selected = true
                appSelectListener.onApplicationSelected(currentItem.packageName)
            }
            else if(!selected){
                currentItem.selected = false
                appSelectListener.onApplicationUnselected(currentItem.packageName)
            }
        }

        holder.textView.text = Utils.getAppNameFromPackageName(currentItem.packageName)
        holder.imageView.setImageDrawable(Utils.getAppIconFromPackageName(currentItem.packageName))

    }

    fun updateData(newData: ArrayList<SelectApplicationsFragment.SelectAppListItem>){
        data = newData
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size


    class SelectedAppsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView = itemView.findViewById<TextView>(R.id.selected_apps_textview)
        val checkbox = itemView.findViewById<CheckBox>(R.id.selected_apps_checkBox)
        val imageView = itemView.findViewById<ImageView>(R.id.selected_apps_imageview)
    }
}