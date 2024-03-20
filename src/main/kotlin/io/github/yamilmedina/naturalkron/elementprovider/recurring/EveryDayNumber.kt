package  io.github.yamilmedina.naturalkron.elementprovider.recurring

import  io.github.yamilmedina.naturalkron.ExpressionElementProvider
import java.util.*
import java.util.regex.Pattern

internal class EveryDayNumber : ExpressionElementProvider {
    private val segments: MutableList<String> = mutableListOf()
    private val monthMap: MutableMap<String, String> by lazy {
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
        val m = io.github.yamilmedina.naturalkron.elementprovider.recurring.EveryDayNumber.Companion.pattern.matcher(value)
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

    override val dayNumberElement: String?
        get() = if (segments.size >= 3) segments[2] else null

    override fun canProvideMonth(): Boolean {
        return segments.size >= 5
    }

    override val monthElement: String?
        get() = if (segments.size >= 5 && monthMap[segments[4].lowercase(Locale.getDefault())] != null
        ) monthMap[segments[4].lowercase(Locale.getDefault())]
        else null

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
        get() = canProvideMonth()

    override val isDayOfWeekElementLocked: Boolean
        get() = false

    companion object {
        private const val PATTERN =
            "(every|each)\\s([0-9]?[0-9])(st|nd|rd|th)\\sof\\s(month|january|february|march|april|may|june|july|august|september|october|november|december)"
        private val pattern: Pattern = Pattern.compile(io.github.yamilmedina.naturalkron.elementprovider.recurring.EveryDayNumber.Companion.PATTERN, Pattern.CASE_INSENSITIVE)
    }
}
