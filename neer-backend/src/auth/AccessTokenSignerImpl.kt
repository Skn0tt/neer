package de.simonknott.neer.auth

object AccessTokenSignerImpl : AccessTokenService {

    override suspend fun validateToken(token: String): TokenPayload? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun issueToken(payload: TokenPayload): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}