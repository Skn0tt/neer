package de.simonknott.neer.util

interface KeyValueStorage {
    suspend fun set(key: String, value: String)
    suspend fun get(key: String): String?
    suspend fun has(key: String): Boolean
    suspend fun unset(key: String)
    suspend fun getKeys(): Set<String>
    suspend fun getValues(): Iterable<String>
    suspend fun getEntries(): Iterable<Map.Entry<String, String>>
}