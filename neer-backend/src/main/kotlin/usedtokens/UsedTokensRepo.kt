package usedtokens

import UserID

interface UsedTokensRepo {
    fun add(user: UserID, token: String)
    fun contains(user: UserID, token: String): Boolean
}