package com.example.notificationmanager

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat.getEnabledListenerPackages

class PermissionActivity : AppCompatActivity() {

    lateinit var goNextText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        checkPermission()

        goNextText = findViewById(R.id.settings_textView)

        goNextText.setOnClickListener() {
            val notificationSettingsIntent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            startActivity(notificationSettingsIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        checkPermission()
    }

    private fun checkPermission() {
        if (permissionsGranted()) {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }
    }

    private fun permissionsGranted() = getEnabledListenerPackages(this).contains(packageName)
}
