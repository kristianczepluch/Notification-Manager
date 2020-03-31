package com.example.notificationmanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.data.NotificationListItem
import com.example.notificationmanager.utils.Utils
import com.example.notificationmanager.utils.Utils.getStaticStringRessourceWithInt
import kotlinx.android.synthetic.main.notification_overview_item.view.*

class Notification_overview_adapter(val data: ArrayList<NotificationListItem>) :


    RecyclerView.Adapter<Notification_overview_adapter.Notification_overview_viewholder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Notification_overview_viewholder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notification_overview_item, parent, false)
        return Notification_overview_viewholder(itemView)

    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: Notification_overview_viewholder, position: Int) {

        val currentItem = data[position]

        holder.title.text = Utils.getAppNameFromPackageName(currentItem.packageName)
        holder.average.visibility = View.GONE
        holder.today.text = getStaticStringRessourceWithInt(R.string.app_notifications_received_adapter,currentItem.COUNT)
        holder.img.setImageDrawable(Utils.getAppIconFromPackageName(currentItem.packageName))

    }

    fun setData(dataArg: List<NotificationListItem>){
        data.clear()
        data.addAll(dataArg)
        notifyDataSetChanged()
    }


    class Notification_overview_viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title = itemView.notification_card_title
        val average = itemView.notification_card_average
        val today = itemView.notification_card_received_today
        val img = itemView.notification_overview_image

    }
}