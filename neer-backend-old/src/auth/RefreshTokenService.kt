package de.simonknott.neer.auth

import de.simonknott.neer.users.UserId
import de.simonknott.neer.util.Dependency
import de.simonknott.neer.util.Provider
import java.time.LocalDateTime

data class RefreshTokenPayload(val userId: UserId)

interface RefreshTokenService {

    suspend fun validateToken(token: String): RefreshTokenPayload?
    suspend fun issueToken(payload: RefreshTokenPayload): String

    companion object: Dependency<RefreshTokenService> by Provider({ RefreshTokenServiceImpl })

}