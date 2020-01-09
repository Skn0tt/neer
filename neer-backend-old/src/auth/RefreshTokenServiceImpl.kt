package de.simonknott.neer.auth

import de.simonknott.neer.util.KeyValueStorage
import de.simonknott.neer.util.PersistantKeyValueStorageProvider
import de.simonknott.neer.util.toISO8601
import java.time.LocalDateTime

object RefreshTokenServiceImpl : RefreshTokenService {

    private object Serializer {
        private const val DELIMITER = ";"

        fun toString(it: RefreshTokenPayload) = listOf(it.userId).joinToString(DELIMITER)


        fun fromString(value: String): RefreshTokenPayload {
            val (userId) = value.split(DELIMITER)
            return RefreshTokenPayload(userId)
        }
    }

    private val kv: KeyValueStorage = PersistantKeyValueStorageProvider.get("refresh_token")

    override suspend fun validateToken(token: String): RefreshTokenPayload? {
        val payload = kv.get(token) ?: return null
        return Serializer.fromString(payload)
    }

    override suspend fun issueToken(payload: RefreshTokenPayload): String
        = TokenGenerator.next(64).also {
            kv.set(it, Serializer.toString(payload))
        }


}