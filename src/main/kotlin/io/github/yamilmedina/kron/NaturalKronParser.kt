package io.github.yamilmedina.kron

import io.github.yamilmedina.kron.internal.StrictErrorListener
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

fun NaturalKronParser() = object : NaturalKronParser {
    override fun parse(naturalKronSchedule: String): String {
        val input = naturalKronSchedule.trim()
        val lexer = CronGrammarLexer(CharStreams.fromString(input))
        val lexerErrorListener = StrictErrorListener()
        lexer.removeErrorListeners()
        lexer.addErrorListener(lexerErrorListener)

        val tokens = CommonTokenStream(lexer)
        val parser = CronGrammarParser(tokens)
        val parserErrorListener = StrictErrorListener()
        parser.removeErrorListeners()
        parser.addErrorListener(parserErrorListener)

        val tree = parser.cron()

        lexerErrorListener.error()?.let {
            throw KronParsingException.lexical(input, it)
        }
        parserErrorListener.error()?.let {
            throw KronParsingException.syntactic(input, it)
        }

        return try {
            val schedule = ScheduleVisitor().visit(tree.schedule())
                ?: throw KronParsingException.syntactic(input, "Could not parse schedule")
            validateTime(input, schedule)
            QuartzCronGenerator.generate(schedule)
        } catch (e: KronParsingException) {
            throw e
        } catch (e: Exception) {
            throw KronParsingException.semantic(
                input,
                e.message ?: "An unknown error occurred while generating the cron expression",
                e
            )
        }
    }

    private fun validateTime(input: String, schedule: NormalizedSchedule) {
        if (schedule.hour !in 0..23) {
            throw KronParsingException.semantic(input, "Invalid hour: ${schedule.hour}. Expected a value between 0 and 23.")
        }
        if (schedule.minute !in 0..59) {
            throw KronParsingException.semantic(input, "Invalid minute: ${schedule.minute}. Expected a value between 0 and 59.")
        }
    }
}
