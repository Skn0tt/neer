package recentmatches

import UserID

object RecentMatchesRepoInMemory : RecentMatchesRepo {

    private val recentMatches = mutableMapOf<UserID, Set<UserID>>()

    override fun set(user: UserID, matches: Set<UserID>) {
        recentMatches[user] = matches
    }

    override fun get(user: UserID): Set<UserID>? {
        return recentMatches[user]
    }

}