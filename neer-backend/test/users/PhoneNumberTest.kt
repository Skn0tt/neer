package de.simonknott.neer.users

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class PhoneNumberTest {

    @Test()
    fun testValidNumber() {
        val pn = PhoneNumber.fromString("+49 1573 1451423")
        assertNotNull(pn, "number is valid")
    }

    @Test()
    fun testNumberWithoutLocationCode() {
        val pn = PhoneNumber.fromString("01573 1451423")
        assertNull(pn, "number without location code is not supported")
    }

    @Test()
    fun testFormatIsE164() {
        val pn = PhoneNumber.fromString("+49 1573 1451423")
        assertEquals("+4915731451423", pn.toString(), "format is E164")
    }

    @Test()
    fun testSerialization() {
        val a = PhoneNumber.fromString("+49 1573 1451423")
        val b = PhoneNumber.fromString(a.toString())
        assertEquals(a.toString(), b.toString())
    }

}