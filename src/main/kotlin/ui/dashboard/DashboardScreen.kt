package ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.myapp.ui.value.R
import kotlinx.coroutines.CoroutineScope
import ui.dashboard.tabs.HomeTab
import ui.dashboard.tabs.TestTab

class DashboardScreen(private val snackbarCoroutineScope : CoroutineScope, private val snackbarHostState: SnackbarHostState) : Screen {
    @Composable
    override fun Content() {
        TabNavigator(HomeTab) {tabNavigator ->
            Row(modifier = Modifier.fillMaxSize()) {
                Sidebar(tabNavigator, modifier = Modifier.width(200.dp))
                Divider(modifier = Modifier.width(1.dp))
                ContentArea()
            }
            /*Scaffold(
                content = {
                    CurrentTab()
                },
                drawerContent = {
                                Sidebar(navigator)
                },
                *//*bottomBar = {
                    BottomNavigation {
                        TabNavigationItem(HomeTab)
                        TabNavigationItem(TestTab)
                        TabNavigationItem(HomeTab)
                    }
                }*//*
            )*/
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
        Box(modifier = Modifier.fillMaxSize().padding(10.dp)) {
            currentTab.Content()
        }
    }

    @Composable
    fun Sidebar(navigator: TabNavigator) {
        Column(modifier = Modifier.fillMaxHeight().width(200.dp).padding(10.dp)) {
            Button(onClick = { navigator.current =HomeTab }, modifier = Modifier.fillMaxWidth()) {
                Text("Home")
            }
            Button(onClick = { navigator.current = TestTab }, modifier = Modifier.fillMaxWidth()) {
                Text("Test")
            }
            // Add more buttons for different tabs or screens
        }
    }

    @Composable
    fun CurrentTab() {
        val tabNavigator = LocalTabNavigator.current
        val currentTab = tabNavigator.current
        currentTab.Content()
    }
    /*@Composable
    private fun RowScope.TabNavigationItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current

        BottomNavigationItem(
            selected = tabNavigator.current.key == tab.key,
            onClick = { tabNavigator.current = tab },
            icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) }
        )
    }*/
}