import friendlist.FriendListRepo
import friendlist.FriendListRepoInMemory
import location.LocationRepo
import location.LocationRepoInMemory
import pushnotification.NotificationData
import pushnotification.sendNotification
import recentmatches.RecentMatchesRepoInMemory
import searchradius.SearchRadiusRepo
import searchradius.SearchRadiusRepoInMemory
import java.util.stream.Collectors

private val friendListRepo: FriendListRepo = FriendListRepoInMemory
private val searchRadiusRepo: SearchRadiusRepo = SearchRadiusRepoInMemory
private val locationRepo: LocationRepo = LocationRepoInMemory
private val recentMatchesRepo = RecentMatchesRepoInMemory

fun getNearbyFriends(user: UserID, location: Location): Set<UserID> {
    val desiredFriends = friendListRepo.getMutuallyDesirable(user)

    val ownSearchRadius = searchRadiusRepo[user]

    return desiredFriends.parallelStream()
        .filter { friend ->
            val friendLocation = locationRepo[friend] ?: return@filter false

            val distance = calculateDistance(location, friendLocation)
            if (distance > ownSearchRadius) {
                return@filter false
            }

            val friendSearchRadius = searchRadiusRepo[friend]
            distance <= friendSearchRadius
        }
        .collect(Collectors.toSet())
}

fun getNearbyFriends(user: UserID): Set<UserID> {
    val ownLocation = locationRepo[user] ?: return emptySet()
    return getNearbyFriends(user, ownLocation)
}

fun scanNearby(user: UserID, location: Location) {
    val recentMatches = recentMatchesRepo[user] ?: emptySet()
    val friendsInReach = getNearbyFriends(user, location)

    val friendsNotInReachAnymore = recentMatches - friendsInReach
    val newFriendsInReach = friendsInReach - recentMatches
    friendsNotInReachAnymore.forEach {
        val payload = mapOf(
            "action" to "friend_not_in_reach",
            "friend" to user
        )
        sendNotification(it) {
            NotificationData(null, null, payload)
        }
    }
    newFriendsInReach.forEach {
        val payload = mapOf(
            "action" to "friend_in_reach",
            "friend" to user
        )
        sendNotification(it) { lang ->
            when (lang) {
                Language.ENGLISH -> NotificationData("A friend is in proximity!", null, payload)
                Language.GERMAN -> NotificationData("Ein Freund ist in der Nähe!", null, payload)
            }
        }
    }

    recentMatchesRepo[user] = friendsInReach
}

fun scanNearby(user: UserID) {
    val ownLocation = locationRepo[user] ?: return
    return scanNearby(user, ownLocation)
}