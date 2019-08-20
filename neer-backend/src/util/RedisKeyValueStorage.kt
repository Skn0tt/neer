package de.simonknott.neer.util

import de.simonknott.neer.Config
import redis.clients.jedis.Jedis

class RedisKeyValueStorage(private val namespace: String): KeyValueStorage {

    private val jedis = Jedis(Config.REDIS_HOST, Config.REDIS_PORT)

    private val prefix = "$namespace:"

    private fun String.namespace() = prefix + this

    override suspend fun set(key: String, value: String) {
        jedis.set(key.namespace(), value)
    }

    override suspend fun get(key: String): String? = jedis.get(key.namespace())

    override suspend fun has(key: String): Boolean = jedis.exists(key.namespace())

    override suspend fun unset(key: String) {
        jedis.del(key.namespace())
    }

    override suspend fun getKeys(): Set<String> {
        val prefixedKeys = jedis.keys("*".namespace())
        val keysWithoutPrefix = prefixedKeys.map { it.removePrefix(prefix) }
        return keysWithoutPrefix.toSet()
    }

    override suspend fun getValues(): Iterable<String> {
        val keys = getKeys()
        return keys.mapNotNull { get(it) }
    }

    override suspend fun getEntries(): Iterable<Map.Entry<String, String>> {
        val keys = getKeys()
        return keys.mapNotNull { key ->
            get(key)?.let {
                object: Map.Entry<String, String> {
                    override val key = key
                    override val value = it
                }
            }
        }
    }

    companion object: NamespacedDependency<RedisKeyValueStorage> by NamespacedProvider({ RedisKeyValueStorage(it) })

}