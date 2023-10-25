package com.littleye233.days.compose

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.littleye233.days.compose.addday.AddDayCallbacks
import com.littleye233.days.compose.addday.AddDayScreen
import com.littleye233.days.compose.home.HomeScreen
import com.littleye233.days.db.DaysDb
import com.littleye233.days.db.DaysDbContent

@Composable
fun DaysApp(dbContent: DaysDbContent) {
    val navController = rememberNavController()
    DaysAppNavHost(navController = navController, dbContent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaysAppNavHost(
    navController: NavHostController,
    dbContent: DaysDbContent
) {
    val dbc = remember { mutableStateOf(dbContent) }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(dbc) { navController.navigate("addDay") }
        }
        composable("addDay") {
            val context = LocalContext.current
            AddDayScreen(AddDayCallbacks(
                onBackClick = { navController.navigateUp() },
                onDoneClick = {
                    dbc.value.days.add(it)
                    dbc.value = dbc.value.copy()  // Trigger to refresh
                    val db = DaysDb(context)
                    db.write(db.stringify(dbc.value))
                    navController.navigateUp()
                }
            ))
        }
    }
}
