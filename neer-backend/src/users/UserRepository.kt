package de.simonknott.neer.users

import de.simonknott.neer.util.Dependency
import de.simonknott.neer.util.Provider

typealias UserId = String

data class User(val id: UserId, val phoneNumber: PhoneNumber?)

interface UserRepository {

    suspend fun getUser(user: UserId): User?

    suspend fun deleteUser(user: UserId)

    suspend fun setPhoneNumber(user: UserId, phoneNumber: PhoneNumber)

    suspend fun unsetPhoneNumber(user: UserId)

    companion object: Dependency<UserRepository> by Provider({ UserRepositoryInMemoryImpl })

}