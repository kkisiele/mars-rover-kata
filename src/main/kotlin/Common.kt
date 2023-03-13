data class Coordinates(val x: Int, val y: Int)

enum class Direction {
    NORTH, EAST, SOUTH, WEST;

    val onLeft: Direction
        get() = neighbours(this).first

    val onRight: Direction
        get() = neighbours(this).second

    private fun neighbours(direction: Direction): Pair<Direction, Direction> = when (direction) {
        NORTH -> Pair(WEST, EAST)
        EAST -> Pair(NORTH, SOUTH)
        SOUTH -> Pair(EAST, WEST)
        WEST -> Pair(SOUTH, NORTH)
    }
}