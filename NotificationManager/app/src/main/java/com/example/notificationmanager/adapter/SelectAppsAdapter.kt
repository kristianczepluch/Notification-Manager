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
import com.example.notificationmanager.onAppSelectListener
import com.example.notificationmanager.utils.Utils

class SelectAppsAdapter (var data: List<String>, val appSelectListener: onAppSelectListener) : RecyclerView.Adapter<SelectAppsAdapter.SelectedAppsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedAppsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.selected_apps_item, parent, false)
        return SelectedAppsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SelectedAppsViewHolder, position: Int) {


        val currentPackage = data.get(position)

        holder.checkbox.setOnCheckedChangeListener(){ _: CompoundButton, selected: Boolean ->
            if(selected) appSelectListener.onApplicationSelected(currentPackage)
            else if(!selected) appSelectListener.onApplicationUnselected(currentPackage)
        }

        holder.textView.text = Utils.getAppNameFromPackageName(currentPackage)
        holder.imageView.setImageDrawable(Utils.getAppIconFromPackageName(currentPackage))

    }

    override fun getItemCount() = data.size

    class SelectedAppsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView = itemView.findViewById<TextView>(R.id.selected_apps_textview)
        val checkbox = itemView.findViewById<CheckBox>(R.id.selected_apps_checkBox)
        val imageView = itemView.findViewById<ImageView>(R.id.selected_apps_imageview)
    }
}