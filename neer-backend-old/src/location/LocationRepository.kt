package de.simonknott.neer.location

import de.simonknott.neer.users.UserId
import de.simonknott.neer.util.Dependency
import de.simonknott.neer.util.Provider

interface LocationRepository {

    suspend fun getLocationOfUser(userId: UserId): Location?
    suspend fun getLocationsOfUsers(userIds: Iterable<UserId>): Map<UserId, Location>
    suspend fun setLocationOfUser(userId: UserId, location: Location)
    suspend fun unsetLocationOfUser(userId: UserId)

    companion object: Dependency<LocationRepository> by Provider({ LocationRepositoryInMemoryImpl })

}