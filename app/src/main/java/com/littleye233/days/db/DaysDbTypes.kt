package com.littleye233.days.db

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * This class stores a day, designed for serialization, thus some properties have unusual types.
 *
 * @param name Name of the day.
 * @param instant `kotlinx.datetime.Instant` object of the day. The time part is not important, just
 * for the sake of automatic process of time zones.
 */
@Serializable
data class DayItem(val name: String, val instant: Instant)

typealias DayItems = ArrayList<DayItem>

/**
 * This class represents the whole structure of a Days database. It contains the version of
 * structure standard and an array list of [DayItem]s.
 *
 * It has some automatically-generated code for such as equals() and hashCode(). They are not vital
 * for serialization. Just ignore them.
 *
 * @param version An integer of version of structure standard.
 * @param days An array list of day items.
 */
@Serializable
data class DaysDbContent(val version: Int, val days: DayItems)
