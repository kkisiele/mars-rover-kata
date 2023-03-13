class Planet(private val obstacles: Set<Coordinates>) {
    private val height = 10
    private val width = 10

    fun nextLocation(location: Coordinates, direction: Direction) = when (direction) {
        Direction.NORTH -> Coordinates(location.x, (location.y + 1) % height)
        Direction.EAST -> Coordinates((location.x + 1) % width, location.y)
        Direction.SOUTH -> Coordinates(location.x, if (location.y > 0) location.y - 1 else height - 1)
        Direction.WEST -> Coordinates(if (location.x > 0) location.x - 1 else width - 1, location.y)
    }

    fun hasObstacle(location: Coordinates) = location in obstacles
}