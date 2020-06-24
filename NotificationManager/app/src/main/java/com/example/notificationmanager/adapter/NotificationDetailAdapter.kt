package com.example.notificationmanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.data.NotificationEntity
import com.example.notificationmanager.utils.Utils
import kotlinx.android.synthetic.main.notification_detail_list_item.view.*

class NotificationDetailAdapter(val packageName: String) : RecyclerView.Adapter<NotificationDetailAdapter.NotificationListViewHolder>(){

    var data: List<NotificationEntity> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationListViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notification_detail_list_item, parent, false)
        val icon = Utils.getAppIconFromPackageName(packageName)
        itemView.findViewById<ImageView>(R.id.notification_detail_image).setImageDrawable(icon)
        return NotificationListViewHolder(itemView)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: NotificationListViewHolder, position: Int) {

        val currentItem = data[position]

        holder.title.text = currentItem.title
        holder.description.text = currentItem.content
        val timestampString = Utils.millisTimeToString(currentItem.timestampNot)
        holder.time.text = timestampString
    }

    fun updateData(newData: List<NotificationEntity>){
        data = newData
        notifyDataSetChanged()
    }

    class NotificationListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title = itemView.notification_detail_titile
        val description = itemView.notification_detail_description
        val image = itemView.notification_detail_image
        val time = itemView.time_textView

    }
}