import java.util.*

private val encoder = Base64.getEncoder()

fun ByteArray.toBase64() = encoder.encodeToString(this)

private val decoder = Base64.getDecoder()

fun String.fromBase64() = decoder.decode(this)