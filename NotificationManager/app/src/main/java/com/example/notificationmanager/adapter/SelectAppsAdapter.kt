package com.example.notificationmanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.utils.NotificationManagerApplication
import com.example.notificationmanager.utils.Utils

class SelectAppsAdapter (var data: List<String>) : RecyclerView.Adapter<SelectAppsAdapter.SelectedAppsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedAppsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.selected_apps_item, parent, false)
        return SelectedAppsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SelectedAppsViewHolder, position: Int) {

        val currentItem = data[position]

        holder.checkbox.setOnCheckedChangeListener(){ compoundButton: CompoundButton, b: Boolean ->
            Toast.makeText(NotificationManagerApplication.appContext, "Clicked: " + currentItem, Toast.LENGTH_SHORT).show()
        }

        holder.textView.text = Utils.getAppNameFromPackageName(currentItem)
        holder.imageView.setImageDrawable(Utils.getAppIconFromPackageName(currentItem))

    }

    override fun getItemCount() = data.size

    class SelectedAppsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView = itemView.findViewById<TextView>(R.id.selected_apps_textview)
        val checkbox = itemView.findViewById<CheckBox>(R.id.selected_apps_checkBox)
        val imageView = itemView.findViewById<ImageView>(R.id.selected_apps_imageview)
    }
}