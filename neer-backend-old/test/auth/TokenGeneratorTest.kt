package de.simonknott.neer.auth

import org.junit.Test
import kotlin.test.assertEquals

class TokenGeneratorTest {

    @Test
    fun testLength() {
        val v = TokenGenerator.next(64)
        assertEquals(64, v.length)
    }

}