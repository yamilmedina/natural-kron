package io.github.yamilmedina.kron

class KronExpression(
    var minute: String? = null,
    var hour: String? = null,
    var dayNumber: String? = null,
    var month: String? = null,
    var dayOfWeek: String? = null
) {

    fun hasMinute(): Boolean = minute != null

    fun setMinute(minute: String): KronExpression {
        this.minute = minute
        return this
    }

    fun hasHour(): Boolean = hour != null

    fun setHour(hour: String): KronExpression {
        this.hour = hour
        return this
    }

    fun hasDayNumber(): Boolean = dayNumber != null

    fun setDayNumber(dayNumber: String): KronExpression {
        this.dayNumber = dayNumber
        return this
    }

    fun hasMonth(): Boolean = month != null

    fun setMonth(month: String): KronExpression {
        this.month = month
        return this
    }

    fun hasDayOfWeek(): Boolean = dayOfWeek != null

    fun setDayOfWeek(dayOfWeek: String): KronExpression {
        this.dayOfWeek = dayOfWeek
        return this
    }

    fun hasNothing(): Boolean = !hasMinute() && !hasHour() && !hasDayNumber() && !hasMonth() && !hasDayOfWeek()

    override fun toString(): String = toUnixStyle()

    /**
     * Returns the expression in Unix style, this is with a 5 segment expression.
     * No seconds are included, starts with minutes.
     */
    fun toUnixStyle(): String = "%s %s %s %s %s".format(
        if (hasMinute()) minute else "0",
        if (hasHour()) hour else "0",
        if (hasDayNumber()) dayNumber else "*",
        if (hasMonth()) month else "*",
        if (hasDayOfWeek()) dayOfWeek else "*"
    )

    /**
     * Returns the expression in Quartz style, this is with a 6 segment expression.
     * Adds a 0 at the beginning of the expression, seconds are just ignored.
     */
    fun toQuartzStyle(): String = "%s %s %s %s %s %s".format(
        "0",
        if (hasMinute()) minute else "0",
        if (hasHour()) hour else "0",
        resolveDayNumber(),
        if (hasMonth()) month else "*",
        resolveDayOfWeek()
    )

    private fun resolveDayNumber(): String {
        return if (dayNumber?.toIntOrNull() == null && dayOfWeek?.toIntOrNull() == null) {
            "*"// * and * -> *
        } else if (hasDayNumber() && dayNumber?.toIntOrNull() != null && dayOfWeek?.toIntOrNull() == null) {
            dayNumber.toString() // number and * -> dayNumber
        } else if (dayNumber?.toIntOrNull() == null && dayOfWeek?.toIntOrNull() != null) {
            "?" // * and number -> ?
        } else "*"
    }

    private fun resolveDayOfWeek(): String {
        return if (dayOfWeek?.toIntOrNull() == null && dayNumber?.toIntOrNull() == null) {
            dayNumber = "*"
            "?"
        } else if (dayOfWeek?.toIntOrNull() != null && dayNumber?.toIntOrNull() == null) {
            dayNumber = "?"
            dayOfWeek.toString()
        } else if (dayOfWeek?.toIntOrNull() == null && dayNumber?.toIntOrNull() != null) {
            "?"
        } else {
            "*"
        }
    }

    // todo, hasDayNumber =  "number" or "*" then DayOfWeek can only be "?"
    // todo, hasDayOfWeek =  "number" or "*" then DayNumber can only be "?"

    // hasDayNumber && hasDayOfWeek -> DayNumber = "*" && DayOfWeek = "?"
    // hasDayNumber && !hasDayOfWeek -> DayNumber = "number" or "*" && DayOfWeek = "*"


}


