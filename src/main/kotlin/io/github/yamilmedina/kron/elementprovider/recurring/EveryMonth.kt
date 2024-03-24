package io.github.yamilmedina.kron.elementprovider.recurring

import io.github.yamilmedina.kron.ExpressionElementProvider

internal class EveryMonth : ExpressionElementProvider {

    companion object {
        private const val PATTERN =
            "(monthly|(every|each)\\s(month|january|february|march|april|may|june|july|august|september|october|november|december))"
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
        val regex = Regex(PATTERN, RegexOption.IGNORE_CASE)
        segments = regex.find(string)?.groupValues?.drop(1) ?: emptyList()
        return segments.isNotEmpty()
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
        return "1"
    }

    override fun canProvideMonth(): Boolean {
        return segments.isNotEmpty()
    }

    override fun getMonthElement(): String? {
        return if (segments[0] == "monthly" || (segments.size == 4 && segments[3] == "month")) {
            "*"
        } else {
            MONTH_MAP[segments.getOrNull(2)]?.toString()
        }
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
        return false
    }

    override fun isDayOfWeekElementLocked(): Boolean {
        return false
    }
}

