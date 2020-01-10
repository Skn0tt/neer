package usedtokens

import UserID

object UsedTokensRepoInMemory : UsedTokensRepo {

    private val usedTokens = mutableSetOf<String>()

    override fun add(user: UserID, token: String) {
        usedTokens.add("$user:$token")
    }

    override fun contains(user: UserID, token: String): Boolean {
        return usedTokens.contains("$user:$token")
    }

}