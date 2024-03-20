package  io.github.yamilmedina.naturalkron.elementprovider.recurring

import  io.github.yamilmedina.naturalkron.ExpressionElementProvider
import java.util.regex.Pattern

internal class EveryMonthStep : ExpressionElementProvider {
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

    override fun canProvideHour(): Boolean {
        return true
    }

    override val hourElement: String
        get() = "0"

    override fun canProvideDayNumber(): Boolean {
        return true
    }

    override val dayNumberElement: String
        get() = "0"

    override fun canProvideMonth(): Boolean {
        return segments.size == 3
    }

    override val monthElement: String
        get() = if (segments.size > 2) String.format("*/%s", segments[2]) else "*"

    override fun canProvideDayOfWeek(): Boolean {
        return true
    }

    override val dayOfWeekElement: String
        get() = "*"

    override val isMinuteElementLocked: Boolean
        get() = false

    override val isHourElementLocked: Boolean
        get() = false

    override val isDayNumberElementLocked: Boolean
        get() = true

    override val isMonthElementLocked: Boolean
        get() = false

    override val isDayOfWeekElementLocked: Boolean
        get() = false

    companion object {
        private const val PATTERN = "(every\\s?(1[1-2]|[1-9])?\\s?months)"
        private val pattern: Pattern = Pattern.compile(PATTERN, Pattern.CASE_INSENSITIVE)
    }
}
