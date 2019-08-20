package de.simonknott.neer.location

import de.simonknott.neer.getMultiple
import de.simonknott.neer.users.UserId

object SearchRadiusRepositoryInMemoryImpl : SearchRadiusRepository {

    private val map: MutableMap<UserId, Double> = mutableMapOf()

    override suspend fun getSearchRadiusOfUsers(users: Iterable<UserId>) = map.getMultiple(users)

    override suspend fun getSearchRadiusOfUser(user: UserId) = map[user]

    override suspend fun setSearchRadiusForUser(user: UserId, radius: Double) {
        map[user] = radius
    }

    override suspend fun unsetSearchRadiusForUser(user: UserId) {
        map.remove(user)
    }

}