package de.simonknott.neer.users

import de.simonknott.neer.Config
import de.simonknott.neer.util.UUID

object UserIdGenerator : Iterator<UserId> {

    private val length = Config.USER_ID_LENGTH

    private fun getRandomString() = UUID.v4().replace("-", "")

    override fun next() = getRandomString().substring(0 until length)

    override fun hasNext() = true

}