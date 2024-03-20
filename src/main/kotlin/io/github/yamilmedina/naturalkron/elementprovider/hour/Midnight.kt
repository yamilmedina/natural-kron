package  io.github.yamilmedina.naturalkron.elementprovider.hour

import  io.github.yamilmedina.naturalkron.ExpressionElementProvider
import java.util.*


internal open class Midnight : ExpressionElementProvider {
    protected var match: Boolean = false

    override fun matches(value: String): Boolean {
        match = value != null && value.lowercase(Locale.getDefault()).indexOf("midnight") >= 0
        return match
    }

    override fun canProvideMinute(): Boolean {
        return match
    }

    override val minuteElement: String
        get() = "0"

    override fun canProvideHour(): Boolean {
        return match
    }

    override val hourElement: String
        get() = "0"

    override fun canProvideDayNumber(): Boolean {
        return false
    }

    override val dayNumberElement: String?
        get() = null

    override fun canProvideMonth(): Boolean {
        return false
    }

    override val monthElement: String?
        get() = null

    override fun canProvideDayOfWeek(): Boolean {
        return false
    }

    override val dayOfWeekElement: String?
        get() = null

    override val isMinuteElementLocked: Boolean
        get() = canProvideMinute()

    override val isHourElementLocked: Boolean
        get() = canProvideHour()

    override val isDayNumberElementLocked: Boolean
        get() = false

    override val isMonthElementLocked: Boolean
        get() = false

    override val isDayOfWeekElementLocked: Boolean
        get() = false
}
