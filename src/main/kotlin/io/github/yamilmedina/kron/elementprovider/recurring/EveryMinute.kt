package io.github.yamilmedina.kron.elementprovider.recurring

import io.github.yamilmedina.kron.ExpressionElementProvider
import java.util.regex.Pattern

internal class EveryMinute : ExpressionElementProvider {
    companion object {
        private const val PATTERN = "((every|each)?\\s?([0-9]+)?\\s?minute)"
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
        return segments.isNotEmpty()
    }

    override fun getMinuteElement(): String? {
        return if (segments.isNotEmpty()) {
            segments[3]?.let { "*/$it" } ?: "*"
        } else {
            null
        }
    }

    override fun canProvideHour(): Boolean {
        return canProvideMinute()
    }

    override fun getHourElement(): String? {
        return "*"
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
        return true
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


