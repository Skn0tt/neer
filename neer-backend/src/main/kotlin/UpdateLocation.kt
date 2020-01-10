import location.LocationRepo
import location.LocationRepoInMemory

private val locationRepo: LocationRepo = LocationRepoInMemory

fun updateLocation(user: UserID, newLocation: Location) {
    locationRepo.update(user, newLocation)
    scanNearby(user, newLocation)
}