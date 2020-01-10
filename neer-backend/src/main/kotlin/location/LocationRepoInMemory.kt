package location

import UserID
import Location

object LocationRepoInMemory : LocationRepo {

    private val locations = mutableMapOf<UserID, Location>()

    override fun update(user: UserID, location: Location) {
        locations[user] = location
    }

    override fun get(user: UserID): Location? {
        return locations[user]
    }

}