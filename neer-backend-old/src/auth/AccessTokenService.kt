package de.simonknott.neer.auth

import de.simonknott.neer.users.UserId
import de.simonknott.neer.util.Dependency
import de.simonknott.neer.util.Provider

data class TokenPayload(val user: UserId)

interface AccessTokenService {

    val expiry: String
        get() = TODO("Get from config")

    suspend fun validateToken(token: String): TokenPayload?
    suspend fun issueToken(payload: TokenPayload): String

    companion object: Dependency<AccessTokenService> by Provider({ AccessTokenSignerImpl })
}