package ui.dashboard

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.myapp.ui.value.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ui.dashboard.tabs.HomeTab
import ui.dashboard.tabs.TestTab

class DashboardScreen(private val snackbarCoroutineScope : CoroutineScope, private val snackbarHostState: SnackbarHostState) : Screen {
    @Composable
    override fun Content() {
        TabNavigator(HomeTab) { tabNavigator ->
            var sidebarVisible by remember { mutableStateOf(true) }  // State to control sidebar visibility
            val sidebarWidth by animateDpAsState(targetValue = if (sidebarVisible) 200.dp else 0.dp)

            Column(modifier = Modifier.fillMaxSize()) {
                TopBar(onHamburgerClick = { sidebarVisible = !sidebarVisible })
                Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                    Sidebar(tabNavigator, modifier = Modifier.width(sidebarWidth))
                    if (sidebarWidth > 0.dp) {
                        Divider(modifier = Modifier.width(1.dp))
                    }
                    ContentArea()
                }
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun ProfileDrawer(visible: Boolean, onClose: () -> Unit) {
        AnimatedVisibility(
            visible = visible,
            enter = slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500)),
            exit = slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(500))
        ) {
            Surface(
                modifier = Modifier.width(250.dp).fillMaxHeight(),
                color = MaterialTheme.colors.surface,
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Profile Details", style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text("Additional information here...")
                    Button(onClick = onClose) {
                        Text("Close Drawer")
                    }
                }
            }
        }
    }

    @Composable
    fun Sidebar(tabNavigator: TabNavigator, modifier: Modifier) {
        Column(modifier = modifier.fillMaxHeight().background(color = R.color.Elephant)) {
            Button(onClick = { tabNavigator.current = HomeTab }, modifier = Modifier.fillMaxWidth()) {
                Text("Home")
            }
            Button(onClick = { tabNavigator.current = TestTab }, modifier = Modifier.fillMaxWidth()) {
                Text("Test")
            }
            // Add more buttons for different tabs as needed
        }
    }

    @Composable
    fun ContentArea() {
        val tabNavigator = LocalTabNavigator.current
        val currentTab = tabNavigator.current
        Box(modifier = Modifier.fillMaxSize().background(color = R.color.Info)) {
            currentTab.Content()
        }
    }


    @Composable
    fun TopBar(onHamburgerClick: () -> Unit) {
        TopAppBar(
            title = {
                var searchText by remember { mutableStateOf("") }
                BasicTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    decorationBox = { innerTextField ->
                        Box(contentAlignment = Alignment.CenterStart) {
                            if (searchText.isEmpty()) {
                                Text("Search", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    }
                )
            },
            navigationIcon = {
                IconButton(onClick = onHamburgerClick) {
                    Icon(Icons.Default.Menu, contentDescription = "Toggle Sidebar")
                }
            },
            actions = {
                val profilePainter: Painter = painterResource("drawable/googlelogo.png")
                IconButton(onClick = { snackbarCoroutineScope.launch {
                    snackbarHostState.showSnackbar("Success: Login Successful!")
                } }) {
                    Icon(Icons.Default.AccountCircle, contentDescription = "Toggle Sidebar")
                }
            },
            backgroundColor = R.color.Elephant,
            contentColor = MaterialTheme.colors.onPrimary
        )
    }

}