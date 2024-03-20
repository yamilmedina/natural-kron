package  io.github.yamilmedina.naturalkron.elementprovider.hour

import  io.github.yamilmedina.naturalkron.ExpressionElementProvider
import java.util.regex.Pattern

internal open class Base24Hour : ExpressionElementProvider {

    @JvmField
    protected var segments: MutableList<String> = mutableListOf()

    override fun matches(value: String): Boolean {
        val m = pattern.matcher(value)
        while (m.find()) {
            for (i in 0..m.groupCount()) {
                if (m.group(i) != null) {
                    segments.add(m.group(i))
                }
            }
        }
        return segments.size > 0
    }

    override fun canProvideMinute(): Boolean {
        return segments.size > 2
    }

    override val minuteElement: String?
        get() = if (segments.size > 2) segments[2].toInt().toString() else null

    override fun canProvideHour(): Boolean {
        return segments.size > 1
    }

    override val hourElement: String?
        get() = if (segments.size > 1) segments[1].toInt().toString() else null

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

    companion object {
        private const val PATTERN = "(2[0-3]|[01]?[0-9]):([0-5]?[0-9])"
        private val pattern: Pattern = Pattern.compile(PATTERN, Pattern.CASE_INSENSITIVE)
    }
}
