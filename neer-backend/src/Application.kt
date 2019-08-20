package de.simonknott.neer

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.locations.*
import io.ktor.features.*
import io.ktor.auth.*
import io.ktor.jackson.*
import org.slf4j.event.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalLocationsAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

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

    }
}
