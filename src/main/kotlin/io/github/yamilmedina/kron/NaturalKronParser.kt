package io.github.yamilmedina.kron

import io.github.yamilmedina.kron.elementprovider.DayNumber
import io.github.yamilmedina.kron.elementprovider.hour.Base12Hour
import io.github.yamilmedina.kron.elementprovider.hour.Base12HourShort
import io.github.yamilmedina.kron.elementprovider.hour.Base24Hour
import io.github.yamilmedina.kron.elementprovider.hour.Midnight
import io.github.yamilmedina.kron.elementprovider.hour.Noon
import io.github.yamilmedina.kron.elementprovider.recurring.EveryDay
import io.github.yamilmedina.kron.elementprovider.recurring.EveryDayNumber
import io.github.yamilmedina.kron.elementprovider.recurring.EveryHour
import io.github.yamilmedina.kron.elementprovider.recurring.EveryMinute
import io.github.yamilmedina.kron.elementprovider.recurring.EveryMonth
import io.github.yamilmedina.kron.elementprovider.recurring.EveryWeek
import io.github.yamilmedina.kron.elementprovider.recurring.EveryYear
import java.text.ParseException
import java.util.regex.Pattern

class NaturalKronParser {

    companion object {
        const val VALID_PATTERN =
            "^(@reboot|@yearly|@annually|@monthly|@weekly|@daily|@midnight|@hourly|((?:[1-9]?\\d|\\*)\\s*(?:(?:[\\/\\-][1-9]?\\d)|(?:,[1-9]?\\d)+)?\\s*){5})$"
    }

    private val elementProviders: List<ExpressionElementProvider> = listOf(
        EveryYear(),
        EveryMonth(),
        EveryWeek(),
        EveryDayNumber(),
        EveryDay(),
        EveryHour(),
        EveryMinute(),
        DayNumber(),
        Noon(),
        Midnight(),
        Base12Hour(),
        Base12HourShort(),
        Base24Hour()
    )

    fun parse(string: String): CronExpression {
        val lowercaseString = string.toLowerCase()
        val mappings = mapOf(
            "@yearly" to CronExpression("0", "0", "1", "1", "*"),
            "@annually" to CronExpression("0", "0", "1", "1", "*"),
            "@monthly" to CronExpression("0", "0", "1", "*", "*"),
            "@weekly" to CronExpression("0", "0", "*", "*", "0"),
            "@midnight" to CronExpression("0", "0", "*", "*", "*"),
            "@daily" to CronExpression("0", "0", "*", "*", "*"),
            "@hourly" to CronExpression("0", "*", "*", "*", "*")
        )

        if (mappings.containsKey(lowercaseString)) {
            return mappings[lowercaseString]!!
        }

        val expression = CronExpression()
        var isMinuteElementLocked = false
        var isHourElementLocked = false
        var isDayNumberElementLocked = false
        var isMonthElementLocked = false
        var isDayOfWeekElementLocked = false

        val shouldUpdateMinute: (CronExpression, ExpressionElementProvider) -> Boolean = { expression, subParser ->
            subParser.canProvideMinute() && !isMinuteElementLocked
        }

        val shouldUpdateHour: (CronExpression, ExpressionElementProvider) -> Boolean = { expression, subParser ->
            subParser.canProvideHour() && !isHourElementLocked
        }

        val shouldUpdateDayNumber: (CronExpression, ExpressionElementProvider) -> Boolean = { expression, subParser ->
            subParser.canProvideDayNumber() && !isDayNumberElementLocked
        }

        val shouldUpdateMonth: (CronExpression, ExpressionElementProvider) -> Boolean = { expression, subParser ->
            subParser.canProvideMonth() && !isMonthElementLocked
        }

        val shouldUpdateDayOfWeek: (CronExpression, ExpressionElementProvider) -> Boolean = { expression, subParser ->
            subParser.canProvideDayOfWeek() && !isDayOfWeekElementLocked
        }

        for (elementProvider in elementProviders) {
            if (elementProvider.matches(lowercaseString)) {
                if (shouldUpdateMinute(expression, elementProvider)) {
                    expression.minute = elementProvider.getMinuteElement()
                }

                if (shouldUpdateHour(expression, elementProvider)) {
                    expression.hour = elementProvider.getHourElement()
                }

                if (shouldUpdateDayNumber(expression, elementProvider)) {
                    expression.dayNumber = elementProvider.getDayNumberElement()
                }

                if (shouldUpdateMonth(expression, elementProvider)) {
                    expression.month = elementProvider.getMonthElement()
                }

                if (shouldUpdateDayOfWeek(expression, elementProvider)) {
                    expression.dayOfWeek = elementProvider.getDayOfWeekElement()
                }

                if (elementProvider.isMinuteElementLocked()) {
                    isMinuteElementLocked = true
                }

                if (elementProvider.isHourElementLocked()) {
                    isHourElementLocked = true
                }

                if (elementProvider.isDayNumberElementLocked()) {
                    isDayNumberElementLocked = true
                }

                if (elementProvider.isMonthElementLocked()) {
                    isMonthElementLocked = true
                }

                if (elementProvider.isDayOfWeekElementLocked()) {
                    isDayOfWeekElementLocked = true
                }
            }
        }

        val validPattern = Pattern.compile(VALID_PATTERN)
        if (expression.hasNothing() || !validPattern.matcher(expression.toString()).matches()) {
            throw ParseException("Unable to parse \"$string\", expression is: $expression", 0)
        }

        return expression
    }

    fun fromString(string: String): String {
        val parser = NaturalKronParser()
        return parser.parse(string).toString()
    }

    fun isValid(string: String): Boolean {
        if (string == "@reboot") {
            return true
        }
        return try {
            fromString(string)
            true
        } catch (e: ParseException) {
            false
        }
    }
}


