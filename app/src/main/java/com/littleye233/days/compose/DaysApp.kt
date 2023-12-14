package com.littleye233.days.compose

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.littleye233.days.compose.addday.AddDayCallbacks
import com.littleye233.days.compose.addday.AddDayScreen
import com.littleye233.days.compose.day.DayScreen
import com.littleye233.days.compose.home.HomeScreen
import com.littleye233.days.compose.home.HomeScreenCallback
import com.littleye233.days.db.DaysDb
import com.littleye233.days.db.DaysDbContent
import kotlinx.coroutines.launch

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
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                dbc, snackbarHostState,
                HomeScreenCallback(
                    onAddClick = { navController.navigate("addDay") },
                    onDayCardClick = { navController.navigate("day/$it") }
                )
            )
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
                    scope.launch {
                        snackbarHostState.showSnackbar("Added a new day")
                    }
                }
            ))
        }
        composable(
            "day/{dayId}",
            arguments = listOf(navArgument("dayId") { type = NavType.IntType })
        ) {
            val dayId = it.arguments?.getInt("dayId") ?: -1
            val context = LocalContext.current
            if (dayId != -1 && dayId < dbc.value.days.size) {
                DayScreen(
                    day = dbc.value.days[dayId],
                    onBackClick = { navController.navigateUp() },
                    onDeleteConfirm = {
                        Log.v("compose.DaysApp", "Database Content: ${dbc.value}")
                        Log.v("compose.DaysApp", "dayId: $dayId")
                        dbc.value.days.removeAt(dayId)
                        dbc.value = dbc.value.copy()
                        val db = DaysDb(context)
                        db.write(db.stringify(dbc.value))
                        navController.navigate("home")
                        scope.launch {
                            snackbarHostState.showSnackbar("Deleted a day")
                        }
                    }
                )
            }
        }
    }
}
