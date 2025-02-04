package com.hustle.awayadminside.navbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.hustle.awayadminside.Screen
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(navController: NavHostController, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()
    ModalDrawerSheet() {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(300.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(12.dp))
            HorizontalDivider()
            NavigationDrawerItem(
                label = { Text("Home") },
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                selected = false,
                onClick = {
                    scope.launch {
                        drawerState.close()
                        navController.navigate(Screen.Home.route)
                    }
                }
            )
            NavigationDrawerItem(
                label = { Text("Add Notice") },
                icon = { Icon(Icons.Outlined.Check, contentDescription = "Settings") },
                selected = false,
                onClick = {
                    scope.launch { drawerState.close() }
                    navController.navigate(Screen.Notice.route)
                }
            )
            NavigationDrawerItem(
                label = { Text("Support") },
                icon = { Icon(Icons.Outlined.Warning, contentDescription = "Payment") },
                selected = false,
                onClick = {
                    scope.launch { drawerState.close() }
                    navController.navigate(Screen.CreateUser.route)
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            HorizontalDivider()
            Text(
                text = "Logout",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        scope.launch { drawerState.close() }
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    },
                color = Color.Gray,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp
            )
        }
    }
}
