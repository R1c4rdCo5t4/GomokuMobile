package pt.isel.pdm.gomoku.ui.screens.lobby.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.pdm.gomoku.R
import pt.isel.pdm.gomoku.domain.game.GameConfig
import pt.isel.pdm.gomoku.domain.game.Opening
import pt.isel.pdm.gomoku.domain.game.Variant
import pt.isel.pdm.gomoku.ui.screens.TestTags.Lobby.gameConfigBoardSizeButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Lobby.gameConfigConfirmButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Lobby.gameConfigOpeningButtonTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Lobby.gameConfigScreenTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Lobby.gameConfigVariantButtonTestTag
import pt.isel.pdm.gomoku.ui.theme.GomokuTheme
import pt.isel.pdm.gomoku.ui.utils.Idle
import pt.isel.pdm.gomoku.ui.utils.LoadState
import pt.isel.pdm.gomoku.ui.utils.Loading
import pt.isel.pdm.gomoku.ui.utils.Spacer
import pt.isel.pdm.gomoku.ui.utils.TextButton
import pt.isel.pdm.gomoku.ui.utils.pressClickEffect

@Composable
fun GameConfigScreen(
    lobbyState: LoadState<Unit> = Idle,
    onJoinLobbyRequest: (GameConfig) -> Unit = { _ -> }
) {
    var boardSize by rememberSaveable { mutableStateOf(15) }
    var variantIndex by rememberSaveable { mutableStateOf(0) }
    var openingIndex by rememberSaveable { mutableStateOf(0) }
    val variant = Variant.values()[variantIndex]
    val opening = Opening.values()[openingIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag(gameConfigScreenTestTag),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(stringResource(R.string.game_config), fontSize = 35.sp)
        Spacer(50.dp)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .fillMaxHeight(0.4f)
                .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(5))
                .padding(30.dp)
        ) {
            Row {
                Text(stringResource(R.string.board_size), fontSize = 20.sp)
                Spacer(10.dp)
                TextButton(
                    text = boardSize.toString(),
                    onClick = { boardSize = if (boardSize == 15) 19 else 15 },
                    modifier = Modifier
                        .pressClickEffect()
                        .testTag(gameConfigBoardSizeButtonTestTag)
                )
            }
            Spacer(20.dp)
            Row {
                Text(stringResource(R.string.variant), fontSize = 20.sp)
                Spacer(10.dp)
                TextButton(
                    text = variant.toString(),
                    onClick = { variantIndex = (variantIndex + 1) % Variant.values().size },
                    modifier = Modifier
                        .pressClickEffect()
                        .testTag(gameConfigVariantButtonTestTag)
                )
            }
            Spacer(20.dp)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(R.string.opening), fontSize = 20.sp)
                Spacer(10.dp)
                TextButton(
                    text = opening.toString(),
                    onClick = { openingIndex = (openingIndex + 1) % Opening.values().size },
                    modifier = Modifier
                        .pressClickEffect()
                        .testTag(gameConfigOpeningButtonTestTag)
                )
            }
        }
        Spacer(50.dp)
        Button(
            onClick = {
                val gameConfig = GameConfig(boardSize, variant, opening)
                onJoinLobbyRequest(gameConfig)
            },
            modifier = Modifier
                .padding(20.dp)
                .testTag(gameConfigConfirmButtonTestTag),
            enabled = lobbyState !is Loading
        ) {
            Text(
                text = stringResource(R.string.find_match),
                fontSize = 20.sp
            )
            Spacer(10.dp)
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Find Match",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameConfigScreenPreview() {
    GomokuTheme {
        Surface(Modifier.fillMaxSize()) {
            GameConfigScreen()
        }
    }
}
