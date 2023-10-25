package com.littleye233.days.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Get date time string in local format.
 *
 * @param dt A `LocalDateTime` object, or null for a current date time.
 */
fun getLocalFormattedDateTime(dt: LocalDateTime?): String {
    val realDt = dt ?: LocalDateTime.now()
    // @see https://stackoverflow.com/a/70196552/12002560
    val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
    return realDt.format(formatter)
}

/**
 * Get date string in local format.
 *
 * @param dt A `LocalDate` object, or null for a current date.
 */
fun getLocalFormattedDate(dt: LocalDate?): String {
    val realDt = dt ?: LocalDate.now()
    // @see https://stackoverflow.com/a/70196552/12002560
    val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
    return realDt.format(formatter)
}
