package io.github.yamilmedina.kron

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class NaturalKronAntlrGenParserTest {

    @Test
    fun givenAWorkingDaySchedule_whenParsing_ShouldReturnCorrectFormat() {
        val input = "every workday at 10:15"
        val cronExpression = NaturalKronParser().parse(input)
        assertEquals("0 15 10 ? * MON-FRI", cronExpression)
    }

    @Test
    fun givenASingleDaySchedule_whenParsing_ShouldReturnCorrectFormat() {
        val input = "every saturday at 10:11"
        val cronExpression = NaturalKronParser().parse(input)
        assertEquals("0 11 10 ? * SAT", cronExpression)
    }

    @Test
    fun givenAnInvalidScheduleFormat_whenParsing_ShouldRaiseAnError() {
        assertThrows<KronParsingException> {
            val input = "every sasaturday" // Example input
            NaturalKronParser().parse(input)
        }
    }

}
