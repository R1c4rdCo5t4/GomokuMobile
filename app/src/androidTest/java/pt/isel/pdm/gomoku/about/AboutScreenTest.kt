package pt.isel.pdm.gomoku.about

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import pt.isel.pdm.gomoku.assertContentExists
import pt.isel.pdm.gomoku.domain.home.about.Home
import pt.isel.pdm.gomoku.ui.screens.TestTags.About.aboutScreenTestTag
import pt.isel.pdm.gomoku.ui.screens.home.components.about.AboutScreen

class
AboutScreenTest {

    @get:Rule
    val testRule = createComposeRule()

    @Test
    fun about_screen_initial_state() {
        testRule.setContent {
            AboutScreen(Home("", "", "", emptyList(), ""))
        }
        testRule.assertContentExists(aboutScreenTestTag)
    }
}
