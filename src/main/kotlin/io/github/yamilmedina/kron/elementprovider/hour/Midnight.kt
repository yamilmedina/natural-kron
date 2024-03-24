package io.github.yamilmedina.kron.elementprovider.hour

import io.github.yamilmedina.kron.ExpressionElementProvider

class Midnight : ExpressionElementProvider {
    private var match: Boolean = false

    override fun matches(string: String): Boolean {
        match = string.contains("midnight")
        return match
    }

    override fun canProvideMinute(): Boolean = match

    override fun getMinuteElement(): String = 0.toString()

    override fun canProvideHour(): Boolean = match

    override fun getHourElement(): String = 0.toString()

    override fun canProvideDayNumber(): Boolean = false

    override fun getDayNumberElement(): String? = null

    override fun canProvideMonth(): Boolean = false

    override fun getMonthElement(): String? = null

    override fun canProvideDayOfWeek(): Boolean = false

    override fun getDayOfWeekElement(): String? = null

    override fun isMinuteElementLocked(): Boolean = canProvideMinute()

    override fun isHourElementLocked(): Boolean = canProvideHour()

    override fun isDayNumberElementLocked(): Boolean = false

    override fun isMonthElementLocked(): Boolean = false

    override fun isDayOfWeekElementLocked(): Boolean = false
}


