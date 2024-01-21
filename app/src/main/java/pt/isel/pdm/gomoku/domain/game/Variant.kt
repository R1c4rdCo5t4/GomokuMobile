package pt.isel.pdm.gomoku.domain.game

enum class Variant {
    FREESTYLE;

    override fun toString(): String {
        return when (this) {
            FREESTYLE -> "Freestyle"
        }
    }
}
