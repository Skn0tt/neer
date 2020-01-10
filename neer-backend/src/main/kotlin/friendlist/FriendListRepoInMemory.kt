package friendlist

import UserID

object FriendListRepoInMemory : FriendListRepo {

    private val friendLists = mutableMapOf<UserID, MutableMap<UserID, Boolean>>()

    override fun getAll(forUser: UserID): Set<UserID> {
        val friendList = friendLists.getOrDefault(forUser, mutableMapOf())
        return friendList.keys
    }

    override fun getDesirable(forUser: UserID): Set<UserID> {
        val friendList = friendLists.getOrDefault(forUser, mutableMapOf())
        return friendList.filter { val isDesirable = it.value; isDesirable }
                .keys
    }

    override fun getMutuallyDesirable(forUser: UserID): Set<UserID> {
        return getDesirable(forUser)
                .filter { friendLists[it]!![forUser]!! }
                .toSet()
    }

    override fun setDesirable(user: UserID, friend: UserID, value: Boolean) {
        val friendList = friendLists.getOrDefault(user, mutableMapOf())
        friendList[friend] = value
    }

    override fun getAllWithDesireStatus(forUser: UserID): Set<Pair<UserID, Boolean>> {
        val friendList = friendLists.getOrDefault(forUser, mutableMapOf())
        return friendList.toList().toSet()
    }

    private fun addFriend(user: UserID, newFriend: UserID) {
        val friendList = friendLists.getOrPut(user, { mutableMapOf() })
        friendList[newFriend] = true
    }

    override fun friend(user: UserID, newFriend: UserID) {
        addFriend(user, newFriend)
        addFriend(newFriend, user)
    }

    override fun unfriend(a: UserID, b: UserID) {
        val friendList = friendLists.getOrPut(a, { mutableMapOf() })
        friendList.remove(b)
    }


}