package de.simonknott.neer.location

import de.simonknott.neer.users.UserId

object LocationService {

    suspend fun updateMyLocation(requestingUser: UserId, location: Location) {
        MatchFinder.updateMatchesFor(requestingUser) { repo ->
            repo.setLocationOfUser(requestingUser, location)
        }
    }

}