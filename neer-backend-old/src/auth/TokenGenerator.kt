package de.simonknott.neer.auth

import java.security.SecureRandom
import java.util.stream.Collectors

object TokenGenerator {

    private val r = SecureRandom.getInstanceStrong()!!
    private const val chars = "0123456789abcdefghijklmnopqrstuvwxyz-_ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    fun next(length: Long): String {
        return r.ints(length, 0, chars.length)
                .toArray()
                .map { chars[it] }
                .joinToString("")
    }

}