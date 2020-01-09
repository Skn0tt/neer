package de.simonknott.neer.contacts

import de.simonknott.neer.users.UserId
import de.simonknott.neer.util.update

object ContactsRepositoryInMemoryImpl : ContactsRepository {

    private val contacts: MutableMap<UserId, Set<UserId>> = mutableMapOf()
    private val wantsToMeet: MutableMap<UserId, Set<UserId>> = mutableMapOf()

    override suspend fun getContactsOfUser(user: UserId) = contacts.getOrDefault(user, emptySet())

    override suspend fun addToContactsOf(user: UserId, contact: Contact) {
        contacts.update(user) { it?.plusElement(contact.userId) ?: setOf(contact.userId) }
    }

    override suspend fun removeFromContactsOf(user: UserId, contact: UserId) {
        this.contacts.update(user) { it?.minusElement(contact) ?: emptySet() }
        this.wantsToMeet.update(user) { it?.minusElement(contact) ?: emptySet() }
    }

    override suspend fun getContactsUserWantsToMeet(user: UserId): Set<UserId> = wantsToMeet.getOrDefault(user, emptySet())

    override suspend fun getContactsThatMutuallyWantToMeet(user: UserId): Set<UserId> {
        val contactsUserWantsToMeet = getContactsUserWantsToMeet(user)
        return contactsUserWantsToMeet.filter { wantsToMeet.getOrDefault(it, setOf()).contains(user) }.toSet()
    }


}