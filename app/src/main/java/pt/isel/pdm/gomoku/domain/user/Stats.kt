package pt.isel.pdm.gomoku.domain.user

data class Stats(
    val rating: Int,
    val gamesPlayed: Int,
    val wins: Int,
    val draws: Int,
    val losses: Int
) {
    init {
        require(rating >= 0) { "Rating cannot be negative" }
        require(gamesPlayed >= 0) { "Games played cannot be negative" }
        require(wins >= 0) { "Wins cannot be negative" }
        require(draws >= 0) { "Draws cannot be negative" }
        require(losses >= 0) { "Losses cannot be negative" }
        require(wins + draws + losses == gamesPlayed) { "Game stats are invalid" }
    }

    override fun toString() = "${wins}W ${losses}L ${draws}D"
}
