package de.simonknott.neer.sms

import de.simonknott.neer.Config
import de.simonknott.neer.users.PhoneNumber
import de.simonknott.neer.util.Dependency
import de.simonknott.neer.util.Provider
import notifications.MatchNotificationService
import notifications.MatchNotificationServiceImpl

interface SMSTransport {
    fun send(recipient: PhoneNumber, message: String)

    companion object: Dependency<SMSTransport> by Provider({
        when (Config.SMS_TRANSPORT) {
            Config.SMSTransport.TWILIO -> SMSTransportTwilioImpl
        }
    })
}