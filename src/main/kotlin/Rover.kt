data class Rover(val location: Coordinates, val direction: Direction, val obstacleEncountered: Boolean = false) {
    fun rotateRight() = Rover(this.location, this.direction.onRight)

    fun rotateLeft() = Rover(this.location, this.direction.onLeft)

    fun move(grid: Grid): Rover {
        val nextLocation = grid.nextLocation(this.location, this.direction)
        if(grid.hasObstacle(nextLocation)) {
            return this.copy(obstacleEncountered = true)
        }
        return Rover(nextLocation, this.direction)
    }
}