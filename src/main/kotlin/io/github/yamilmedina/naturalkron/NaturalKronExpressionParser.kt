package io.github.yamilmedina.naturalkron

import io.github.yamilmedina.naturalkron.elementprovider.DayNumber
import io.github.yamilmedina.naturalkron.elementprovider.hour.Base12Hour
import io.github.yamilmedina.naturalkron.elementprovider.hour.Base12HourShort
import io.github.yamilmedina.naturalkron.elementprovider.hour.Base24Hour
import io.github.yamilmedina.naturalkron.elementprovider.hour.Midnight
import io.github.yamilmedina.naturalkron.elementprovider.hour.Noon
import io.github.yamilmedina.naturalkron.elementprovider.recurring.EveryDay
import io.github.yamilmedina.naturalkron.elementprovider.recurring.EveryDayNumberStep
import io.github.yamilmedina.naturalkron.elementprovider.recurring.EveryHour
import io.github.yamilmedina.naturalkron.elementprovider.recurring.EveryMinute
import io.github.yamilmedina.naturalkron.elementprovider.recurring.EveryMonth
import io.github.yamilmedina.naturalkron.elementprovider.recurring.EveryMonthStep
import io.github.yamilmedina.naturalkron.elementprovider.recurring.EveryWeek
import io.github.yamilmedina.naturalkron.elementprovider.recurring.EveryYear
import java.util.*
import java.util.regex.Pattern

class NaturalKronExpressionParser {
    private val mappings: MutableMap<String, KronExpression> by lazy {
        mutableMapOf<String, KronExpression>().also {
            it["yearly"] = KronExpression("0", "0", "0", "1", "1", "*")
            it["annually"] = KronExpression("0", "0", "0", "1", "1", "*")
            it["monthly"] = KronExpression("0", "0", "0", "1", "*", "*")
            it["weekly"] = KronExpression("0", "0", "0", "*", "*", "0")
            it["midnight"] = KronExpression("0", "0", "0", "*", "*", "*")
            it["daily"] = KronExpression("0", "0", "0", "*", "*", "*")
            it["hourly"] = KronExpression("0", "0", "*", "*", "*", "*")
            it["midday"] = KronExpression("0", "0", "12", "*", "*", "*")
        }
    }
    private val elementProviders: List<ExpressionElementProvider> = listOf(
        EveryYear(),
        EveryMonth(),
        EveryMonthStep(),
        EveryWeek(),
        io.github.yamilmedina.naturalkron.elementprovider.recurring.EveryDayNumber(),
        EveryDayNumberStep(),
        EveryDay(),
        EveryHour(),
        EveryMinute(),
        DayNumber(),
        Noon(),
        Midnight(),
        Base12Hour(),
        Base12HourShort(),
        Base24Hour(),
    )

    /**
     * Parses a natural language expression into a KronExpression
     *
     * @param input the natural language expression
     * @return the KronExpression
     * @throws KronParserException if the input cannot be parsed
     */
    fun parse(input: String): KronExpression {
        val rawExpression = input.lowercase(Locale.getDefault())
        if (mappings[rawExpression] != null) {
            // return a 'cached' value of common expressions
            return mappings[rawExpression]!!
        }

        val kronExpression = KronExpression()
        var isSecondElementLocked = false
        var isMinuteElementLocked = false
        var isHourElementLocked = false
        var isDayNumberElementLocked = false
        var isMonthElementLocked = false
        var isDayOfWeekElementLocked = false

        elementProviders.forEach { elementProvider ->
            if (elementProvider.matches(input)) {
                if (shouldUpdateSecond(elementProvider, isSecondElementLocked)) {
                    kronExpression.setSecond(elementProvider.secondElement)
                }

                if (shouldUpdateMinute(elementProvider, isMinuteElementLocked)) {
                    kronExpression.setMinute(elementProvider.minuteElement)
                }

                if (shouldUpdateHour(elementProvider, isHourElementLocked)) {
                    kronExpression.setHour(elementProvider.hourElement)
                }

                if (shouldUpdateDayNumber(elementProvider, isDayNumberElementLocked)) {
                    kronExpression.setDayNumber(elementProvider.dayNumberElement)
                }

                if (shouldUpdateMonth(elementProvider, isMonthElementLocked)) {
                    kronExpression.setMonth(elementProvider.monthElement)
                }

                if (shouldUpdateDayOfWeek(elementProvider, isDayOfWeekElementLocked)) {
                    kronExpression.setDayOfWeek(elementProvider.dayOfWeekElement)
                }

                if (elementProvider.isSecondElementLocked) {
                    isSecondElementLocked = true
                }

                if (elementProvider.isMinuteElementLocked) {
                    isMinuteElementLocked = true
                }

                if (elementProvider.isHourElementLocked) {
                    isHourElementLocked = true
                }

                if (elementProvider.isDayNumberElementLocked) {
                    isDayNumberElementLocked = true
                }

                if (elementProvider.isMonthElementLocked) {
                    isMonthElementLocked = true
                }

                if (elementProvider.isDayOfWeekElementLocked) {
                    isDayOfWeekElementLocked = true
                }
            }
        }

        val isValidKronExpression = pattern.matcher(kronExpression.toString())
        if (kronExpression.hasNothing() || !isValidKronExpression.matches()) {
            throw KronParserException(String.format("Unable to parse \"%s\"", input))
        }

        return kronExpression
    }

    internal companion object {
        private const val VALID_PATTERN: String =
            "^(((?:[1-9]?\\d|\\*)\\s*(?:(?:[\\/-][1-9]?\\d)|(?:,[1-9]?\\d)+)?\\s*){6})$"
        private val pattern: Pattern = Pattern.compile(VALID_PATTERN)

        private fun shouldUpdateSecond(subParser: ExpressionElementProvider, isSecondElementLocked: Boolean): Boolean {
            return subParser.canProvideSecond() && !isSecondElementLocked
        }

        private fun shouldUpdateMinute(subParser: ExpressionElementProvider, isMinuteElementLocked: Boolean): Boolean {
            return subParser.canProvideMinute() && !isMinuteElementLocked
        }

        private fun shouldUpdateHour(subParser: ExpressionElementProvider, isHourElementLocked: Boolean): Boolean {
            return subParser.canProvideHour() && !isHourElementLocked
        }

        private fun shouldUpdateDayNumber(
            subParser: ExpressionElementProvider,
            isDayNumberElementLocked: Boolean
        ): Boolean {
            return subParser.canProvideDayNumber() && !isDayNumberElementLocked
        }

        private fun shouldUpdateMonth(subParser: ExpressionElementProvider, isMonthElementLocked: Boolean): Boolean {
            return subParser.canProvideMonth() && !isMonthElementLocked
        }

        private fun shouldUpdateDayOfWeek(
            subParser: ExpressionElementProvider,
            isDayOfWeekElementLocked: Boolean
        ): Boolean {
            return subParser.canProvideDayOfWeek() && !isDayOfWeekElementLocked
        }
    }
}
