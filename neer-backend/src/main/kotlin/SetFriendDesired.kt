import friendlist.FriendListRepoInMemory

private val friendListRepo = FriendListRepoInMemory

fun setFriendDesired(user: UserID, friend: UserID, desired: Boolean) {
    friendListRepo.setDesirable(user, friend, desired)
}