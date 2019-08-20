package notifications

import de.simonknott.neer.users.UserId
import de.simonknott.neer.util.Dependency
import de.simonknott.neer.util.Provider

interface MatchNotificationService {
    suspend fun sendNotification(users: Pair<UserId, UserId>)
    suspend fun revokeNotification(users: Pair<UserId, UserId>)

    companion object: Dependency<MatchNotificationService> by Provider({ MatchNotificationServiceImpl })
}