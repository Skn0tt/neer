package de.simonknott.neer.util

class InMemoryKeyValueStorage private constructor(): KeyValueStorage {

    private val map = mutableMapOf<String, String>()

    override suspend fun set(key: String, value: String) {
        map[key] = value
    }

    override suspend fun get(key: String): String? = map[key]

    override suspend fun has(key: String): Boolean = map.containsKey(key)

    override suspend fun unset(key: String) {
        map.remove(key)
    }

    override suspend fun getKeys(): Set<String> = map.keys

    override suspend fun getValues(): Iterable<String> = map.values

    override suspend fun getEntries(): Iterable<Map.Entry<String, String>> = map.entries

    companion object: NamespacedDependency<InMemoryKeyValueStorage> by NamespacedProvider({ InMemoryKeyValueStorage() })

}