import searchradius.Meters
import searchradius.SearchRadiusRepoInMemory

private val searchRadiusRepo = SearchRadiusRepoInMemory

fun updateSearchRadius(user: UserID, radius: Meters) {
    searchRadiusRepo[user] = radius
    scanNearby(user)
}