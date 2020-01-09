package de.simonknott.neer.location

import de.simonknott.neer.getMultiple
import de.simonknott.neer.users.UserId

object LocationRepositoryInMemoryImpl : LocationRepository {

    private val map: MutableMap<UserId, Location> = mutableMapOf()

    override suspend fun getLocationOfUser(userId: UserId) = map[userId]

    override suspend fun getLocationsOfUsers(userIds: Iterable<UserId>) = map.getMultiple(userIds)

    override suspend fun setLocationOfUser(userId: UserId, location: Location) {
        map[userId] = location
    }

    override suspend fun unsetLocationOfUser(userId: UserId) {
        map.remove(userId)
    }

}