import spark.Request
import spark.Spark.*

const val bearerPrefix = "Bearer "
private fun authenticate(request: Request): UserID? {
    val authorization = request.headers("Authorization") ?: return null
    if (!authorization.startsWith(bearerPrefix)) return null
    val token = authorization.removePrefix(bearerPrefix)
    return authenticateUser(token)
}

fun registerHttp() {
    port(3000)

    get("/status") { _, _ ->
        "OK"
    }

    post("/createAccount") { request, _ ->
        val (username, token, lang) = request.body().split(":", limit=3)
        val privateKey = createAccount(username, token, Language.valueOf(lang))
        privateKey.toBase64()
    }

    post("/migrateAccount") { request, _ ->
        val migrationCode = request.body()
        val newPrivateKey = migrateAccount(migrationCode)
        newPrivateKey
    }

    post("/location") { request, response ->
        val userId = authenticate(request) ?: return@post response.status(401)

        val ( latS, lonS ) = request.body().split(":")
        val newLocation = Location(latS.toDouble(), lonS.toDouble())
        updateLocation(userId, newLocation)
    }

    get("/friends") { request, response ->
        val userID = authenticate(request) ?: return@get response.status(401)

        getFriendList(userID)
    }

    get("/friends/nearby") { request, response ->
        val userID = authenticate(request) ?: return@get response.status(401)

        getNearbyFriends(userID)
    }

    post("/friends/:friendID") { request, response ->
        val userID = authenticate(request) ?: return@post response.status(401)

        val friendID = request.params("friendID")
        if (!userExists(friendID)) return@post response.status(404)

        addFriend(userID, friendID)
    }

    delete("/friends/:friendID") { request, response ->
        val userID = authenticate(request) ?: return@delete response.status(401)

        val friendID = request.params("friendID")
        if (!userExists(friendID)) return@delete response.status(404)

        removeFriend(userID, friendID)
    }

    put("/friends/:friendID/desired") { request, response ->
        val userID = authenticate(request) ?: return@put response.status(401)

        val friendID = request.params("friendID")
        if (!userExists(friendID)) return@put response.status(404)

        setFriendDesired(userID, friendID, true)
    }

    put("/friends/:friendID/undesired") { request, response ->
        val userID = authenticate(request) ?: return@put response.status(401)

        val friendID = request.params("friendID")
        if (!userExists(friendID)) return@put response.status(404)

        setFriendDesired(userID, friendID, false)
    }

    put("/searchradius") { request, response ->
        val userID = authenticate(request) ?: return@put response.status(401)

        val radius = request.body()

        updateSearchRadius(userID, radius.toInt())
    }


}