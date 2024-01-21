package pt.isel.pdm.gomoku.ui.screens.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.pdm.gomoku.domain.user.Stats
import pt.isel.pdm.gomoku.domain.user.User
import pt.isel.pdm.gomoku.domain.user.profile.Profile
import pt.isel.pdm.gomoku.ui.screens.profile.components.ProfileView
import pt.isel.pdm.gomoku.ui.theme.GomokuTheme
import pt.isel.pdm.gomoku.ui.utils.LoadState
import pt.isel.pdm.gomoku.ui.utils.LoadStateRenderer
import pt.isel.pdm.gomoku.ui.utils.success

@Composable
fun ProfileScreen(
    profileState: LoadState<Profile>,
    onProfileRefresh: () -> Unit = {},
    onGetGame: (String) -> Unit = {}
) {
    LoadStateRenderer(
        loadState = profileState,
        pullToRefresh = onProfileRefresh
    ) {
        ProfileView(it, onGetGame)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    GomokuTheme {
        Surface(Modifier.fillMaxSize()) {
            ProfileScreen(
                success(
                    Profile(
                        User("Ricardo", "rcosta@gmail.com", Stats(600, 0, 0, 0, 0)),
                        emptyList()
                    )
                )
            )
        }
    }
}
