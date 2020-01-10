package recentmatches

import UserID

interface RecentMatchesRepo {
    operator fun set(user: UserID, matches: Set<UserID>)
    operator fun get(user: UserID): Set<UserID>?
}