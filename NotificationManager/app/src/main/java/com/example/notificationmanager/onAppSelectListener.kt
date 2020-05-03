package com.example.notificationmanager

interface onAppSelectListener {
    fun onApplicationSelected(app: String)
    fun onApplicationUnselected(app: String)
}