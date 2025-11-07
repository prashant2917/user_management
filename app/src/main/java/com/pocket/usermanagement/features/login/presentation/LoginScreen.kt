package com.pocket.usermanagement.features.login.presentation

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pocket.usermanagement.R
import com.pocket.usermanagement.network.ApiErrorException
import com.pocket.usermanagement.network.RequestBuilder
import com.pocket.usermanagement.ui.navigation.AppNavigationScreen
import com.pocket.usermanagement.utils.AppLogger
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    val context = LocalContext.current
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val showLoading by loginViewModel.mutableStateFlowLoading.collectAsState()
    var isButtonEnabled by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current
    val snackBarHostState = remember { SnackbarHostState() }


    LaunchedEffect(key1 = lifecycleOwner.lifecycle, key2 = loginViewModel.loginEventFlow) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            launch {
                loginViewModel.loginEventFlow.collect { loginEvent ->
                    when (loginEvent) {
                        is LoginScreenEvent.LoginScreenSuccess -> {
                            snackBarHostState.showSnackbar(
                                message = context.getString(R.string.str_user_login_success),
                                duration = SnackbarDuration.Short,
                                actionLabel = context.getString(R.string.str_proceed),
                                withDismissAction = true
                            )
                            loginViewModel.saveUserId(loginEvent.userEntity?.id.toString())
                            loginViewModel.setIsUserLogin(true)
                            navController.navigate(AppNavigationScreen.HOME.name)
                            loginViewModel.resetValues()

                        }

                        is LoginScreenEvent.LoginScreenError -> {
                            loginEvent.exception.let {
                                AppLogger.d("LoginScreen", "loginError not null")
                                loginError =
                                    ApiErrorException.getErrorMessage(context, loginEvent.exception)
                                if (loginError.isNotEmpty()) {
                                    snackBarHostState.showSnackbar(
                                        message = loginError,
                                        duration = SnackbarDuration.Long,
                                        actionLabel = context.getString(R.string.str_dismiss),
                                        withDismissAction = true
                                    )

                                }
                            }

                        }
                    }
                }

            }

        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { contentPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            color = MaterialTheme.colorScheme.background,

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

                if (showLoading) CircularProgressIndicator()

                OutlinedTextField(
                    value = userName,
                    onValueChange = {
                        userName = it
                        isButtonEnabled = userName.isNotEmpty() && password.isNotEmpty()
                    },
                    label = { Text(stringResource(id = R.string.str_user_name)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
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
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {

                            keyboardController?.hide()
                            val loginRequest = RequestBuilder.buildLoginRequest(userName, password)
                            loginViewModel.doLogin(loginRequest)
                        },

                        ),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            ImageVector.vectorResource(R.drawable.ic_password_visible)
                        else ImageVector.vectorResource(R.drawable.ic_password_invisible)

                        // Localized description for accessibility services
                        val description = if (passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, description)
                        }
                    }
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
}

@Preview
@Composable
fun PreviewLoginScreen() {
   LoginScreen(navController = rememberNavController(), loginViewModel = hiltViewModel())
}


