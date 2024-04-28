package ui.dashboard

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.CoroutineScope

class DashboardScreen(private val snackbarCoroutineScope : CoroutineScope, private val snackbarHostState: SnackbarHostState) : Screen {
    @Composable
    override fun Content() {

    }
}