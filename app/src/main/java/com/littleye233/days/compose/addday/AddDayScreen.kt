package com.littleye233.days.compose.addday

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.littleye233.days.db.DayItem
import com.littleye233.days.ui.theme.DaysTheme
import kotlinx.datetime.Instant

/**
 * Callback functions passed into these Composables.
 *
 * @param onBackClick Function called when clicking the Navigation Back button.
 * @param onDoneClick Function called when clicking the floating button.
 */
data class AddDayCallbacks(
    val onBackClick: () -> Unit,
    val onDoneClick: (day: DayItem) -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDayScreen(callbacks: AddDayCallbacks) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val textName = remember { mutableStateOf("") }
    val dateMillis = remember { mutableLongStateOf(System.currentTimeMillis()) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                title = {
                    Text(text = "Add New Day", maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                navigationIcon = {
                    IconButton(onClick = callbacks.onBackClick) {
                        Icon(Icons.Filled.ArrowBack, "Navigation Up")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            if (textName.value != "") {
                FloatingActionButton(
                    onClick = {
                        callbacks.onDoneClick(
                            DayItem(
                                name = textName.value,
                                instant = Instant.fromEpochMilliseconds(dateMillis.longValue)
                            )
                        )
                    }
                ) {
                    Icon(Icons.Filled.Check, "Done")
                }
            }
        }
    ) {
        AddDayScreenContent(innerPadding = it, textName, dateMillis)
    }
}

@Composable
fun AddDayScreenContent(
    innerPadding: PaddingValues,
    textName: MutableState<String>,
    dateMillis: MutableLongState
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 15.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Divider()
        Text(
            "Name", style = MaterialTheme.typography.titleMedium
        )
        OutlinedTextField(
            value = textName.value,
            onValueChange = { textName.value = it },
            label = { Text("Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Divider()
        Text(
            "Date", style = MaterialTheme.typography.titleMedium
        )
        DatePickTextField(modifier = Modifier.fillMaxWidth(), dateMillis)
    }
}

@Preview
@Composable
fun AddDayScreenPreview() {
    DaysTheme {
        Surface {
            AddDayScreen(AddDayCallbacks({}, {}))
        }
    }
}
