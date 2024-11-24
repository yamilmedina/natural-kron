package io.github.yamilmedina.kron

import io.github.yamilmedina.kron.internal.antlr.CronGrammarBaseVisitor
import io.github.yamilmedina.kron.internal.antlr.CronGrammarParser
import org.antlr.v4.runtime.tree.ParseTree

/**
 * Maps natural language expressions to cron expressions in Quartz syntax.
 *```
 * <second> <minute> <hour> <day of the month> <month> <day of the week> <year>
 * ```
 *
 * Note: Quartz cron only supports a value in either the 4th or the 6th position, but not in both. At the same time, both positions cannot be empty.
 *
 * @see <a href="https://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/crontrigger.html#format">Cron Expressions Format- Quartz</a>
 */
class NaturalKronExpressionGenerator : CronGrammarBaseVisitor<String>() {

    override fun visit(tree: ParseTree?): String {
        val result: String? = super.visit(tree)
        return result ?: throw IllegalArgumentException("Invalid expression: ${tree?.text}")
    }

    override fun visitEveryWorkday(ctx: CronGrammarParser.EveryWorkdayContext): String {
        val time = ctx.TIME().text.split(":")
        val minutes = time[1].toInt()
        val hours = time[0].toInt()
        return "0 $minutes $hours ? * MON-FRI"
    }

    override fun visitEverySpecificDay(ctx: CronGrammarParser.EverySpecificDayContext): String {
        val time = ctx.TIME().text.split(":")
        val minutes = time[1].toInt()
        val hours = time[0].toInt()
        val day = mapDay(ctx.SPECIFIC_DAY().text)
        return "0 $minutes $hours ? * $day"
    }

    private fun mapDay(day: String): String {
        return when (day) {
            "monday" -> "MON"
            "tuesday" -> "TUE"
            "wednesday" -> "WED"
            "thursday" -> "THU"
            "friday" -> "FRI"
            "saturday" -> "SAT"
            "sunday" -> "SUN"
            "day" -> "*"
            else -> throw IllegalArgumentException("Invalid day")
        }
    }
}