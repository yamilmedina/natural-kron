package io.github.yamilmedina.kron

import io.github.yamilmedina.kron.antlr.NaturalCronLexer
import io.github.yamilmedina.kron.antlr.NaturalCronParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class NaturalKronAntlrGenParserTest {
    @Test
    fun testAntlrParser() {
        val input = CharStreams.fromString("weekly at 15:00 ")
        val lexer = NaturalCronLexer(input)
        val tokens = CommonTokenStream(lexer)

        val parser = NaturalCronParser(tokens)
        val naturalCron = parser.naturalCron()

        assertEquals("weekly", naturalCron.date().DAYS().toString())
        assertEquals("15:00", naturalCron.clock().HOUR().toString())
    }
}
