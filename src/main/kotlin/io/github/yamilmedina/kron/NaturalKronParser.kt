package io.github.yamilmedina.kron

import io.github.yamilmedina.kron.internal.antlr.CronGrammarLexer
import io.github.yamilmedina.kron.internal.antlr.CronGrammarParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

/**
 * The NaturalKronParser interface provides a method to parse natural language expressions into cron expressions.
 * The cron expressions are returned in Quartz syntax.
 * @see <a href="https://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/crontrigger.html#format">Cron Expressions Format- Quartz</a>
 *
 * You can parse for now expressions like:
 * - every workday at 10:15
 * - every saturday at 10:11
 * - every day at 09:00
 */
interface NaturalKronParser {
    /**
     * Parses a natural language expression into a cron expression.
     *
     * @param naturalKronSchedule the natural language expression to parse. e.g. "every workday at 10:15"
     * @return the cron expression in Quartz syntax. e.g. "0 15 10 ? * MON-FRI"
     *
     * @throws KronParsingException if an error occurs while parsing the expression.
     */
    fun parse(naturalKronSchedule: String): String
}

internal fun NaturalKronParser() = object : NaturalKronParser {
    override fun parse(naturalKronSchedule: String): String {
        val lexer = CronGrammarLexer(CharStreams.fromString(naturalKronSchedule))
        val tokens = CommonTokenStream(lexer)
        val parser = CronGrammarParser(tokens)
        val tree = parser.cron()

        return try {
            val cronGenerator = NaturalKronExpressionGenerator()
            cronGenerator.visit(tree)
        } catch (e: Exception) {
            throw KronParsingException("An error occurred while parsing the expression: [$naturalKronSchedule]", e)
        }
    }
}