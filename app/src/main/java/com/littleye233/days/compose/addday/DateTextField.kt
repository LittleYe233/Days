package com.littleye233.days.compose.addday

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.littleye233.days.ui.theme.DaysTheme
import com.littleye233.days.util.getLocalFormattedDate
import com.littleye233.days.util.getLocalFormattedDateTime
import java.time.Instant
import java.time.LocalDateTime
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DatePickTextField(
    modifier: Modifier = Modifier,
    dateMillis: MutableLongState = mutableLongStateOf(System.currentTimeMillis())
) {
    var textDate by remember { mutableStateOf(getLocalFormattedDate(null)) }
    var isDialogOpen by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = textDate,
        onValueChange = { textDate = it },
        readOnly = true,
        // It should be added to make `Modifier.clickable` work.
        enabled = false,
        modifier = modifier.clickable { isDialogOpen = true },
        trailingIcon = {
            Icon(imageVector = Icons.Default.DateRange, contentDescription = "Pick Date")
        }
    )
    if (isDialogOpen) {
        DatePickTextFieldDialog(
            onDismissRequest = { isDialogOpen = false },
            onConfirmClick = { datePickerState ->
                isDialogOpen = false
                // I think it won't be null.
                val dt = LocalDateTime.ofInstant(
                    datePickerState.selectedDateMillis?.let { Instant.ofEpochMilli(it) },
                    TimeZone.getDefault().toZoneId()
                )
                textDate = getLocalFormattedDateTime(dt)
                // I think it won't be null either.
                dateMillis.longValue = datePickerState.selectedDateMillis!!
            },
            onDismissClick = { isDialogOpen = false },
            initialSelectedDateMillis = dateMillis.longValue
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickTextFieldDialog(
    onDismissRequest: () -> Unit,
    onConfirmClick: (DatePickerState) -> Unit,
    onDismissClick: () -> Unit,
    initialSelectedDateMillis: Long? = null
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialSelectedDateMillis ?: System.currentTimeMillis()
    )
    val isConfirmEnabled by remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }

    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = { onConfirmClick(datePickerState) },
                enabled = isConfirmEnabled
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissClick) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DatePickTextFieldDialogPreview() {
    DaysTheme {
        Surface {
            DatePickTextFieldDialog(
                onDismissRequest = {},
                onConfirmClick = {},
                onDismissClick = {}
            )
        }
    }
}
