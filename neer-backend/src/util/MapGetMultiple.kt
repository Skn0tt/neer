package de.simonknott.neer

public fun <K, V> Map<K, V>.getMultiple(keys: Iterable<K>): Map<K, V> = keys.flatMap {
    val value = this.get(it)
    if (value != null) listOf(it to value) else emptyList()
}.toMap()