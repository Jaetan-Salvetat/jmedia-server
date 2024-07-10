package com.jmedia.extensions

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.reflect.KClass

/**
 * Convert a [String] to a [LocalDate]
 *
 * @param str [String]
 * @return
 */
fun KClass<LocalDate>.fromString(str: String): LocalDate? = try {
    LocalDate.parse(str, DateTimeFormatter.ofPattern("dd MM yyyy"))
} catch (unused: DateTimeParseException) {
    null
}