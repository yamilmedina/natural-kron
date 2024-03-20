package  io.github.yamilmedina.naturalkron.elementprovider

import  io.github.yamilmedina.naturalkron.ExpressionElementProvider
import java.util.regex.Pattern

internal class DayNumber : ExpressionElementProvider {
    private val segments: MutableList<String> = mutableListOf()

    override fun matches(value: String): Boolean {
        val m = pattern.matcher(value)
        while (m.find()) {
            for (i in 0..m.groupCount()) {
                segments.add(m.group(i))
            }
        }
        return segments.size > 0
    }

    override fun canProvideMinute(): Boolean {
        return true
    }

    override val minuteElement: String
        get() = "0"

    override val isMinuteElementLocked: Boolean
        get() = false

    override fun canProvideHour(): Boolean {
        return true
    }

    override val hourElement: String
        get() = "0"

    override val isHourElementLocked: Boolean
        get() = false

    override fun canProvideDayNumber(): Boolean {
        return true
    }

    override val dayNumberElement: String?
        get() = if (segments.size >= 1) segments[1] else null

    override val isDayNumberElementLocked: Boolean
        get() = true

    override fun canProvideMonth(): Boolean {
        return segments.size > 0
    }

    override val monthElement: String
        get() = "*"

    override val isMonthElementLocked: Boolean
        get() = false

    override fun canProvideDayOfWeek(): Boolean {
        return true
    }

    override val dayOfWeekElement: String
        get() = "*"

    override val isDayOfWeekElementLocked: Boolean
        get() = false

    companion object {
        private const val PATTERN = "([0-9]?[0-9])(st|nd|rd|th)"
        private val pattern: Pattern = Pattern.compile(PATTERN, Pattern.CASE_INSENSITIVE)
    }
}
