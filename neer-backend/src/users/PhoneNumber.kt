package de.simonknott.neer.users

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil

val util = PhoneNumberUtil.getInstance()!!

class PhoneNumber private constructor(private val number: com.google.i18n.phonenumbers.Phonenumber.PhoneNumber) {

    override fun toString(): String = util.format(number, PhoneNumberUtil.PhoneNumberFormat.E164)

    companion object {

        /**
         * @param number Phone number including country code ("+49 1573 ...")
         */
        fun fromString(number: String): PhoneNumber? {
            val pn = parseToLibphonenumber(number) ?: return null
            return PhoneNumber(pn)
        }

        private fun parseToLibphonenumber(number: String): com.google.i18n.phonenumbers.Phonenumber.PhoneNumber? {
            return try {
                val proto = util.parse(number, null)
                if (util.isValidNumber(proto)) proto else null
            } catch (e: NumberParseException) {
                null
            }
        }

    }
}
