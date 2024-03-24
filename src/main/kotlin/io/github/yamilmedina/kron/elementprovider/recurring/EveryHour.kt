package io.github.yamilmedina.kron.elementprovider.recurring

import io.github.yamilmedina.kron.ExpressionElementProvider
import java.util.regex.Pattern

internal class EveryHour : ExpressionElementProvider {
    companion object {
        private const val PATTERN = "(hourly|(every|each)?\\s?([0-9]+)?\\shour)"
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
        return false
    }

    override fun getMinuteElement(): String? {
        return null
    }

    override fun canProvideHour(): Boolean {
        return segments.isNotEmpty()
    }

    override fun getHourElement(): String? {
        return if (segments.isNotEmpty()) {
            segments[3]?.let { "*/$it" } ?: "*"
        } else {
            null
        }
    }

    override fun canProvideDayNumber(): Boolean {
        return false
    }

    override fun getDayNumberElement(): String? {
        return null
    }

    override fun canProvideMonth(): Boolean {
        return false
    }

    override fun getMonthElement(): String? {
        return null
    }

    override fun canProvideDayOfWeek(): Boolean {
        return false
    }

    override fun getDayOfWeekElement(): String? {
        return null
    }

    override fun isMinuteElementLocked(): Boolean {
        return false
    }

    override fun isHourElementLocked(): Boolean {
        return true
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


