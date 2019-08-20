package de.simonknott.neer.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toISO8601(): String = this.format(DateTimeFormatter.ISO_DATE_TIME)