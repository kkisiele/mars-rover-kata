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

    private fun result(rover: Rover): String = Result(rover.location, rover.direction, rover.obstacleEncountered).get()

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
}
