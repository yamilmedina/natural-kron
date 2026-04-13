package io.github.yamilmedina.kron

import io.github.yamilmedina.kron.internal.antlr.CronGrammarBaseVisitor
import io.github.yamilmedina.kron.internal.antlr.CronGrammarParser

internal class ScheduleVisitor : CronGrammarBaseVisitor<NormalizedSchedule>() {

    override fun visitSchedule(ctx: CronGrammarParser.ScheduleContext): NormalizedSchedule {
        val daySelector = ctx.daySelector().text.lowercase()
        val time = ctx.timeClause().text
        val hour = time.substringBefore(':').toInt()
        val minute = time.substringAfter(':').toInt()

        return when (daySelector) {
            "workday", "weekday", "weekdays" -> NormalizedSchedule(
                dayKind = DayKind.WEEKDAY,
                hour = hour,
                minute = minute
            )

            "day", "daily", "everyday" -> NormalizedSchedule(
                dayKind = DayKind.EVERY_DAY,
                hour = hour,
                minute = minute
            )

            else -> NormalizedSchedule(
                dayKind = DayKind.SPECIFIC_DAY,
                specificDay = mapDay(daySelector),
                hour = hour,
                minute = minute
            )
        }
    }

    private fun mapDay(day: String): String = when (day) {
        "monday", "mon" -> "MON"
        "tuesday", "tue" -> "TUE"
        "wednesday", "wed" -> "WED"
        "thursday", "thu" -> "THU"
        "friday", "fri" -> "FRI"
        "saturday", "sat" -> "SAT"
        "sunday", "sun" -> "SUN"
        else -> throw IllegalArgumentException("Invalid day selector: $day")
    }
}
