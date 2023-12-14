package com.littleye233.days.compose.day

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.littleye233.days.ui.theme.DaysTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayDeleteDialog(
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirmClick) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelClick) {
                Text("Cancel")
            }
        },
        text = {
            Text(text = "Confirm deletion? This action cannot be undone.")
        }
    )
}

@Preview
@Composable
fun DayDeleteDialogPreview() {
    DaysTheme {
        Surface {
            DayDeleteDialog({}, {}, {})
        }
    }
}
