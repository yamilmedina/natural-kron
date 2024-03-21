package  io.github.yamilmedina.naturalkron.elementprovider.recurring

import  io.github.yamilmedina.naturalkron.ExpressionElementProvider
import java.util.regex.Pattern

internal class EveryMinute : ExpressionElementProvider {
    private val segments: MutableList<String> = mutableListOf()

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
        return segments.size > 0
    }

    override val minuteElement: String?
        get() {
            if (canProvideMinute()) {
                return if (segments.size > 3) String.format("*/%s", segments[3]) else "*"
            }
            return null
        }

    override fun canProvideHour(): Boolean {
        return canProvideMinute()
    }

    override val hourElement: String
        get() = "*"

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
        get() = true

    override val isHourElementLocked: Boolean
        get() = false

    override val isDayNumberElementLocked: Boolean
        get() = false

    override val isMonthElementLocked: Boolean
        get() = false

    override val isDayOfWeekElementLocked: Boolean
        get() = false

    companion object {
        private const val PATTERN = "((every|each) ?([0-9]+)?\\sminute)"
        private val pattern: Pattern = Pattern.compile(PATTERN, Pattern.CASE_INSENSITIVE)
    }
}