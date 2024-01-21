package pt.isel.pdm.gomoku.ui.screens.home.components.watch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.pdm.gomoku.R
import pt.isel.pdm.gomoku.domain.home.watch.WatchList
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.liveGameViewTestTag
import pt.isel.pdm.gomoku.ui.screens.TestTags.Home.watchLiveScreenTestTag
import pt.isel.pdm.gomoku.ui.screens.home.HomeViewModel.Companion.WATCH_MAX_GAMES
import pt.isel.pdm.gomoku.ui.screens.home.components.watch.components.LiveGameView
import pt.isel.pdm.gomoku.ui.theme.GomokuTheme
import pt.isel.pdm.gomoku.ui.utils.Spacer
import pt.isel.pdm.gomoku.ui.utils.TextButton

@Composable
fun WatchLiveScreen(
    watchList: WatchList,
    onWatchGameRequest: (String) -> Unit = {},
    onLiveGamesLoadMore: () -> Unit = {}
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp, vertical = 20.dp)
            .testTag(watchLiveScreenTestTag)
    ) {
        item {
            Spacer(10.dp)
            Text(
                text = stringResource(R.string.watch_live),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(20.dp)
        }
        if (watchList.games.isEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.no_live_games),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            items(watchList.games.size) { index ->
                LiveGameView(
                    game = watchList.games[index],
                    onWatchGameRequest = onWatchGameRequest,
                    modifier = Modifier.testTag("$liveGameViewTestTag-$index")
                )
                Spacer(20.dp)
            }
            item {
                if (watchList.games.size % WATCH_MAX_GAMES == 0) {
                    TextButton(
                        text = "Load More",
                        onClick = onLiveGamesLoadMore,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    )
                }
                Spacer(20.dp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WatchLivePreview() {
    GomokuTheme {
        Surface(Modifier.fillMaxSize()) {
            WatchLiveScreen(WatchList(emptyList()))
        }
    }
}
