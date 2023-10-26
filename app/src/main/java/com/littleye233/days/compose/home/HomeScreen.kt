package com.littleye233.days.compose.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.littleye233.days.compose.util.DayCard
import com.littleye233.days.compose.util.TopDateCard
import com.littleye233.days.db.DaysDb
import com.littleye233.days.db.DaysDbContent
import com.littleye233.days.ui.theme.DaysTheme
import kotlinx.datetime.toJavaInstant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.util.TimeZone

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    db: MutableState<DaysDbContent>,
    snackbarHostState: SnackbarHostState,
    onAddClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                title = {
                    Text(
                        text = "Days",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Filled.Add, contentDescription = "Add Day")
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        HomeScreenContent(it, db)
    }
}

@Composable
fun HomeScreenContent(innerPadding: PaddingValues, db: MutableState<DaysDbContent>) {
    val nowDt = LocalDate.now()
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 15.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        TopDateCard()
        Text(
            text = "Your Days",
            style = MaterialTheme.typography.titleSmall
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
           db.value.days.forEach {
               val dt = LocalDateTime.ofInstant(
                   it.instant.toJavaInstant(),
                   TimeZone.getDefault().toZoneId()
               ).toLocalDate()
               val period = Period.between(dt, nowDt)
               DayCard(
                   title = it.name,
                   date = dt,
                   days = period.days
               )
           }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeScreenPreview() {
    DaysTheme {
        Surface {
            HomeScreen(mutableStateOf(DaysDb.DB_DEFAULT), SnackbarHostState()) {}
        }
    }
}
