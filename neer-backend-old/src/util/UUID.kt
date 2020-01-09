package de.simonknott.neer.util

object UUID {
    fun v4(): String = java.util.UUID.randomUUID().toString()
}