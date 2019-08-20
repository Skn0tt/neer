package de.simonknott.neer.location

import de.simonknott.neer.contacts.ContactsRepository
import de.simonknott.neer.users.UserId
import notifications.MatchNotificationService

object MatchFinder {

    private val locationRepo = LocationRepository.get()
    private val contactsRepository = ContactsRepository.get()
    private val matchNotificationRepository = MatchNotificationService.get()
    private val searchRadiusRepository = SearchRadiusRepository.get()

    suspend fun updateMatchesFor(user: UserId, update: suspend (repo: LocationRepository) -> Unit) {
        val matchesBeforeUpdate = findMatchesFor(user)
        update(locationRepo)
        val matchesAfterUpdate = findMatchesFor(user)

        val newMatches = matchesAfterUpdate - matchesBeforeUpdate
        val staleMatches = matchesBeforeUpdate - matchesAfterUpdate

        newMatches.forEach { matchNotificationRepository.sendNotification(user to it) }
        staleMatches.forEach { matchNotificationRepository.revokeNotification(user to it) }
    }

    private suspend fun findMatchesFor(user: UserId): Set<UserId> {
        val ownLocation = locationRepo.getLocationOfUser(user) ?: return emptySet()

        val contactsThatWantToMeet = contactsRepository.getContactsThatMutuallyWantToMeet(user)
        if (contactsThatWantToMeet.isEmpty()) {
            return emptySet()
        }

        val contactsToLocation = locationRepo.getLocationsOfUsers(contactsThatWantToMeet)

        val contactsToDistance = contactsToLocation.mapValues { DistanceCalculator.calculateDistance(ownLocation, it.value) }

        val ownPreferredDistance = searchRadiusRepository.getSearchRadiusOfUser(user) ?: return emptySet()
        val contactsInOwnRadius = contactsToDistance.filter { it.value <= ownPreferredDistance }

        val contactsWitTheirRadiuses = searchRadiusRepository.getSearchRadiusOfUsers(contactsInOwnRadius.keys)
        val contactsInTheirRadius = contactsWitTheirRadiuses.filter {
            val userId = it.key
            val theirRadius = it.value
            val theirDistance = contactsInOwnRadius[userId]!!
            theirDistance <= theirRadius
        }

        return contactsInTheirRadius.keys
    }

}