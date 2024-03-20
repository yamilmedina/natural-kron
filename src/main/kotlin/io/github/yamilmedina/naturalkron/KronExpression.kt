package  io.github.yamilmedina.naturalkron

import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class KronExpression {
    var second: String? = null
    var minute: String? = null
    var hour: String? = null
    var dayNumber: String? = null
    var month: String? = null
    var dayOfWeek: String? = null

    internal constructor()

    constructor(
        second: String?,
        minute: String?,
        hour: String?,
        dayNumber: String?,
        month: String?,
        dayOfWeek: String?
    ) : super() {
        this.second = second
        this.minute = minute
        this.hour = hour
        this.dayNumber = dayNumber
        this.month = month
        this.dayOfWeek = dayOfWeek
    }

    fun hasSecond(): Boolean {
        return second != null
    }

    fun setSecond(second: String?): KronExpression {
        this.second = second
        return this
    }

    fun setSecond(second: Int): KronExpression {
        return setSecond(second.toString())
    }

    fun hasMinute(): Boolean {
        return minute != null
    }

    fun setMinute(minute: String?): KronExpression {
        this.minute = minute
        return this
    }

    fun setMinute(minute: Int): KronExpression {
        return setMinute(minute.toString())
    }

    fun hasHour(): Boolean {
        return hour != null
    }

    fun setHour(hour: String?): KronExpression {
        this.hour = hour
        return this
    }

    fun setHour(minute: Int): KronExpression {
        return setHour(minute.toString())
    }

    fun hasDayNumber(): Boolean {
        return dayNumber != null
    }

    fun setDayNumber(dayNumber: String?): KronExpression {
        this.dayNumber = dayNumber
        return this
    }

    fun setDayNumber(minute: Int): KronExpression {
        return setDayNumber(minute.toString())
    }

    fun hasMonth(): Boolean {
        return month != null
    }

    fun setMonth(month: String?): KronExpression {
        this.month = month
        return this
    }

    fun setMonth(minute: Int): KronExpression {
        return setMonth(minute.toString())
    }

    fun hasDayOfWeek(): Boolean {
        return dayOfWeek != null
    }

    fun setDayOfWeek(dayOfWeek: String?): KronExpression {
        this.dayOfWeek = dayOfWeek
        return this
    }

    fun setDayOfWeek(minute: Int): KronExpression {
        return setDayOfWeek(minute.toString())
    }

    fun hasNothing(): Boolean {
        return !hasMinute() && !hasHour() && !hasDayNumber() && !hasMonth() && !hasDayOfWeek()
    }

    fun toNaturalLanguage(): String? {
        if (!hasNothing()) {
            // check if it's one of our static expressions
            val expression = toString()
            if (expression == "0 0 0 1 1 *") return "yearly"
            if (expression == "0 0 0 1 * *") return "monthly"
            if (expression == "0 0 0 * * 0") return "weekly"
            if (expression == "0 0 12 * * ?" || expression == "0 0 12 * * *") return "midday"
            if (expression == "* * * * * ?" || expression == "* * * * * *") return "every second"
            if (expression == "0 * * * * ?" || expression == "0 * * * * *") return "every minute"
            if (expression == "0 0 * * * ?" || expression == "0 0 * * * *") return "hourly"
            if (expression == "0 0 0 * * *") return "daily"

            val b = StringBuilder()
            b.append("every ")

            val timeSetPattern = Pattern.compile("^([1-9]|[0-5][0-9])$")
            val timeStepSetPattern = Pattern.compile("^\\*\\/([1-9]|[0-5][0-9])$")

            // minutes
            if (timeSetPattern.matcher(minute).matches()) {
                if (hour == "*") {
                    b.append(minute).append(" past the hour")
                }
                // let hour capture the minute if set
            }

            if (timeStepSetPattern.matcher(minute).matches()) {
                if (hour == "*") {
                    b.append(minute!!.replace("[^0-9]".toRegex(), "")).append(" minutes")
                }
            }

            // hours
            if (timeSetPattern.matcher(hour).matches()) {
                val h = hour!!.toInt()

                if (minute == "*") {
                    b.append("minute past ")
                        .append(if (h >= 12) h - 12 else h)
                        .append(if (h >= 12) "pm" else "am")
                } else if (timeStepSetPattern.matcher(minute).matches()) {
                    val m = minute!!.replace("[^0-9]".toRegex(), "").toInt()
                    b.append(m)
                        .append(" minutes past ")
                        .append(if (h >= 12) h - 12 else h)
                        .append(if (h >= 12) "pm" else "am")
                } else if (timeSetPattern.matcher(minute).matches()) {
                    val m = minute!!.replace("[^0-9]".toRegex(), "").toInt()
                    if (dayNumber == "*") {
                        b.append("day at ")
                    }
                    b.append(if (h >= 12) h - 12 else h)
                        .append(":")
                        .append(if (m < 10) "0$m" else m)
                        .append(if (h >= 12) "pm" else "am")
                } else if (minute == "0") {
                    if (dayNumber == "*") {
                        b.append("day at ")
                    }
                    b.append(if (h >= 12) h - 12 else h)
                        .append(if (h >= 12) "pm" else "am")
                }
            }

            if (timeStepSetPattern.matcher(hour).matches()) {
                b.append(hour!!.replace("[^0-9]".toRegex(), "")).append(" hours")
            }

            // day of month
            if (dayNumber != "*") {
                val dayOfMonthSetPattern = Pattern.compile("^([1-9]|[1-2][0-9]|3[0-1])$")
                if (dayOfMonthSetPattern.matcher(dayNumber).matches()) {
                    b.append(
                        String.format(
                            "%s%s", dayNumber, KronExpression.Companion.getDayNumberSuffix(
                                dayNumber!!.toInt()
                            )
                        )
                    )
                }
            }

            // month
            if (month != "*") {
                val monthSetPattern = Pattern.compile("^([1-9]|1[0-2])$")
                if (monthSetPattern.matcher(month).matches()) {
                    val c = Calendar.getInstance()
                    c[Calendar.MONTH] = month!!.toInt() - 1
                    val sdf = SimpleDateFormat("MMMM")
                    if (dayNumber != "*") {
                        b.append(" of ")
                    }
                    b.append(sdf.format(c.time))
                }
            }

            // day of week
            val daySetPattern = Pattern.compile("[0-6]")
            if (daySetPattern.matcher(dayOfWeek).matches()) {
                val c = Calendar.getInstance()
                c[Calendar.DAY_OF_WEEK] = dayOfWeek!!.toInt() + 1
                val sdf = SimpleDateFormat("EEEE")

                if (b.toString().endsWith("minutes")) {
                    b.append(" on ")
                        .append(sdf.format(c.time))
                        .append("s")
                } else {
                    b.append(sdf.format(c.time))
                }
            }

            return b.toString()
        }

        return null
    }

    override fun toString(): String {
        return String.format(
            "%s %s %s %s %s %s",
            if (hasSecond()) second else 0,
            if (hasMinute()) minute else 0,
            if (hasHour()) hour else 0,
            if (hasDayNumber()) dayNumber else '*',
            if (hasMonth()) month else '*',
            if (hasDayOfWeek()) dayOfWeek else '*'
        )
    }

    companion object {
        /**
         * Returns a CronExpression for the specified cron expression string.
         *
         * @param expression A cron string
         * @return A CronExpression
         */
        fun fromExpression(expression: String?): KronExpression? {
            if (expression != null) {
                val parts = expression.split("\\s".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (parts.size != 6) {
                    throw KronParserException("Unexpected expression, expected 5 parts, e.g. * * * * * *.")
                }

                val kronExpression = KronExpression()
                kronExpression.setSecond(parts[0])
                kronExpression.setMinute(parts[1])
                kronExpression.setHour(parts[2])
                kronExpression.setDayNumber(parts[3])
                kronExpression.setMonth(parts[4])
                kronExpression.setDayOfWeek(parts[5])

                return kronExpression
            }

            return null
        }

        /**
         * Return a valid suffix for a positional number
         *
         * @param day
         * @return
         */
        private fun getDayNumberSuffix(day: Int): String {
            if (day in 11..13) {
                return "th"
            }
            return when (day % 10) {
                1 -> "st"
                2 -> "nd"
                3 -> "rd"
                else -> "th"
            }
        }
    }
}
