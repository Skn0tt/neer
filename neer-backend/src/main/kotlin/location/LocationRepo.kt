package location

import UserID
import Location

interface LocationRepo {
    fun update(user: UserID, location: Location)
    operator fun get(user: UserID): Location?
}