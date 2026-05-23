package com.reconix.utils

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class ReconixMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        // Handle incoming FCM messages
        message.notification?.let {
            showNotification(it.title ?: "Reconix", it.body ?: "")
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Send token to server
    }

    private fun showNotification(title: String, body: String) {
        // Notification display handled via NotificationManager
    }
}
