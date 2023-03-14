class Planet(private val obstacles: Set<Coordinates>) {
    private val height = 10
    private val width = 10

    fun nextLocation(location: Coordinates, direction: Direction): Coordinates {
        val nextLocation = when (direction) {
            Direction.NORTH -> Coordinates(location.x, location.y + 1)
            Direction.EAST -> Coordinates(location.x + 1, location.y)
            Direction.SOUTH -> Coordinates(location.x, location.y - 1)
            Direction.WEST -> Coordinates(location.x - 1, location.y)
        }
        return nextLocation.wrapAroundEdges()
    }

    fun hasObstacle(location: Coordinates) = location in obstacles

    private fun Coordinates.wrapAroundEdges() = Coordinates((this.x + width) % width, (this.y + height) % height)
}
