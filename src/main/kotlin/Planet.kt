class Planet(private val obstacles: Set<Coordinates>) {
    private val height = 10
    private val width = 10

    fun nextLocation(location: Coordinates, direction: Direction): Coordinates = when (direction) {
        Direction.NORTH -> wrapAroundEdges(location.x, location.y + 1)
        Direction.EAST -> wrapAroundEdges(location.x + 1, location.y)
        Direction.SOUTH -> wrapAroundEdges(location.x, location.y - 1)
        Direction.WEST -> wrapAroundEdges(location.x - 1, location.y)
    }

    private fun wrapAroundEdges(x: Int, y: Int) = Coordinates((x + width) % width, (y + height) % height)

    fun hasObstacle(location: Coordinates) = location in obstacles
}
