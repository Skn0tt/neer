package credentials

import UserID

object PublicKeyRepoInMemory : PublicKeyRepo {

    private val keys = mutableMapOf<UserID, PublicKey>()

    override fun get(user: UserID): PublicKey? {
        return keys[user]
    }

    override fun set(user: UserID, value: PublicKey) {
        keys[user] = value
    }

    override fun remove(user: UserID) {
        keys.remove(user)
    }

    override fun contains(user: UserID): Boolean {
        return keys.containsKey(user)
    }

}