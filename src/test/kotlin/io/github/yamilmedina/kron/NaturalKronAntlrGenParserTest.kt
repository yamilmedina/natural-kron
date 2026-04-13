package io.github.yamilmedina.kron

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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
        val ex = assertThrows<KronParsingException> {
            val input = "every sasaturday"
            NaturalKronParser().parse(input)
        }
        assertEquals(FailureType.LEXICAL, ex.failureType)
    }

    @Test
    fun givenEveryDayAtTime_whenParsing_ShouldReturnCorrectFormat() {
        assertEquals("0 0 9 ? * *", NaturalKronParser().parse("every day at 09:00"))
    }

    @Test
    fun givenDailyAlias_whenParsing_ShouldReturnCorrectFormat() {
        assertEquals("0 30 8 ? * *", NaturalKronParser().parse("every daily at 08:30"))
    }

    @Test
    fun givenEverydayAlias_whenParsing_ShouldReturnCorrectFormat() {
        assertEquals("0 45 14 ? * *", NaturalKronParser().parse("every everyday at 14:45"))
    }

    @Test
    fun givenWeekdayAlias_whenParsing_ShouldReturnCorrectFormat() {
        assertEquals("0 15 10 ? * MON-FRI", NaturalKronParser().parse("every weekday at 10:15"))
    }

    @Test
    fun givenWeekdaysAlias_whenParsing_ShouldReturnCorrectFormat() {
        assertEquals("0 0 9 ? * MON-FRI", NaturalKronParser().parse("every weekdays at 9:00"))
    }

    @Test
    fun givenShortDayName_whenParsing_ShouldReturnCorrectFormat() {
        assertEquals("0 5 8 ? * MON", NaturalKronParser().parse("every mon at 8:05"))
    }

    @Test
    fun givenMixedCaseInput_whenParsing_ShouldReturnCorrectFormat() {
        assertEquals("0 15 10 ? * SAT", NaturalKronParser().parse("Every Saturday at 10:15"))
    }

    @Test
    fun givenUppercaseInput_whenParsing_ShouldReturnCorrectFormat() {
        assertEquals("0 15 10 ? * MON-FRI", NaturalKronParser().parse("EVERY WORKDAY AT 10:15"))
    }

    @Test
    fun givenExtraWhitespace_whenParsing_ShouldReturnCorrectFormat() {
        assertEquals("0 15 10 ? * MON", NaturalKronParser().parse("  every   monday   at   10:15  "))
    }

    @Test
    fun givenSingleDigitHour_whenParsing_ShouldReturnCorrectFormat() {
        assertEquals("0 5 8 ? * MON", NaturalKronParser().parse("every monday at 8:05"))
    }

    @Test
    fun givenInvalidHour24_whenParsing_ShouldRaiseASemanticError() {
        val ex = assertThrows<KronParsingException> {
            NaturalKronParser().parse("every day at 24:00")
        }
        assertEquals(FailureType.SEMANTIC, ex.failureType)
    }

    @Test
    fun givenInvalidHour27_whenParsing_ShouldRaiseASemanticError() {
        val ex = assertThrows<KronParsingException> {
            NaturalKronParser().parse("every day at 27:15")
        }
        assertEquals(FailureType.SEMANTIC, ex.failureType)
    }

    @Test
    fun givenInvalidMinute60_whenParsing_ShouldRaiseASemanticError() {
        val ex = assertThrows<KronParsingException> {
            NaturalKronParser().parse("every day at 10:60")
        }
        assertEquals(FailureType.SEMANTIC, ex.failureType)
    }

    @Test
    fun givenPartialInput_whenParsing_ShouldRaiseAnError() {
        val ex = assertThrows<KronParsingException> {
            NaturalKronParser().parse("every monday")
        }
        assertEquals(FailureType.SYNTACTIC, ex.failureType)
    }

    @Test
    fun givenUnknownSynonym_whenParsing_ShouldRaiseAnError() {
        val ex = assertThrows<KronParsingException> {
            NaturalKronParser().parse("every sameday at 10:00")
        }
        assertEquals(FailureType.LEXICAL, ex.failureType)
    }

    @Test
    fun givenTrailingJunk_whenParsing_ShouldRaiseAnError() {
        val ex = assertThrows<KronParsingException> {
            NaturalKronParser().parse("every monday at 10:00 extra")
        }
        assertTrue(ex.failureType == FailureType.LEXICAL || ex.failureType == FailureType.SYNTACTIC)
    }

    @Test
    fun givenUnsupportedAmPmInput_whenParsing_ShouldRaiseAnError() {
        val ex = assertThrows<KronParsingException> {
            NaturalKronParser().parse("every monday at 10pm")
        }
        assertTrue(ex.failureType == FailureType.LEXICAL || ex.failureType == FailureType.SYNTACTIC)
    }

    @Test
    fun givenUnsupportedIntervalInput_whenParsing_ShouldRaiseAnError() {
        val ex = assertThrows<KronParsingException> {
            NaturalKronParser().parse("every 5 minutes")
        }
        assertTrue(ex.failureType == FailureType.LEXICAL || ex.failureType == FailureType.SYNTACTIC)
    }
}
