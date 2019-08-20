package de.simonknott.neer.util

import de.simonknott.neer.Config

object PersistantKeyValueStorageProvider {

    fun get(namespace: String): KeyValueStorage = when (Config.STORAGE_IMPLEMENTATION) {
        Config.StorageImplementation.IN_MEMORY -> InMemoryKeyValueStorage.get(namespace)
        Config.StorageImplementation.REDIS -> RedisKeyValueStorage.get(namespace)
    }

}