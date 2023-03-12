import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class MarsRoverTests {
    @Test
    fun returnsCurrentPosition() {
        val game = MarsRover()

        assertThat(game.execute("")).isEqualTo("0:0:N")
    }

    @ParameterizedTest
    @CsvSource(
        "R,0:0:E",
        "RR,0:0:S",
        "RRR,0:0:W",
        "RRRR,0:0:N",
        "RRRRR,0:0:E",
        "L,0:0:W",
        "LL,0:0:S",
        "LLL,0:0:E",
        "LLLL,0:0:N",
        "M, 0:1:N",
        "MM, 0:2:N",
        "MMMM, 0:4:N",
        "RM, 1:0:E",
        "RMLLM, 0:0:W",
        "MMMMMMMMM, 0:9:N",
        "MMMMMMMMMM, 0:0:N",
        "MMLLMM, 0:0:S",
        "LLM, 0:9:S",
        "LM, 9:0:W",
        "RMMMMMMMMMM, 0:0:E",
        "RMMMMMMMMMMMMMMM, 5:0:E"
    )
    fun move(commands: String, expectedPosition: String) {
        val game = MarsRover()

        assertThat(game.execute(commands)).isEqualTo(expectedPosition)
    }

    @ParameterizedTest
    @CsvSource(
        "MMMMM, O:0:4:N",
        "MMMMMM, O:0:4:N",
    )
    fun obstacles(commands: String, expectedPosition: String) {
        val game = MarsRover(obstacles = setOf(0 to 5))

        assertThat(game.execute(commands)).isEqualTo(expectedPosition)
    }

    @ParameterizedTest
    @CsvSource(
        "MMMM, O:0:3:N",
        "RMMMMMM, O:1:0:E",
    )
    fun obstacles2(commands: String, expectedPosition: String) {
        val game = MarsRover(obstacles = setOf(2 to 0, 0 to 4))

        assertThat(game.execute(commands)).isEqualTo(expectedPosition)
    }

}