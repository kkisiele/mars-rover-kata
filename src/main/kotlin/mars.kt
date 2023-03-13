import Direction.EAST
import Direction.NORTH
import Direction.SOUTH
import Direction.WEST

private const val GRID_HEIGHT = 10
private const val GRID_WIDTH = 10

class Mars(val obstacles: Set<Coordinates> = emptySet()) {

    fun execute(commands: String): String {
        var position = Rover(Coordinates(0, 0), NORTH)
        for (command in commands) {
            val nextPosition = execute(command, position)
            if (obstacles.contains(nextPosition.location)) {
                return result(position, obstacle = true)
            }
            position = nextPosition
        }
        return result(position)
    }

    private fun execute(command: Char, rover: Rover): Rover = when (command) {
        'R' -> rover.rotateRight()
        'L' -> rover.rotateLeft()
        'M' -> rover.move()
        else -> throw UnsupportedOperationException("Invalid command $command")
    }
}

private fun result(rover: Rover, obstacle: Boolean = false): String =
    Result(rover.location, rover.direction, obstacle).get()

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

private data class Rover(val location: Coordinates, val direction: Direction) {
    fun rotateRight() = Rover(this.location, this.direction.onRight)

    fun rotateLeft() = Rover(this.location, this.direction.onLeft)

    fun move() = when (this.direction) {
        NORTH -> Rover(Coordinates(location.x, (location.y + 1) % GRID_HEIGHT), this.direction)
        EAST -> Rover(Coordinates((location.x + 1) % GRID_WIDTH, location.y), this.direction)
        SOUTH -> Rover(
            Coordinates(location.x, if (location.y > 0) location.y - 1 else GRID_HEIGHT - 1),
            this.direction
        )
        WEST -> Rover(
            Coordinates(if (location.x > 0) location.x - 1 else GRID_WIDTH - 1, location.y),
            this.direction
        )
    }
}

private enum class Direction {
    NORTH, EAST, SOUTH, WEST;

    val onLeft: Direction
        get() = neighbours(this).first

    val onRight: Direction
        get() = neighbours(this).second

    private fun neighbours(direction: Direction): Pair<Direction, Direction> = when(direction) {
        NORTH -> Pair(WEST, EAST)
        EAST -> Pair(NORTH, SOUTH)
        SOUTH -> Pair(EAST, WEST)
        WEST -> Pair(SOUTH, NORTH)
    }
}