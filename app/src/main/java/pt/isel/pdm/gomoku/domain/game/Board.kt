package pt.isel.pdm.gomoku.domain.game

data class Board(
    val size: Int,
    private val pieces: List<Piece> = emptyList()
) {
    init {
        require(size in listOf(15, 19)) { "Board size must be 15 or 19" }
        require(pieces.all { it.coord.row in 1..size && it.coord.col in 1..size }) { "All pieces must be inside the board" }
        require(pieces.size == pieces.distinctBy { it.coord }.size) { "All pieces should have different coordinates" }
    }

    fun getPieceAt(row: Int, col: Int): Piece? =
        pieces.find { it.coord.row == row && it.coord.col == col }

    fun getPieceAt(coordinate: Coordinate): Piece? =
        pieces.find { it.coord == coordinate }

    fun setPieceAt(row: Int, col: Int, color: Color) =
        Board(size, pieces + Piece(Coordinate(row, col), color))

    fun setPieceAt(coordinate: Coordinate, color: Color) =
        Board(size, pieces + Piece(coordinate, color))
}
