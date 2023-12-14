package com.littleye233.days.compose.day

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.littleye233.days.db.DayItem
import com.littleye233.days.ui.theme.DaysTheme
import kotlinx.datetime.Clock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayScreen(
    day: DayItem,
    onBackClick: () -> Unit,
    onDeleteConfirm: () -> Unit
) {
    val deleteState = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back to Home"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { deleteState.value = true }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete Day"
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit Day"
                        )
                    }
                }
            )
        }
    ) {
        DayScreenContent(
            innerPadding = it,
            day,
            deleteState,
            onDeleteConfirm = onDeleteConfirm
        )
    }
}

@Composable
fun DayScreenContent(
    innerPadding: PaddingValues,
    day: DayItem,
    deleteState: MutableState<Boolean>,
    onDeleteConfirm: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
    ) {
        Text(day.name)
        Text(day.instant.toString())

        // Deletion dialog
        if (deleteState.value) {
            DayDeleteDialog(
                onDismissRequest = { deleteState.value = false },
                onConfirmClick = {
                    deleteState.value = false
                    onDeleteConfirm()
                },
                onCancelClick = { deleteState.value = false }
            )
        }
    }
}

@Preview
@Composable
fun DayScreenPreview() {
    DaysTheme {
        Surface {
            DayScreen(
                DayItem(name = "test", instant = Clock.System.now()), {}
            ) {}
        }
    }
}
