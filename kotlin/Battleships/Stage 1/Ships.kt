package battleship

enum class Ships(private val shipName: String, val length: Int) {
    AIRCRAFT_CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    override fun toString() = "$shipName ($length cells)"
}

enum class Cell(private val character: Char) {
    EMPTY('~'),
    SHIP('O'),
    HIT('X'),
    MISS('M');

    override fun toString() = character.toString()
}
