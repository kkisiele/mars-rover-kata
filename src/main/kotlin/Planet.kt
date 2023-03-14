class Planet(private val obstacles: Set<Coordinates>) {
    private val height = 10
    private val width = 10

    fun nextLocation(location: Coordinates, direction: Direction) = when (direction) {
        Direction.NORTH -> Coordinates(location.x, (location.y + 1).wrapAround(height))
        Direction.EAST -> Coordinates((location.x + 1).wrapAround(width), location.y)
        Direction.SOUTH -> Coordinates(location.x, (location.y - 1).wrapAround(height))
        Direction.WEST -> Coordinates((location.x - 1).wrapAround(width), location.y)
    }

    private fun Int.wrapAround(total: Int): Int = (this + total) % total

    fun hasObstacle(location: Coordinates) = location in obstacles
}