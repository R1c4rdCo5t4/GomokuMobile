package pt.isel.pdm.gomoku.domain.game

enum class Opening {
    FREESTYLE, PRO, LONG_PRO;

    override fun toString(): String {
        return when (this) {
            FREESTYLE -> "Freestyle"
            PRO -> "Pro"
            LONG_PRO -> "Long Pro"
        }
    }
}
