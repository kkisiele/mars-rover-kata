data class Rover(val location: Coordinates, val direction: Direction, val obstacleEncountered: Boolean = false) {
    fun rotateRight() = this.copy(direction =  direction.onRight)

    fun rotateLeft() = this.copy(direction = direction.onLeft)

    fun move(planet: Planet): Rover {
        val nextLocation = planet.nextLocation(this.location, this.direction)
        if (planet.hasObstacle(nextLocation)) {
            return this.copy(obstacleEncountered = true)
        }
        return this.copy(location = nextLocation)
    }
}