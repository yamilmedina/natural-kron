package  io.github.yamilmedina.naturalkron.elementprovider.recurring

import  io.github.yamilmedina.naturalkron.ExpressionElementProvider
import java.util.regex.Pattern

internal class EveryYear : ExpressionElementProvider {
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

    override fun canProvideMinute() = true

    override val minuteElement = "0"

    override fun canProvideHour() = true

    override val hourElement = "0"

    override fun canProvideDayNumber() = true

    override val dayNumberElement = "1"

    override fun canProvideMonth() = true

    override val monthElement = "1"

    override fun canProvideDayOfWeek() = true

    override val dayOfWeekElement = "*"

    override val isMinuteElementLocked = false

    override val isHourElementLocked = false

    override val isDayNumberElementLocked = false

    override val isMonthElementLocked = false

    override val isDayOfWeekElementLocked = false

    companion object {
        private const val PATTERN = "(yearly|annually|(every|each) ?([0-9]+)?\\s?year)"
        private val pattern: Pattern = Pattern.compile(PATTERN, Pattern.CASE_INSENSITIVE)
    }
}
