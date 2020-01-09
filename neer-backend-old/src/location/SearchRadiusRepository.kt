package de.simonknott.neer.location

import de.simonknott.neer.users.UserId
import de.simonknott.neer.util.Dependency
import de.simonknott.neer.util.Provider

interface SearchRadiusRepository {
    suspend fun getSearchRadiusOfUser(user: UserId): Double?
    suspend fun getSearchRadiusOfUsers(users: Iterable<UserId>): Map<UserId, Double>
    suspend fun setSearchRadiusForUser(user: UserId, radius: Double)
    suspend fun unsetSearchRadiusForUser(user: UserId)

    companion object: Dependency<SearchRadiusRepository> by Provider({ SearchRadiusRepositoryInMemoryImpl })
}