package io.github.yamilmedina.kron.elementprovider.hour

import io.github.yamilmedina.kron.ExpressionElementProvider

open class Base24Hour : ExpressionElementProvider {

    companion object {
        const val PATTERN = "(2[0-3]|[01]?[0-9]):([0-5]?[0-9])"
    }

    protected var segments: List<String> = listOf()

    override fun matches(string: String): Boolean {
        val regex = Regex(PATTERN, RegexOption.IGNORE_CASE)
        val matches = regex.find(string)
        this.segments = matches?.groupValues?.toList() ?: emptyList()
        return this.segments.isNotEmpty()
    }

    override fun canProvideMinute(): Boolean {
        return segments.size > 2
    }

    override fun getMinuteElement(): String {
        return segments[2].toUInt().toString()
    }

    override fun canProvideHour(): Boolean {
        return segments.size > 1
    }

    override fun getHourElement(): String {
        return segments[1].toUInt().toString()
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
        return canProvideMinute()
    }

    override fun isHourElementLocked(): Boolean {
        return canProvideHour()
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

