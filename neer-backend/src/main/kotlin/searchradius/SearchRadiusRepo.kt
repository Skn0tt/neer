package searchradius

import UserID

typealias Meters = Int

interface SearchRadiusRepo {

    operator fun set(user: UserID, radiusInMeters: Meters)

    fun getOrNull(user: UserID): Meters?

    operator fun get(user: UserID) = getOrNull(user) ?: Config.defaultSearchRadius

}