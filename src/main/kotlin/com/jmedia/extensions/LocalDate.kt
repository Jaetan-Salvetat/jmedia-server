package com.jmedia.extensions

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.reflect.KClass

fun KClass<LocalDate>.fromString(s: String): LocalDate? = try {
    LocalDate.parse(s, DateTimeFormatter.ofPattern("dd MM yyyy"))
} catch (unused: DateTimeParseException) {
    null
}