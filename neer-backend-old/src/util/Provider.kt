package de.simonknott.neer.util

/**
 * @see https://blog.kotlin-academy.com/effective-java-in-kotlin-item-1-consider-static-factory-methods-instead-of-constructors-8d0d7b5814b2
 */

interface Dependency<T> {
    var mocked: T?
    fun get(): T
    fun lazyGet(): Lazy<T> = lazy { get() }
}

class Provider<T>(val init: () -> T): Dependency<T> {
    var original: T? = null
    override var mocked: T? = null

    override fun get(): T = mocked ?: original ?: init()
        .apply { original = this }
}