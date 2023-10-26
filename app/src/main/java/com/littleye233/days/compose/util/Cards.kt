package com.littleye233.days.compose.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.littleye233.days.ui.theme.DaysTheme
import com.littleye233.days.util.getLocalFormattedDate
import java.time.LocalDate

@Preview
@Composable
fun TopDateCard() {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Today is",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = getLocalFormattedDate(null),
                fontSize = 30.sp
            )
        }
    }
}

@Composable
fun DayCard(title: String, date: LocalDate, days: Int) {
    // Main Card of This Day
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Row Wrapper
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            // Title and Date
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = getLocalFormattedDate(date),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            // Sub-card of Day
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                // Column Wrapper
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(120.dp)
                ) {
                    Text(
                        style = MaterialTheme.typography.titleLarge,
                        text = buildAnnotatedString {
                            append("$days ")
                            withStyle(style = MaterialTheme.typography.bodyLarge.toSpanStyle()) {
                                if (days == 1 || days == -1) {
                                    append("day")
                                } else {
                                    append("days")
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DayCardPreview() {
    DaysTheme {
        Surface {
            Column {
                DayCard(
                    "Test Title",
                    LocalDate.of(2023, 1, 1),
                    99999
                )
                DayCard(
                    "Test Title",
                    LocalDate.of(2023, 1, 1),
                    1
                )
            }
        }
    }
}
