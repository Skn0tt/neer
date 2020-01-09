package de.simonknott.neer.users

import de.simonknott.neer.Config
import org.junit.Test
import kotlin.test.assertEquals

class UserIdGeneratorTest {

    @Test
    fun testLength() {
        assertEquals(UserIdGenerator.next().length, Config.USER_ID_LENGTH)
    }

}