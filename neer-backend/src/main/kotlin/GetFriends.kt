import friendlist.FriendListRepoInMemory

private val friendListRepo = FriendListRepoInMemory

fun getFriendList(user: UserID) = friendListRepo.getAllWithDesireStatus(user)