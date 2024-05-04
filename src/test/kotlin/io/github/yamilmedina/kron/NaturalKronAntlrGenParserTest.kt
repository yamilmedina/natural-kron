package io.github.yamilmedina.kron

import io.github.yamilmedina.kron.antlr.NaturalCronLexer
import io.github.yamilmedina.kron.antlr.NaturalCronParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class NaturalKronAntlrGenParserTest {
    @Test
    fun testAntlrParser_StandardSchedule() {
        val input = CharStreams.fromString("weekly at 15:00")
        val lexer = NaturalCronLexer(input)
        val tokens = CommonTokenStream(lexer)

        val parser = NaturalCronParser(tokens)
        val naturalCron = parser.naturalCron()

        assertNotNull(naturalCron.date().STANDARD_SCHEDULE())
        assertEquals("weekly", naturalCron.date().STANDARD_SCHEDULE().toString())
        assertEquals("15:00", naturalCron.clock().HOUR().toString())
    }

    @Test
    fun testAntlrParser_DynamicScheduleFromNow() {
        val input = CharStreams.fromString("every day at 15:00")
        val lexer = NaturalCronLexer(input)
        val tokens = CommonTokenStream(lexer)

        val parser = NaturalCronParser(tokens)
        val naturalCron = parser.naturalCron()

        assertNotNull(naturalCron.date().DYNAMIC_FROM_NOW_SCHEDULE())
        assertEquals("every day", naturalCron.date().DYNAMIC_FROM_NOW_SCHEDULE().toString())
        assertEquals("15:00", naturalCron.clock().HOUR().toString())
    }

    @Test
    fun testAntlrParser_DynamicScheduleDetail() {
        val input = CharStreams.fromString("every monday at 15:00")
        val lexer = NaturalCronLexer(input)
        val tokens = CommonTokenStream(lexer)

        val parser = NaturalCronParser(tokens)
        val naturalCron = parser.naturalCron()

        assertNotNull(naturalCron.date().DYNAMIC_SCHEDULE())
        assertEquals("every", naturalCron.date().DYNAMIC_SCHEDULE().toString())
        assertEquals("monday", naturalCron.date().DAYS().toString())
        assertEquals("15:00", naturalCron.clock().HOUR().toString())
    }
}
