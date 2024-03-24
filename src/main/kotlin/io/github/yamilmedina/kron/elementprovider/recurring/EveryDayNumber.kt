package io.github.yamilmedina.kron.elementprovider.recurring

import io.github.yamilmedina.kron.ExpressionElementProvider
import java.util.regex.Pattern

class EveryDayNumber : ExpressionElementProvider {
    companion object {
        private const val PATTERN =
            "(every|each)\\s([0-9]?[0-9])(st|nd|rd|th)\\sof\\s(month|january|february|march|april|may|june|july|august|september|october|november|december)"
        private val MONTH_MAP = mapOf(
            "january" to 1,
            "february" to 2,
            "march" to 3,
            "april" to 4,
            "may" to 5,
            "june" to 6,
            "july" to 7,
            "august" to 8,
            "september" to 9,
            "october" to 10,
            "november" to 11,
            "december" to 12
        )
    }

    private var segments: List<String> = emptyList()

    override fun matches(string: String): Boolean {
        val pattern = Pattern.compile(PATTERN, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(string)
        if (matcher.find()) {
            segments = (0..matcher.groupCount()).map { matcher.group(it) }
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
        return segments[2]
    }

    override fun canProvideMonth(): Boolean {
        return segments.size > 4
    }

    override fun getMonthElement(): String? {
        return MONTH_MAP[segments.getOrNull(4)]?.toString()
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
        return false
    }

    override fun isMonthElementLocked(): Boolean {
        return canProvideMonth()
    }

    override fun isDayOfWeekElementLocked(): Boolean {
        return false
    }
}

