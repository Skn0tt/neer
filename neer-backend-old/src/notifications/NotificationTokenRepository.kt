package notifications

import de.simonknott.neer.users.UserId
import de.simonknott.neer.util.Dependency
import de.simonknott.neer.util.Provider

enum class NotificationPlatform { ANDROID, IOS }

data class NotificationToken(val token: String, val platform: NotificationPlatform)

interface NotificationTokenRepository {
    suspend fun setToken(user: UserId, token: NotificationToken)
    suspend fun getToken(user: UserId): NotificationToken?
    suspend fun unsetToken(user: UserId)

    companion object: Dependency<NotificationTokenRepository> by Provider({ NotificationTokenRepositoryInMemoryImpl })
}