package de.simonknott.neer.users

import de.simonknott.neer.auth.RefreshTokenPayload
import de.simonknott.neer.auth.RefreshTokenService
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.delete
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post

object UsersRoute {

    private val userRepository = UserRepository.get()
    private val refreshTokenService = RefreshTokenService.get()

    suspend fun deleteUser(userId: String, call: ApplicationCall) {
        val user = userRepository.deleteUser(userId)
        if (user != null) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    }

    suspend fun registerUser(phoneNumber: String?, call: ApplicationCall) {
        data class RegisterResponse(val userId: String, val refreshToken: String?)
        val newUserId = UserIdGenerator.next()

        if (phoneNumber != null) {
            val parsedNumber = PhoneNumber.fromString(phoneNumber)
            if (parsedNumber == null) {
                call.respond(HttpStatusCode.BadRequest, "Please provide a valid phone number.")
            } else {
                userRepository.createUser(User(newUserId, phoneNumber = parsedNumber))
                call.respond(HttpStatusCode.Accepted, RegisterResponse(userId = newUserId, refreshToken = null))
            }
        } else {
            userRepository.createUser(User(newUserId, phoneNumber = null))
            val refreshToken = refreshTokenService.issueToken(RefreshTokenPayload(newUserId))
            call.respond(HttpStatusCode.OK, RegisterResponse(userId = newUserId, refreshToken = refreshToken))
        }
    }

}

@KtorExperimentalLocationsAPI
fun Route.users() {

    @Location("/{userId}")
    data class DeleteUser(val userId: String)

    delete<DeleteUser> { location ->
        UsersRoute.deleteUser(location.userId, call)
    }


    post("/") {
        data class RegisterBody(val phoneNumber: String?)
        val body = call.receive<RegisterBody>()
        UsersRoute.registerUser(body.phoneNumber, call)
    }

}