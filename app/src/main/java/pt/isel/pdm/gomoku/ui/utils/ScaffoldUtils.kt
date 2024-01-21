package pt.isel.pdm.gomoku.ui.utils

import androidx.compose.foundation.layout.height
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.pdm.gomoku.R

@Composable
fun ScaffoldTopAppBar(title: String, onNavigationButtonClick: () -> Unit = {}) {
    TopAppBar(
        modifier = Modifier.height(70.dp),
        backgroundColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary,
        title = {
            Text(text = title, fontSize = 18.sp)
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigationButtonClick,
                icon = Icons.Filled.ArrowBack,
                contentDescription = stringResource(R.string.navigation_button_icon),
                size = 35.dp,
                buttonColor = Color.Transparent,
                iconColor = MaterialTheme.colorScheme.inverseOnSurface
            )
        }
    )
}
