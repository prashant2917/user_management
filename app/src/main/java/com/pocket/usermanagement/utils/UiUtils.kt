package com.pocket.usermanagement.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable

object UiUtils {
@Composable
fun ShowToast(context: Context, message: String?) {
   Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}
}
