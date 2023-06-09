import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ApplicationTests {
    @Test
    fun emptyCommandReturnsInitialPosition() {
        val sut = Application()

        assertThat(sut.execute("")).isEqualTo("0:0:N")
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
    fun returnsRoverEndPositionInObstacleFreeWorld(commands: String, expectedPosition: String) {
        val sut = Application()

        assertThat(sut.execute(commands)).isEqualTo(expectedPosition)
    }

    @ParameterizedTest
    @CsvSource(
        "MMMMM, O:0:4:N",
        "MMMMMM, O:0:4:N",
        "RMMMMMM, O:1:0:E",
    )
    fun movesTillObstacleEncountered(commands: String, expectedPosition: String) {
        val sut = Application(obstacles = setOf(Coordinates(0, 5), Coordinates(2, 0)))

        assertThat(sut.execute(commands)).isEqualTo(expectedPosition)
    }
}