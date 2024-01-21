package pt.isel.pdm.gomoku.ui.screens.game.components

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pt.isel.pdm.gomoku.R
import pt.isel.pdm.gomoku.ui.utils.Popup
import pt.isel.pdm.gomoku.ui.utils.TextButton

@Composable
fun LeaveGamePopup(
    leaveEnable: Boolean = true,
    onClose: () -> Unit,
    onLeaveGame: () -> Unit
) {
    Popup(
        title = stringResource(R.string.leave_game_popup),
        onClose = onClose,
        modifier = Modifier.testTag("LeaveGamePopupTestTag")
    ) {
        TextButton(
            onClick = onLeaveGame,
            text = stringResource(R.string.yes),
            modifier = Modifier.width(100.dp),
            enabled = leaveEnable
        )
        TextButton(
            onClick = onClose,
            text = stringResource(R.string.no),
            modifier = Modifier.width(100.dp)
        )
    }
}
