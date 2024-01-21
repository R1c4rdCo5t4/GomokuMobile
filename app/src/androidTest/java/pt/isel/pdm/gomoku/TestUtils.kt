package pt.isel.pdm.gomoku

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick

fun ComposeContentTestRule.clickButton(buttonTag: String) {
    onNodeWithTag(buttonTag).performClick()
}

fun ComposeContentTestRule.assertContentExists(vararg checkTags: String) {
    checkTags.forEach { onNodeWithTag(it).assertExists() }
}

fun ComposeContentTestRule.assertContentNotExists(vararg checkTags: String) {
    checkTags.forEach { onNodeWithTag(it).assertDoesNotExist() }
}
