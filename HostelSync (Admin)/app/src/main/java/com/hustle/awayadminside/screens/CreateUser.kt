package com.hustle.awayadminside.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.hustle.awayadminside.auth.SignUpState
import com.hustle.awayadminside.auth.SignUpViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.hustle.awayadminside.navbar.DrawerContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUser(
//    onSignUpClick: SignUpState.Success,
    viewModel: SignUpViewModel = viewModel(),
    navController: NavHostController
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current
    val signUpState by viewModel.signUpState
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    LaunchedEffect(signUpState) {
        when (signUpState) {
            is SignUpState.Success -> {
//                onSignUpClick()
                viewModel.resetState()
            }
            is SignUpState.Error -> {}
            else -> {}
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Main Menu",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )
                HorizontalDivider()
                 DrawerContent(navController = navController, drawerState = drawerState) // Uncomment if using navigation
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Create New Resident",
                            maxLines = 1,
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Full Name input
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("Full Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = signUpState !is SignUpState.Loading
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Email input
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = signUpState !is SignUpState.Loading
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Password input
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = signUpState !is SignUpState.Loading
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Confirm Password input
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = signUpState !is SignUpState.Loading
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Error message
                if (signUpState is SignUpState.Error) {
                    Text(
                        text = (signUpState as SignUpState.Error).message,
                        color = Color.Red,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                // Sign Up button
                Button(
                    onClick = {
                        scope.launch {
                            viewModel.signUpWithEmailPassword(
                                fullName = fullName,
                                email = email,
                                password = password,
                                confirmPassword = confirmPassword
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(12.dp),
                    enabled = signUpState !is SignUpState.Loading
                ) {
                    if (signUpState is SignUpState.Loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(text = "Add Resident")
                        viewModel.resetState()
                    }
                    if(signUpState is SignUpState.Success) {
                        Text(text = "Resident Added")
                    }
                }
            }
        }
    }
}
