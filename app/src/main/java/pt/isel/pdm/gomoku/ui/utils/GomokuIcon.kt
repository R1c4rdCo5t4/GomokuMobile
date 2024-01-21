package pt.isel.pdm.gomoku.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import pt.isel.pdm.gomoku.R

@Composable
fun GomokuIcon(size: Dp) {
    Image(
        painter = painterResource(id = R.drawable.gomoku_icon),
        contentDescription = stringResource(R.string.gomoku_icon),
        modifier = Modifier.size(size)
    )
}
