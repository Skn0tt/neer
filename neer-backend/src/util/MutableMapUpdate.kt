package de.simonknott.neer.util

fun <K, V> MutableMap<K, V>.update(key: K, mapper: (v: V?) -> V?) {
    val old = this[key]
    val new = mapper(old) ?: return
    this[key] = new
}