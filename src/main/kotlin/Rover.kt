data class Rover(val location: Coordinates, val direction: Direction, val obstacleEncountered: Boolean = false) {
    fun rotateRight() = copy(direction =  direction.onRight)

    fun rotateLeft() = copy(direction = direction.onLeft)

    fun move(planet: Planet): Rover {
        val nextLocation = planet.nextLocation(location, direction)
        if (planet.hasObstacle(nextLocation)) {
            return copy(obstacleEncountered = true)
        }
        return copy(location = nextLocation)
    }
}