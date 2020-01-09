package de.simonknott.neer.users

import de.simonknott.neer.util.update

object UserRepositoryInMemoryImpl : UserRepository {

    private data class UserRecord(val phoneNumber: PhoneNumber?) {
        fun toUser(id: UserId) = User(id, phoneNumber)
    }

    private val users: MutableMap<UserId, UserRecord> = mutableMapOf()

    override suspend fun getUser(user: UserId): User? = users[user]?.toUser(user)

    override suspend fun createUser(user: User) {
        users[user.id] = UserRecord(user.phoneNumber)
    }

    override suspend fun deleteUser(user: UserId) = users.remove(user)?.toUser(user)

    override suspend fun setPhoneNumber(user: UserId, phoneNumber: PhoneNumber) {
        users.update(user, { it?.copy(phoneNumber = phoneNumber) })
    }

    override suspend fun unsetPhoneNumber(user: UserId) {
        users.update(user, { it?.copy(phoneNumber = null) })
    }

}