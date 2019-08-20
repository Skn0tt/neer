package de.simonknott.neer.util

typealias Namespace = String

interface NamespacedDependency<T> {
    val mocked: Map<Namespace, T>
    fun get(namespace: Namespace): T
    fun lazyGet(namespace: Namespace): Lazy<T> = lazy { get(namespace) }
}

class NamespacedProvider<T>(val init: (namespace: Namespace) -> T): NamespacedDependency<T> {
    private val instances = mutableMapOf<Namespace, T>()
    override val mocked = mutableMapOf<Namespace, T>()
    override fun get(namespace: Namespace) = mocked[namespace] ?: instances.getOrPut(namespace, { init(namespace) })
}