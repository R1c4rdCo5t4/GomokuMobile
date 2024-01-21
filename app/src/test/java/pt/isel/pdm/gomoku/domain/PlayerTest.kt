package pt.isel.pdm.gomoku.domain

import org.junit.Test
import pt.isel.pdm.gomoku.domain.game.Color
import pt.isel.pdm.gomoku.domain.game.Player
import pt.isel.pdm.gomoku.domain.user.Stats
import pt.isel.pdm.gomoku.domain.user.User
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PlayerTest {

    private fun createPlayer(
        name: String = "testUser",
        random: Int = UUID.randomUUID().variant(),
        stats: Stats = Stats(600, 0, 0, 0, 0),
        color: Color = Color.BLACK
    ): Player {
        val user = User("$name$random", "$name$random@mail.com", stats)
        return Player(user, color)
    }

    @Test
    fun `test user creation`() {
        val random = UUID.randomUUID().variant()
        val player = createPlayer(random = random)

        assertEquals(600, player.stats.rating)
        assertEquals(0, player.stats.wins)
        assertEquals(0, player.stats.losses)
        assertEquals(0, player.stats.draws)
        assertEquals(0, player.stats.gamesPlayed)
        assertEquals("testUser$random", player.user.name)
        assertEquals("testUser$random@mail.com", player.user.email)
        assertEquals(Color.BLACK, player.color)
    }

    @Test
    fun `test invalid user creation`() {
        assertFailsWith<IllegalArgumentException> {
            createPlayer(stats = Stats(-1, 0, 0, 0, 0))
        }
        assertFailsWith<IllegalArgumentException> {
            createPlayer(stats = Stats(0, -1, 0, 0, 0))
        }
        assertFailsWith<IllegalArgumentException> {
            createPlayer(stats = Stats(0, 0, -1, 0, 0))
        }
        assertFailsWith<IllegalArgumentException> {
            createPlayer(stats = Stats(0, 0, 0, -1, 0))
        }
        assertFailsWith<IllegalArgumentException> {
            createPlayer(stats = Stats(0, 0, 0, 0, -1))
        }
        assertFailsWith<IllegalArgumentException> {
            Player(
                User(
                    "",
                    "user@mail.com",
                    Stats(0, 0, 0, 0, 0)
                ),
                Color.BLACK
            )
        }
    }
}
