package com.littleye233.days

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import com.littleye233.days.compose.DaysApp
import com.littleye233.days.db.DaysDb
import com.littleye233.days.db.DaysDbContent
import com.littleye233.days.ui.theme.DaysTheme

class MainActivity : ComponentActivity() {
    private lateinit var dbContent: DaysDbContent  // Placeholder!

    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Database
        val db = DaysDb(applicationContext)
        db.initialize()
        dbContent = db.parse(db.read())

        setContent {
            DaysTheme {
                DaysApp(dbContent)
            }
        }
    }
}
