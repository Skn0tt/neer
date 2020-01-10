import friendlist.FriendListRepoInMemory
import pushnotification.NotificationData
import pushnotification.sendNotification

private val friendListRepo = FriendListRepoInMemory

fun addFriend(user: UserID, newFriend: UserID) {
    friendListRepo.friend(user, newFriend)
    sendNotification(newFriend) {
        val payload = mapOf(
            "action" to "new_friend",
            "friend" to user
        )
        when (it) {
            Language.ENGLISH -> NotificationData("You've got a new friend!", null, payload)
            Language.GERMAN -> NotificationData("Du hast einen neuen Freund!", null, payload)
        }
    }
}