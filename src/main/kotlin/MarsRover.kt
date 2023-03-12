import Direction.EAST
import Direction.NORTH
import Direction.SOUTH
import Direction.WEST


class MarsRover(val obstacles: Set<Pair<Int, Int>> = emptySet()) {
    private val GRID_HEIGHT = 10
    private val GRID_WIDTH = 10

    fun execute(commands: String): String {
        var position = Position(0, 0, NORTH)
        for (c in commands) {
            val newPosition = execute(position, c)
            if(obstacles.contains(newPosition.x to newPosition.y)) {
                return display(position, obstacle = true)
            }
            position = newPosition
        }
        return display(position)
    }

    private fun display(position: Position, obstacle: Boolean = false): String {
        var d = display(position.direction)
        if(obstacle) {
            return "O:${position.x}:${position.y}:$d"
        }
        return "${position.x}:${position.y}:$d"
    }

    private fun display(direction: Direction) = when (direction) {
        NORTH -> "N"
        EAST -> "E"
        SOUTH -> "S"
        WEST -> "W"
    }

    private fun execute(position: Position, command: Char): Position = when (command) {
        'R' -> rotatedRight(position)
        'L' -> rotatedLeft(position)
        'M' -> moved(position)
        else -> throw UnsupportedOperationException("Invalid command $command")
    }

    private fun moved(position: Position) = when (position.direction) {
        NORTH -> Position(position.x, (position.y + 1) % GRID_HEIGHT, position.direction)
        EAST -> Position((position.x + 1) % GRID_WIDTH, position.y, position.direction)
        SOUTH -> Position(position.x, if (position.y > 0) position.y - 1 else GRID_HEIGHT - 1, position.direction)
        WEST -> Position(if (position.x > 0) position.x - 1 else GRID_WIDTH - 1, position.y, position.direction)
    }

    private fun rotatedRight(position: Position) = Position(
        position.x, position.y, when (position.direction) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            WEST -> NORTH
        }
    )

    private fun rotatedLeft(position: Position) = Position(
        position.x, position.y, when (position.direction) {
            NORTH -> WEST
            WEST -> SOUTH
            SOUTH -> EAST
            EAST -> NORTH
        }
    )
}

data class Position(val x: Int, val y: Int, val direction: Direction)

enum class Direction {
    NORTH, EAST, SOUTH, WEST
}