package pushnotification

import UserID
import Language

object TokenRepoInMemory : TokenRepo {

    private val tokens = mutableMapOf<UserID, Pair<Token, Language>>()

    override fun set(user: UserID, token: Pair<Token, Language>) {
        tokens[user] = token
    }

    override fun get(user: UserID): Pair<Token, Language>? {
        return tokens[user]
    }

    override fun contains(user: UserID) = tokens.containsKey(user)

}