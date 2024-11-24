package io.github.yamilmedina.kron

import io.github.yamilmedina.kron.internal.antlr.CronGrammarLexer
import io.github.yamilmedina.kron.internal.antlr.CronGrammarParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.junit.jupiter.api.Test

class NaturalKronAntlrGenParserTest {

//    @Nested
//    inner class StandardScheduleTestCases {
//        @Test
//        fun givenAStandardSchedule_whenParsing_ShouldReturnCorrectFormat() {
//            val input = CharStreams.fromString("weekly at 15:00")
//            val lexer = NaturalCronLexer(input)
//            val tokens = CommonTokenStream(lexer)
//
//            val parser = NaturalCronParser(tokens)
//            val naturalCron = parser.naturalCron()
//
//            assertNotNull(naturalCron.date().STANDARD_SCHEDULE())
//            assertEquals("weekly", naturalCron.date().STANDARD_SCHEDULE().toString())
//            assertEquals("15:00", naturalCron.clock().FULL_HOUR().toString())
//        }
//    }
//
//    @Nested
//    inner class DynamicScheduleTestCases {
//        @Test
//        fun givenADynamicSchedule_whenParsingNoDaySpecified_ShouldReturnEveryFormat() {
//            val input = CharStreams.fromString("every day at 15:00")
//            val lexer = NaturalCronLexer(input)
//            val tokens = CommonTokenStream(lexer)
//
//            val parser = NaturalCronParser(tokens)
//            val naturalCron = parser.naturalCron()
//
//            assertNotNull(naturalCron.date().DYNAMIC_FROM_NOW_SCHEDULE())
//            assertEquals("every day", naturalCron.date().DYNAMIC_FROM_NOW_SCHEDULE().toString())
//            assertEquals("15:00", naturalCron.clock().FULL_HOUR().toString())
//        }
//
//        @Test
//        fun givenADynamicSchedule_whenParsing_ShouldReturnCorrectFormat() {
//            val input = CharStreams.fromString("every monday at 15:00")
//            val lexer = NaturalCronLexer(input)
//            val tokens = CommonTokenStream(lexer)
//
//            val parser = NaturalCronParser(tokens)
//            val naturalCron = parser.naturalCron()
//
//            assertNotNull(naturalCron.date().DYNAMIC_SCHEDULE())
//            assertEquals("every", naturalCron.date().DYNAMIC_SCHEDULE().toString())
//            assertEquals("monday", naturalCron.date().DAYS().toString())
//            assertEquals("15:00", naturalCron.clock().FULL_HOUR().toString())
//        }
//
//        @Test
//        fun givenAnInputWithMixedCase_whenParsing_ShouldReturnCorrectFormat() {
//            val input = CharStreams.fromString("eVery Monday at 15:00")
//            val lexer = NaturalCronLexer(input)
//            val tokens = CommonTokenStream(lexer)
//
//            val parser = NaturalCronParser(tokens)
//            val naturalCron = parser.naturalCron()
//
//            assertNotNull(naturalCron.date().DYNAMIC_SCHEDULE())
//            assertEquals("every", naturalCron.date().DYNAMIC_SCHEDULE().toString().lowercase())
//            assertEquals("Monday", naturalCron.date().DAYS().toString())
//            assertEquals("15:00", naturalCron.clock().FULL_HOUR().toString())
//        }
//    }
//
//    @Nested
//    inner class HourScheduleTestCases {
//        @Test
//        fun givenAShortHourStyle_whenParsing_ShouldReturnCorrectFormat() {
//            val input = CharStreams.fromString("every Monday at 15hrs")
//            val lexer = NaturalCronLexer(input)
//            val tokens = CommonTokenStream(lexer)
//
//            val parser = NaturalCronParser(tokens)
//            val naturalCron = parser.naturalCron()
//
//            assertNotNull(naturalCron.date().DYNAMIC_SCHEDULE())
//            assertEquals("every", naturalCron.date().DYNAMIC_SCHEDULE().toString().lowercase())
//            assertEquals("Monday", naturalCron.date().DAYS().toString())
//            assertEquals("15hrs", naturalCron.clock().SHORT_HOUR().toString())
//        }
//    }

    @Test
    fun testParser() {
        val input = "every workday at 10:15" // Example input
        val lexer = CronGrammarLexer(CharStreams.fromString(input))
        val tokens = CommonTokenStream(lexer)
        val parser = CronGrammarParser(tokens)
        val tree = parser.cron()

        val cronGenerator = NaturalKronExpressionGenerator()
        val cronExpression = cronGenerator.visit(tree)

        println(cronExpression) // Output the cron expression
    }
    @Test
    fun testParser2() {
        val input = "every saturday at 10:11" // Example input
        val lexer = CronGrammarLexer(CharStreams.fromString(input))
        val tokens = CommonTokenStream(lexer)
        val parser = CronGrammarParser(tokens)
        val tree = parser.cron()

        val cronGenerator = NaturalKronExpressionGenerator()
        val cronExpression = cronGenerator.visit(tree)

        println(cronExpression) // Output the cron expression
    }

}
