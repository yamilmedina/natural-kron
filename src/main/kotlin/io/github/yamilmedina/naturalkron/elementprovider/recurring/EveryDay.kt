package  io.github.yamilmedina.naturalkron.elementprovider.recurring

import  io.github.yamilmedina.naturalkron.ExpressionElementProvider
import java.util.*
import java.util.regex.Pattern

internal class EveryDay : ExpressionElementProvider {

    private val segments: MutableList<String> = mutableListOf()
    private val dayMap: MutableMap<String, String> by lazy {
        mutableMapOf(
            "sunday" to "0",
            "monday" to "1",
            "tuesday" to "2",
            "wednesday" to "3",
            "thursday" to "4",
            "friday" to "5",
            "saturday" to "6"
        )
    }

    override fun matches(value: String): Boolean {
        val m = dayOfWeekPattern.matcher(value)
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
        get() = "*"

    override fun canProvideMonth(): Boolean {
        return true
    }

    override val monthElement: String
        get() = "*"

    override fun canProvideDayOfWeek(): Boolean {
        return segments.size > 0
    }

    override val dayOfWeekElement: String?
        get() {
            if ((segments.size > 0 && segments[0] == "daily") || (segments.size > 3 && segments[3] == "day")) {
                return "*"
            }
            return if (segments.size > 3 && dayMap[segments[3].lowercase(Locale.getDefault())] != null) dayMap[segments[3].lowercase(
                Locale.getDefault()
            )]
            else null
        }

    override val isMinuteElementLocked: Boolean
        get() = false

    override val isHourElementLocked: Boolean
        get() = false

    override val isDayNumberElementLocked: Boolean
        get() = false

    override val isMonthElementLocked: Boolean
        get() = false

    override val isDayOfWeekElementLocked: Boolean
        get() = true

    companion object {
        private const val DAY_OF_WEEK_PATTERN =
            "(daily|(every|each|on)\\s(day|monday|tuesday|wednesday|thursday|friday|saturday|sunday)(?s))"
        private val dayOfWeekPattern: Pattern = Pattern.compile(DAY_OF_WEEK_PATTERN, Pattern.CASE_INSENSITIVE)
    }
}
