
package com.example.notificationmanager.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.NotificationListViewModel
import com.example.notificationmanager.adapter.NotificationDetailAdapter

class NotificationListActivity : AppCompatActivity() {


    lateinit var notificationRecyclerview: RecyclerView
    lateinit var notificaionDetailsAdapter: NotificationDetailAdapter
    lateinit var mainToolbar: Toolbar
    private val notificationListViewModel: NotificationListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_list)

        // Setup the toolbar
        mainToolbar = findViewById(R.id.notifications_list_toolbar)
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val packageName = intent.getStringExtra(INTENT_EXTRA_PACKAGENAME)
        val timestamp = intent.getLongExtra(INTENT_EXTRA_TIMESTAMP, (-1).toLong())
        if(timestamp == (-1).toLong()) finish()

        notificationRecyclerview = findViewById(R.id.notification_list_recyclerview)
        notificationRecyclerview.layoutManager = LinearLayoutManager(this)

        if(packageName != null){
            notificaionDetailsAdapter = NotificationDetailAdapter(packageName)
            notificationRecyclerview.adapter = notificaionDetailsAdapter

            notificationListViewModel.getNotificationsFromAppAndTimestamp(packageName, timestamp).observe(this, Observer { list ->
                notificaionDetailsAdapter.updateData(list)
            })
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object{
        @JvmStatic
        val INTENT_EXTRA_PACKAGENAME = "intent_extra_packagename"
        val INTENT_EXTRA_TIMESTAMP = "intent_extra_timestamp"
    }
}
