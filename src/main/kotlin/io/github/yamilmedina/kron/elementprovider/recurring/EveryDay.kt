package io.github.yamilmedina.kron.elementprovider.recurring

import io.github.yamilmedina.kron.ExpressionElementProvider
import java.util.regex.Pattern

internal class EveryDay : ExpressionElementProvider {
    companion object {
        private const val PATTERN =
            "(daily|(every|each|on)\\s(day|monday|tuesday|wednesday|thursday|friday|saturday|sunday)(?s))"
        private val DAY_MAP = mapOf(
            "sunday" to 0,
            "monday" to 1,
            "tuesday" to 2,
            "wednesday" to 3,
            "thursday" to 4,
            "friday" to 5,
            "saturday" to 6
        )
    }

    private var segments: List<String> = emptyList()

    override fun matches(string: String): Boolean {
        val regex = Pattern.compile(PATTERN, Pattern.CASE_INSENSITIVE)
        val matcher = regex.matcher(string)
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
        return "*"
    }

    override fun canProvideMonth(): Boolean {
        return true
    }

    override fun getMonthElement(): String {
        return "*"
    }

    override fun canProvideDayOfWeek(): Boolean {
        return segments.isNotEmpty()
    }

    override fun getDayOfWeekElement(): String? {
        return if (segments[0] == "daily" || (segments.size >= 4 && segments[3] == "day")) {
            null
        } else {
            DAY_MAP[segments.getOrNull(3)]?.toString()
        }
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
        return true
    }
}
