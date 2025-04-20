package com.pocket.usermanagement.features.login.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pocket.usermanagement.R
import com.pocket.usermanagement.di.AppViewModelFactory
import com.pocket.usermanagement.network.RequestBuilder


@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel(factory = AppViewModelFactory.LoginFactory)) {
    val context = LocalContext.current
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("")}
    val showLoading by loginViewModel.mutableStateFlowLoading.collectAsState()
    var isButtonEnabled by remember { mutableStateOf(false) }
    val loginError by loginViewModel.mutableStateFlowLoginError.collectAsState()

    if(!loginError.isNullOrEmpty()) {
        Toast.makeText(context, loginError, Toast.LENGTH_LONG).show()
    }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            if(showLoading) CircularProgressIndicator()

            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it
                                 isButtonEnabled = userName.isNotEmpty() && password.isNotEmpty()
                                },
                label = { Text(stringResource(id = R.string.str_user_name)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    isButtonEnabled = userName.isNotEmpty() && password.isNotEmpty()
                },
                label = { Text(stringResource(id = R.string.str_password)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(8.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),

            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                  val loginRequest = RequestBuilder.buildLoginRequest(userName, password)
                   loginViewModel.doLogin(loginRequest)
                },
                enabled = isButtonEnabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.str_login))
            }
        }
    }
}



