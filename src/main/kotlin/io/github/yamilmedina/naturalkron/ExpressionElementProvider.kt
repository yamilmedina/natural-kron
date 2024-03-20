package  io.github.yamilmedina.naturalkron

internal interface ExpressionElementProvider {

    fun matches(value: String): Boolean

    fun canProvideSecond(): Boolean {
        return false
    }

    val secondElement: String?
        get() = null

    fun canProvideMinute(): Boolean

    val minuteElement: String?

    fun canProvideHour(): Boolean

    val hourElement: String?

    fun canProvideDayNumber(): Boolean

    val dayNumberElement: String?

    fun canProvideMonth(): Boolean

    val monthElement: String?

    fun canProvideDayOfWeek(): Boolean

    val dayOfWeekElement: String?

    val isSecondElementLocked: Boolean
        get() = false

    val isMinuteElementLocked: Boolean

    val isHourElementLocked: Boolean

    val isDayNumberElementLocked: Boolean

    val isMonthElementLocked: Boolean

    val isDayOfWeekElementLocked: Boolean
}
