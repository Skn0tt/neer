package friendlist

import UserID

interface FriendListRepo {

    fun getAll(forUser: UserID): Set<UserID>
    fun getAllWithDesireStatus(forUser: UserID): Set<Pair<UserID, Boolean>>
    fun getDesirable(forUser: UserID): Set<UserID>
    fun getMutuallyDesirable(forUser: UserID): Set<UserID>

    fun setDesirable(user: UserID, friend: UserID, value: Boolean)
    fun friend(a: UserID, b: UserID)

    /**
     * Unfriending is one-sided - the unfriended person shouldn't see it.
     */
    fun unfriend(user: UserID, pastFriend: UserID)

}