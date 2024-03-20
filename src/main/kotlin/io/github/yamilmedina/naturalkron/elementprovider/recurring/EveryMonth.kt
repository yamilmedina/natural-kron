package  io.github.yamilmedina.naturalkron.elementprovider.recurring

import  io.github.yamilmedina.naturalkron.ExpressionElementProvider
import java.util.*
import java.util.regex.Pattern

internal class EveryMonth : ExpressionElementProvider {
    private val segments: MutableList<String> = mutableListOf()
    private val monthMap: MutableMap<String, String?> by lazy {
        mutableMapOf(
            "january" to "1",
            "february" to "2",
            "march" to "3",
            "april" to "4",
            "may" to "5",
            "june" to "6",
            "july" to "7",
            "august" to "8",
            "september" to "9",
            "october" to "10",
            "november" to "11",
            "december" to "12"
        )
    }

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
        get() = "1"

    override fun canProvideMonth(): Boolean {
        return true
    }

    override val monthElement: String?
        get() {
            if ((segments.size > 0 && segments[0] == "monthly")
                || (segments.size > 3 && segments[3] == "month")
            ) {
                return "*"
            }
            return if (segments.size > 3 && monthMap[segments[3].lowercase(Locale.getDefault())] != null
            ) monthMap[segments[3].lowercase(Locale.getDefault())]
            else null
        }

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
        get() = false

    override val isMonthElementLocked: Boolean
        get() = false

    override val isDayOfWeekElementLocked: Boolean
        get() = false

    companion object {
        private const val PATTERN =
            "(monthly|(every|each)\\s(month|january|february|march|april|may|june|july|august|september|october|november|december))"
        private val pattern: Pattern = Pattern.compile(PATTERN, Pattern.CASE_INSENSITIVE)
    }
}
