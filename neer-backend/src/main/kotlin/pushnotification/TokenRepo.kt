package pushnotification

import UserID
import Language

typealias Token = String

interface TokenRepo {
    operator fun set(user: UserID, token: Pair<Token, Language>)
    operator fun get(user: UserID): Pair<Token, Language>?
    operator fun contains(user: UserID): Boolean
}