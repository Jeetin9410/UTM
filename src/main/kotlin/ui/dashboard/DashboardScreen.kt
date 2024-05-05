package ui.dashboard

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.myapp.ui.value.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ui.dashboard.tabs.HomeTab
import ui.dashboard.tabs.ProfileTab
import ui.dashboard.tabs.TestTab
import utils.FirebaseApp
import utils.IconAndTextView

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
        val currentTab = tabNavigator.current

        Column(modifier = modifier.fillMaxHeight().background(R.color.Elephant)) {
            IconAndTextView(
                modifier = Modifier.fillMaxWidth(),
                image = Icons.Default.Home,
                name = "Home",
                isSelected = currentTab is HomeTab,
                onClick = { tabNavigator.current = HomeTab }
            )
            IconAndTextView(
                modifier = Modifier.fillMaxWidth(),
                image = Icons.Default.Settings,
                name = "My tasks",
                isSelected = currentTab is TestTab,
                onClick = { tabNavigator.current = TestTab }
            )
            IconAndTextView(
                modifier = Modifier.fillMaxWidth(),
                image = Icons.Default.AccountCircle,
                name = "Profile",
                isSelected = currentTab is ProfileTab,
                onClick = { tabNavigator.current = ProfileTab }
            )
            // Add more navigation items similarly
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
        var isWindowOpen by remember { mutableStateOf(false) }
        if (isWindowOpen) {
            ExampleDialog({isWindowOpen = false})
        }
        TopAppBar(
            title = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    var searchText by remember { mutableStateOf("") }
                    BasicTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .height(30.dp) // Specify the height to make sure the TextField has a standard size
                            .fillMaxWidth(0.35f) // Use half of the TopAppBar width for the TextField
                            .border(
                                BorderStroke(1.dp, R.color.selectedItemBackgroundColor), // Set border color and width
                                shape = RoundedCornerShape(20.dp) // Set rounded corners
                            ),
                        decorationBox = { innerTextField ->
                            Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.padding(start = 8.dp)) {
                                if (searchText.isEmpty()) {
                                    Text("Search", style = TextStyle(
                                        color = Color.White,
                                        fontSize = 14.0.sp,
                                        fontFamily = FontFamily(
                                            Font("fonts/GoogleSans-Regular.ttf")
                                        )
                                    )
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = onHamburgerClick) {
                    Icon(Icons.Default.Menu, contentDescription = "Toggle Sidebar")
                }
            },
            actions = {
                val profilePainter: Painter = painterResource("drawable/googlelogo.png")
                IconButton(onClick = {
                    isWindowOpen = true
                    snackbarCoroutineScope.launch {
                    snackbarHostState.showSnackbar("Success: Login Successful!")
                } }) {
                    Icon(Icons.Default.AccountCircle, contentDescription = "Toggle Sidebar")
                }
            },
            backgroundColor = R.color.Elephant,
            contentColor = MaterialTheme.colors.onPrimary
        )
    }
    @Composable
    fun ExampleDialog(onDismiss: () -> Unit) {
        Dialog(onCloseRequest = onDismiss, undecorated = true) {
            // Dialog content
            Card(elevation = 8.dp) {
                Column(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("This is a dialog", style = MaterialTheme.typography.h5)
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = onDismiss) {
                        Text("Close")
                    }
                }
            }
        }
    }

}