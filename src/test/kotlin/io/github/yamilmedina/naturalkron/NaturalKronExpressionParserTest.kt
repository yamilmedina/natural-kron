package io.github.yamilmedina.naturalkron

import java.time.DayOfWeek
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class NaturalKronExpressionParserTest {

    @Test
    fun `given every monday is parsed, then returns every monday expression`() {
        val expression = "every monday"
        val parsed = NaturalKronExpressionParser().parse(expression)

        assertEquals(expression, parsed.toNaturalLanguage()?.lowercase())
        assertEquals(DayOfWeek.MONDAY.value.toString(), parsed.dayOfWeek)
    }

    @Test
    fun `given every friday at 1015am is parsed, then returns every monday expression`() {
        val expression = "every friday at 10:15am"
        val parsed = NaturalKronExpressionParser().parse(expression)

        val expectedKronExpressionEveryFridayAt1015am = "0 15 10 * * 5"
        assertEquals(DayOfWeek.FRIDAY.value.toString(), parsed.dayOfWeek)
        assertEquals(expectedKronExpressionEveryFridayAt1015am, parsed.toString())
    }

    @Test
    fun `given every day at 9am is parsed, then returns every day expression with date`() {
        val expression = "every day at 9am"
        val parsed = NaturalKronExpressionParser().parse(expression)

        val expectedKronExpressionEveryFridayAt9am = "0 0 9 * * *"
        assertEquals("*", parsed.dayOfWeek)
        assertEquals(expectedKronExpressionEveryFridayAt9am, parsed.toString())
    }

    @Test
    fun `given an invalid expression, then raise KronException`() {
        val invalidExpression = "invalid expression"
        assertThrows<KronParserException> {
            NaturalKronExpressionParser().parse(invalidExpression)
        }
    }

    @Test
    fun `given a partial invalid expression, then parse valid part`() {
        val invalidExpression = "invalid at 1st of the month"
        assertDoesNotThrow {
            val parsed = NaturalKronExpressionParser().parse(invalidExpression)
            assertEquals("0 0 0 1 * *", parsed.toString())
        }
    }

}