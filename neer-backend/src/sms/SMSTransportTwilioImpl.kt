package de.simonknott.neer.sms

import com.twilio.Twilio
import com.twilio.rest.chat.v2.service.channel.Message
import de.simonknott.neer.users.PhoneNumber

object SMSTransportTwilioImpl : SMSTransport {

    init {
        Twilio.init()
    }

    override fun send(recipient: PhoneNumber, message: String) {
        val message = Message()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun PhoneNumber.toTwilioPN() = com.twilio.type.PhoneNumber(this.toString())
}