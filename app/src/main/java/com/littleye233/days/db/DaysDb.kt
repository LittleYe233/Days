package com.littleye233.days.db

import android.content.Context
import android.util.Log
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.FileNotFoundException

const val LOG_TAG = "db.DaysDb"

class DaysDb(private val context: Context) {
    companion object {
        private const val DB_FILENAME = "db.json"
        private const val VERSION = 1
        val DB_DEFAULT = DaysDbContent(VERSION, DayItems())
    }

    fun initialize() {
        try {
            context.openFileInput(DB_FILENAME)
        } catch (e: FileNotFoundException) {
            Log.w(LOG_TAG, "Database file $DB_FILENAME not found")
            Log.v(LOG_TAG, "Trying to create default database file")
            write(stringify(DB_DEFAULT))
        }
        Log.v(LOG_TAG, "Successfully initialized")
    }

    fun read(): String {
        // @see https://stackoverflow.com/a/41000650/12002560
        return context.openFileInput(DB_FILENAME).bufferedReader().use(BufferedReader::readText)
    }

    fun write(text: String) {
        context.openFileOutput(DB_FILENAME, Context.MODE_PRIVATE).use {
            it.write(text.toByteArray())
        }
    }

    fun parse(text: String): DaysDbContent {
        return Json.decodeFromString<DaysDbContent>(text)
    }

    fun stringify(cont: DaysDbContent): String {
        return Json.encodeToString(cont)
    }
}
