package com.pocket.usermanagement.features.profile.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateOfBirthPicker(
    label: String,
    initialDate: Long? = null, // Optional initial date in milliseconds
    onDateSelected: (Long?) -> Unit // Callback with selected date in milliseconds
) {
    var selectedDateMillis by remember { mutableStateOf(initialDate) }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    OutlinedTextField(
        value = selectedDateMillis?.let { dateFormatter.format(Date(it)) } ?: "",
        onValueChange = { /* Read-only, so no action here */ },
        label = { Text(label) },
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDatePickerDialog = true },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.CalendarToday,
                contentDescription = "Select Date",
                modifier = Modifier.clickable { showDatePickerDialog = true }
            )
        }
    )

    if (showDatePickerDialog) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDateMillis ?: System.currentTimeMillis()
        )
        DatePickerDialog(
            onDismissRequest = { showDatePickerDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePickerDialog = false
                        selectedDateMillis = datePickerState.selectedDateMillis
                        onDateSelected(selectedDateMillis)
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePickerDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DateOfBirthPickerPreview() {
    var dateOfBirth by remember { mutableStateOf<Long?>(null) }
    MaterialTheme {
        DateOfBirthPicker(
            label = "Date of Birth",
            initialDate = dateOfBirth,
            onDateSelected = { selectedMillis ->
                dateOfBirth = selectedMillis
                // You can convert selectedMillis to a more specific date object here if needed
                // For example, using kotlinx-datetime:
                // val instant = kotlinx.datetime.Instant.fromEpochMilliseconds(selectedMillis)
                // val localDate = instant.toLocalDateTime(kotlinx.datetime.TimeZone.UTC).date
            }
        )
    }
}
