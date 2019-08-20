package de.simonknott.neer.contacts

import de.simonknott.neer.users.UserId
import de.simonknott.neer.util.Dependency
import de.simonknott.neer.util.Provider

data class Contact(val userId: UserId, val wantsToMeet: Boolean)

interface ContactsRepository {

    suspend fun getContactsOfUser(user: UserId): Set<UserId>
    suspend fun getContactsUserWantsToMeet(user: UserId): Set<UserId>
    suspend fun getContactsThatMutuallyWantToMeet(user: UserId): Set<UserId>
    suspend fun addToContactsOf(user: UserId, contact: Contact)
    suspend fun removeFromContactsOf(user: UserId, contact: UserId)

    companion object: Dependency<ContactsRepository> by Provider({ ContactsRepositoryInMemoryImpl })

}