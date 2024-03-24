package io.github.yamilmedina.kron.elementprovider

import io.github.yamilmedina.kron.ExpressionElementProvider
import java.util.regex.Pattern

internal class DayNumber : ExpressionElementProvider {
    companion object {
        private const val PATTERN = "([0-9]?[0-9])(st|nd|rd|th)"
    }

    private var segments: List<String> = emptyList()

    override fun matches(string: String): Boolean {
        val regex = Pattern.compile(PATTERN, Pattern.CASE_INSENSITIVE)
        val matcher = regex.matcher(string)
        if (matcher.find()) {
            segments = (1..matcher.groupCount()).map { matcher.group(it) }
            return true
        }
        return false
    }

    override fun canProvideMinute(): Boolean {
        return true
    }

    override fun getMinuteElement(): String {
        return 0.toString()
    }

    override fun canProvideHour(): Boolean {
        return true
    }

    override fun getHourElement(): String {
        return 0.toString()
    }

    override fun canProvideDayNumber(): Boolean {
        return true
    }

    override fun getDayNumberElement(): String {
        return segments[0]
    }

    override fun canProvideMonth(): Boolean {
        return segments.isNotEmpty()
    }

    override fun getMonthElement(): String {
        return "*"
    }

    override fun canProvideDayOfWeek(): Boolean {
        return true
    }

    override fun getDayOfWeekElement(): String {
        return "*"
    }

    override fun isMinuteElementLocked(): Boolean {
        return false
    }

    override fun isHourElementLocked(): Boolean {
        return false
    }

    override fun isDayNumberElementLocked(): Boolean {
        return true
    }

    override fun isMonthElementLocked(): Boolean {
        return false
    }

    override fun isDayOfWeekElementLocked(): Boolean {
        return false
    }
}


