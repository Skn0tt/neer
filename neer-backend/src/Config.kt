package de.simonknott.neer

object Config {

    val REDIS_HOST = System.getenv("REDIS_HOST") ?: throw IllegalArgumentException("Please specify REDIS_HOST")

    val REDIS_PORT = Integer.parseInt(
        System.getenv("REDIS_PORT") ?: "6379"
    )

    enum class StorageImplementation {
        REDIS,
        IN_MEMORY
    }

    val STORAGE_IMPLEMENTATION = StorageImplementation.valueOf(System.getenv("STORAGE_IMPLEMENTATION") ?: "REDIS")

}