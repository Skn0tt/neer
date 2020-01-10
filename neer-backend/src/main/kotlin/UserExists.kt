import pushnotification.TokenRepoInMemory

private val publicTokens = TokenRepoInMemory

fun userExists(user: UserID) = user in publicTokens
