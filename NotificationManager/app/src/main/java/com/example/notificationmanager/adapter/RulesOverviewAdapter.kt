package com.example.notificationmanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.RuleType
import com.example.notificationmanager.data.DetoxRuleEntity
import com.example.notificationmanager.utils.NotificationManagerApplication
import com.example.notificationmanager.utils.Utils
import kotlinx.android.synthetic.main.rule_list_item.view.*

class RulesOverviewAdapter(val data: ArrayList<DetoxRuleEntity>) :


    RecyclerView.Adapter<RulesOverviewAdapter.Rules_overview_viewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Rules_overview_viewholder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rule_list_item, parent, false)
        return Rules_overview_viewholder(itemView)

    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: Rules_overview_viewholder, position: Int) {

        val currentItem = data[position]


        holder.ruletype.text = Utils.ruleTypeToUiString(Utils.uiPositionToRuleType(currentItem.ruleType))
        holder.title.text = Utils.getAppNameFromPackageName(currentItem.packageName)

        when(Utils.uiPositionToRuleType(currentItem.ruleType)){
            RuleType.ETERNALLY -> {
                val drawable = NotificationManagerApplication.appContext.getDrawable(R.drawable.ic_not_interested_24px)!!
                DrawableCompat.setTint(drawable, NotificationManagerApplication.appContext.getColor(R.color.rule_forbid_eternally))
                holder.img.setImageDrawable(drawable)
                holder.ruletype.setTextColor(NotificationManagerApplication.appContext.getColor(R.color.rule_forbid_eternally)) }
            RuleType.SHORT_BREAK -> {
                val drawable = NotificationManagerApplication.appContext.getDrawable(R.drawable.ic_snooze_24px)!!
                DrawableCompat.setTint(drawable, NotificationManagerApplication.appContext.getColor(R.color.rule_short_break))
                holder.img.setImageDrawable(drawable)
                holder.ruletype.setTextColor(NotificationManagerApplication.appContext.getColor(R.color.rule_short_break))}
            RuleType.SCHEDULE -> {
                val drawable = NotificationManagerApplication.appContext.getDrawable(R.drawable.ic_today_24px)!!
                DrawableCompat.setTint(drawable, NotificationManagerApplication.appContext.getColor(R.color.rule_schedule))
                holder.img.setImageDrawable(drawable)
                holder.ruletype.setTextColor(NotificationManagerApplication.appContext.getColor(R.color.rule_schedule))}
            RuleType.LIMIT_NUMBER -> {
                val drawable = NotificationManagerApplication.appContext.getDrawable(R.drawable.ic_filter_9_plus_24px)!!
                DrawableCompat.setTint(drawable, NotificationManagerApplication.appContext.getColor(R.color.rule_limit_numner))
                holder.img.setImageDrawable(drawable)
                holder.ruletype.setTextColor(NotificationManagerApplication.appContext.getColor(R.color.rule_limit_numner))
            }
            else -> { }
        }

    }

    fun setData(dataArg: List<DetoxRuleEntity>){
        data.clear()
        data.addAll(dataArg)
        notifyDataSetChanged()
    }


    class Rules_overview_viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title = itemView.rule_card_title
        val ruletype = itemView.rules_card_ruletype
        val img = itemView.rule_card_imageView
    }
}