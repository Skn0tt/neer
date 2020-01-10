package pushnotification

import UserID
import Language
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification

private val tokenRepo = TokenRepoInMemory

data class NotificationData(val title: String?, val body: String?, val payload: Map<String, String>?)

private fun NotificationData.toFBMessage(token: String): Message {
    return Message.builder().apply {
        setToken(token)

        setNotification(
            Notification.builder().apply {
                setTitle(title)
                setBody(body)
            }.build()
        )

        if (payload != null) {
            putAllData(payload)
        }
    }.build()
}

private fun dispatch(token: String, data: NotificationData) {
    val messaging = FirebaseMessaging.getInstance()
    messaging.send(data.toFBMessage(token))
}

fun sendNotification(user: UserID, payloadCreator: (lang: Language) -> NotificationData) {
    val (token, language) = tokenRepo[user] ?: return
    val payload = payloadCreator(language)
    dispatch(token, payload)
}