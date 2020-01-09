package de.simonknott.neer

import de.simonknott.neer.users.users
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.locations.*
import io.ktor.features.*
import io.ktor.auth.*
import io.ktor.jackson.*
import io.ktor.response.respondText
import org.slf4j.event.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalLocationsAPI
@Suppress("unused") // Referenced in application.conf
fun Application.module() {

    install(Locations)

    install(AutoHeadResponse)

    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(CallId)

    install(ConditionalHeaders)

    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }

    install(Authentication) {}

    install(ContentNegotiation) {
        jackson {}
    }

    routing {
        get("/") {
            call.respondText("Hello World")
        }

        route("/users") {
            users()
        }
    }
}
