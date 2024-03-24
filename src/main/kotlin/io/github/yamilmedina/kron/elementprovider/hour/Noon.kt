package io.github.yamilmedina.kron.elementprovider.hour

import io.github.yamilmedina.kron.ExpressionElementProvider

internal class Noon : ExpressionElementProvider {
    private var match: Boolean = false

    override fun matches(string: String): Boolean {
        match = string.contains("noon")
        return match
    }

    override fun canProvideMinute(): Boolean {
        return match
    }

    override fun getMinuteElement(): String {
        return 0.toString()
    }

    override fun canProvideHour(): Boolean {
        return match
    }

    override fun getHourElement(): String {
        return 12.toString()
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


