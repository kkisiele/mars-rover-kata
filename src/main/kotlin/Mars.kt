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
            if (obstacles.contains(nextPosition.coordinates)) {
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

    private fun result(rover: Rover, obstacle: Boolean = false): String = buildString {
        if (obstacle) {
            append("O:")
        }
        append("${rover.coordinates.x}:${rover.coordinates.y}")
        append(":").append(toString(rover.direction))
    }

    private fun toString(direction: Direction): String = when (direction) {
        NORTH -> "N"
        EAST -> "E"
        SOUTH -> "S"
        WEST -> "W"
    }
}

data class Coordinates(val x: Int, val y: Int)

data class Rover(val coordinates: Coordinates, val direction: Direction) {
    fun rotateRight() = Rover(
        this.coordinates, when (this.direction) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            WEST -> NORTH
        }
    )

    fun rotateLeft() = Rover(
        this.coordinates, when (this.direction) {
            NORTH -> WEST
            WEST -> SOUTH
            SOUTH -> EAST
            EAST -> NORTH
        }
    )

    fun move() = when (this.direction) {
        NORTH -> Rover(Coordinates(coordinates.x, (coordinates.y + 1) % GRID_HEIGHT), this.direction)
        EAST -> Rover(Coordinates((coordinates.x + 1) % GRID_WIDTH, coordinates.y), this.direction)
        SOUTH -> Rover(
            Coordinates(coordinates.x, if (coordinates.y > 0) coordinates.y - 1 else GRID_HEIGHT - 1),
            this.direction
        )
        WEST -> Rover(
            Coordinates(if (coordinates.x > 0) coordinates.x - 1 else GRID_WIDTH - 1, coordinates.y),
            this.direction
        )
    }
}

enum class Direction {
    NORTH, EAST, SOUTH, WEST
}