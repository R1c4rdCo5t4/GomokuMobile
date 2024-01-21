package pt.isel.pdm.gomoku.profile

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import pt.isel.pdm.gomoku.assertContentExists
import pt.isel.pdm.gomoku.domain.user.Stats
import pt.isel.pdm.gomoku.domain.user.User
import pt.isel.pdm.gomoku.domain.user.profile.Profile
import pt.isel.pdm.gomoku.services.http.utils.APIResult
import pt.isel.pdm.gomoku.ui.screens.TestTags.Profile.profileScreenTestTag
import pt.isel.pdm.gomoku.ui.screens.profile.ProfileScreen
import pt.isel.pdm.gomoku.ui.utils.loaded

class ProfileScreenTest {

    @get:Rule
    val testRule = createComposeRule()

    private val loadedProfile = loaded(
        APIResult.success(
            Profile(
                User("Ricardo", "ricardo@mail.com", Stats(0, 0, 0, 0, 0)),
                emptyList()
            )
        )
    )

    @Test
    fun profile_screen() {
        testRule.setContent { ProfileScreen(profileState = loadedProfile) }
        testRule.assertContentExists(profileScreenTestTag)
    }
}
