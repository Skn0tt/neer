package de.simonknott.neer

object Config {

    val REDIS_HOST = System.getenv("REDIS_HOST") ?: "localhost"

    val REDIS_PORT = Integer.parseInt(
        System.getenv("REDIS_PORT") ?: "6379"
    )

    enum class StorageImplementation {
        REDIS,
        IN_MEMORY
    }

    val STORAGE_IMPLEMENTATION = StorageImplementation.valueOf(System.getenv("STORAGE_IMPLEMENTATION") ?: "REDIS")

    val USER_ID_LENGTH = Integer.parseInt(System.getenv("USER_ID_LENGTH") ?: "4")

    enum class SMSTransport {
        TWILIO
    }
    val SMS_TRANSPORT = SMSTransport.valueOf((System.getenv("SMS_TRANSPORT") ?: "TWILIO").toUpperCase())

}