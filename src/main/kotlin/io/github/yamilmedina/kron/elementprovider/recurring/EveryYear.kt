package io.github.yamilmedina.kron.elementprovider.recurring

import io.github.yamilmedina.kron.ExpressionElementProvider

class EveryYear : ExpressionElementProvider {

    companion object {
        private const val PATTERN = "(yearly|annually|(every|each) ?([0-9]+)?year)"
    }

    private var segments: List<String> = emptyList()

    override fun matches(string: String): Boolean {
        val regex = Regex(PATTERN, RegexOption.IGNORE_CASE)
        segments = regex.find(string)?.groupValues ?: emptyList()
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
        return 1.toString()
    }

    override fun canProvideMonth(): Boolean {
        return true
    }

    override fun getMonthElement(): String {
        return 1.toString()
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

