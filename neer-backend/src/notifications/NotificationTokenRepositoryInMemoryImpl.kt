package notifications

import de.simonknott.neer.users.UserId

object NotificationTokenRepositoryInMemoryImpl : NotificationTokenRepository {

    private val tokens: MutableMap<UserId, NotificationToken> = mutableMapOf()

    override suspend fun setToken(user: UserId, token: NotificationToken) {
        tokens[user] = token
    }

    override suspend fun getToken(user: UserId): NotificationToken? = tokens[user]

    override suspend fun unsetToken(user: UserId) {
        tokens.remove(user)
    }

}