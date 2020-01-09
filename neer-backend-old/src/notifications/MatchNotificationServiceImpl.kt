package notifications

import de.simonknott.neer.users.UserId

object MatchNotificationServiceImpl : MatchNotificationService {

    private val notificationTokenRepository = NotificationTokenRepository.get()

    override suspend fun sendNotification(users: Pair<UserId, UserId>) {
        suspend fun createNotification(recipient: UserId, match: UserId) {
            val token = notificationTokenRepository.getToken(recipient) ?: throw IllegalStateException("Token of user '$recipient' needs to be available.")
        }

        val notifications = listOf(createNotification(users.first, users.second), createNotification(users.second, users.first))
        TODO("dispatch notifications")
        TODO("save notifications ids for revocation")
    }

    override suspend fun revokeNotification(users: Pair<UserId, UserId>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}