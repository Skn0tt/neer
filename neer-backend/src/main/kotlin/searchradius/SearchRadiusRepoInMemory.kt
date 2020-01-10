package searchradius

import UserID

object SearchRadiusRepoInMemory : SearchRadiusRepo {

    private val searchRadii = mutableMapOf<UserID, Meters>()

    override operator fun set(user: UserID, radiusInMeters: Meters) {
        searchRadii[user] = radiusInMeters
    }

    override fun getOrNull(user: UserID): Meters? {
        return searchRadii[user]
    }

}