import Direction.EAST
import Direction.NORTH
import Direction.SOUTH
import Direction.WEST


class Mars(val obstacles: Set<Coordinates> = emptySet()) {
    private val grid = Grid(obstacles)

    fun execute(commands: String): String {
        var rover = Rover(Coordinates(0, 0), NORTH)
        for (command in commands) {
            rover = execute(command, rover)
            if (rover.obstacleEncountered) {
                break
            }
        }
        return result(rover)
    }

    private fun execute(command: Char, rover: Rover): Rover = when (command) {
        'R' -> rover.rotateRight()
        'L' -> rover.rotateLeft()
        'M' -> rover.move(grid)
        else -> throw UnsupportedOperationException("Invalid command $command")
    }
}

private class Grid(private val obstacles: Set<Coordinates>) {
    private val height = 10
    private val width = 10

    fun nextLocation(location: Coordinates, direction: Direction) = when (direction) {
        NORTH -> Coordinates(location.x, (location.y + 1) % height)
        EAST -> Coordinates((location.x + 1) % width, location.y)
        SOUTH -> Coordinates(location.x, if (location.y > 0) location.y - 1 else height - 1)
        WEST -> Coordinates(if (location.x > 0) location.x - 1 else width - 1, location.y)
    }

    fun hasObstacle(location: Coordinates) = obstacles.contains(location)
}

private fun result(rover: Rover): String =
    Result(rover.location, rover.direction, rover.obstacleEncountered).get()

private data class Result(val coordinates: Coordinates, val direction: Direction, val obstacle: Boolean) {
    fun get(): String = buildString {
        if (obstacle) {
            append("O:")
        }
        append("${coordinates.x}:${coordinates.y}")
        append(":").append(toString(direction))
    }

    private fun toString(direction: Direction): String = when (direction) {
        NORTH -> "N"
        EAST -> "E"
        SOUTH -> "S"
        WEST -> "W"
    }
}

data class Coordinates(val x: Int, val y: Int)

private data class Rover(val location: Coordinates, val direction: Direction, val obstacleEncountered: Boolean = false) {
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

private enum class Direction {
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