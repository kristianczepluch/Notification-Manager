package com.example.notificationmanager.adapter

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.util.forEach
import androidx.core.util.set
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.RuleType
import com.example.notificationmanager.data.DetoxRuleEntity
import com.example.notificationmanager.utils.NotificationManagerApplication
import com.example.notificationmanager.utils.Utils
import kotlinx.android.synthetic.main.rule_list_item.view.*

class RulesOverviewAdapter(
    val data: ArrayList<DetoxRuleEntity>,
    val listener: OnRulesItemListener,
    var actionMode: Boolean = false
) :
    RecyclerView.Adapter<RulesOverviewAdapter.Rules_overview_viewholder>() {

    var selectedSparseArray: SparseBooleanArray = SparseBooleanArray()


    interface OnRulesItemListener {
        fun onRulesItemClicked(position: Int, isSelected: Boolean, id: Int)
        fun onRulesItemLongClicked(position: Int, id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Rules_overview_viewholder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.rule_list_item, parent, false)
        return Rules_overview_viewholder(itemView)

    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: Rules_overview_viewholder, position: Int) {


        val currentItem = data[position]

        holder.checkbox.isChecked = selectedSparseArray.get(position)


        if (actionMode) {
            holder.checkbox.visibility = View.VISIBLE
        } else holder.checkbox.visibility = View.INVISIBLE

        holder.ruletype.text =
            Utils.ruleTypeToUiString(Utils.uiPositionToRuleType(currentItem.ruleType))
        holder.title.text = Utils.getAppNameFromPackageName(currentItem.packageName)
        holder.imgApp.setImageDrawable(Utils.getAppIconFromPackageName(currentItem.packageName))

        holder.card.setOnClickListener {
            val currentValue = holder.checkbox.isChecked
            holder.checkbox.isChecked = !currentValue
            listener.onRulesItemClicked(position, !currentValue, currentItem.id)
        }

        holder.card.setOnLongClickListener {
            val currentValue = holder.checkbox.isChecked
            holder.checkbox.isChecked = !currentValue
            listener.onRulesItemLongClicked(position, currentItem.id)
            true
        }



        when (Utils.uiPositionToRuleType(currentItem.ruleType)) {
            RuleType.ETERNALLY -> {
                val drawable =
                    NotificationManagerApplication.appContext.getDrawable(R.drawable.ic_not_interested_24px)!!
                DrawableCompat.setTint(
                    drawable,
                    NotificationManagerApplication.appContext.getColor(R.color.rule_forbid_eternally)
                )
                holder.imgRuleType.setImageDrawable(drawable)
                holder.ruletype.setTextColor(NotificationManagerApplication.appContext.getColor(R.color.rule_forbid_eternally))
            }
            RuleType.SHORT_BREAK -> {
                val drawable =
                    NotificationManagerApplication.appContext.getDrawable(R.drawable.ic_snooze_24px)!!
                DrawableCompat.setTint(
                    drawable,
                    NotificationManagerApplication.appContext.getColor(R.color.rule_short_break)
                )
                holder.imgRuleType.setImageDrawable(drawable)
                holder.ruletype.setTextColor(NotificationManagerApplication.appContext.getColor(R.color.rule_short_break))
            }
            RuleType.SCHEDULE -> {
                val drawable =
                    NotificationManagerApplication.appContext.getDrawable(R.drawable.ic_today_24px)!!
                DrawableCompat.setTint(
                    drawable,
                    NotificationManagerApplication.appContext.getColor(R.color.rule_schedule)
                )
                holder.imgRuleType.setImageDrawable(drawable)
                holder.ruletype.setTextColor(NotificationManagerApplication.appContext.getColor(R.color.rule_schedule))
            }
            RuleType.LIMIT_NUMBER -> {
                val drawable =
                    NotificationManagerApplication.appContext.getDrawable(R.drawable.ic_filter_9_plus_24px)!!
                DrawableCompat.setTint(
                    drawable,
                    NotificationManagerApplication.appContext.getColor(R.color.rule_limit_numner)
                )
                holder.imgRuleType.setImageDrawable(drawable)
                holder.ruletype.setTextColor(NotificationManagerApplication.appContext.getColor(R.color.rule_limit_numner))
            }
            else -> {
            }
        }

    }

    fun setItemSelected(position: Int, isSelected: Boolean) {
        selectedSparseArray.put(position, isSelected)
    }

    fun setData(dataArg: List<DetoxRuleEntity>) {
        data.clear()
        data.addAll(dataArg)
        notifyDataSetChanged()
    }

    fun setActionModeActivated(mode: Boolean) {
        actionMode = mode
        notifyDataSetChanged()
    }

    fun clearItemSelectedArray() {
        selectedSparseArray.forEach { i: Int, b: Boolean ->
            selectedSparseArray[i] = false
        }
    }

    class Rules_overview_viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title = itemView.rule_card_title
        val ruletype = itemView.rules_card_ruletype
        val imgRuleType = itemView.rule_card__imageView_ruletype
        val imgApp = itemView.rule_card__imageView_app
        val card = itemView.rules_item
        val checkbox = itemView.rules_card_checkbox
    }
}