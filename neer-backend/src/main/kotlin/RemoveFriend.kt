import friendlist.FriendListRepoInMemory

private val friendListRepo = FriendListRepoInMemory

fun removeFriend(user: UserID, newFriend: UserID) {
    friendListRepo.unfriend(user, newFriend)
}