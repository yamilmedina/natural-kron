package io.github.yamilmedina.kron

class CronExpression(
    var minute: String? = null,
    var hour: String? = null,
    var dayNumber: String? = null,
    var month: String? = null,
    var dayOfWeek: String? = null
) {

    fun hasMinute(): Boolean = minute != null

    fun setMinute(minute: String): CronExpression {
        this.minute = minute
        return this
    }

    fun hasHour(): Boolean = hour != null

    fun setHour(hour: String): CronExpression {
        this.hour = hour
        return this
    }

    fun hasDayNumber(): Boolean = dayNumber != null

    fun setDayNumber(dayNumber: String): CronExpression {
        this.dayNumber = dayNumber
        return this
    }

    fun hasMonth(): Boolean = month != null

    fun setMonth(month: String): CronExpression {
        this.month = month
        return this
    }

    fun hasDayOfWeek(): Boolean = dayOfWeek != null

    fun setDayOfWeek(dayOfWeek: String): CronExpression {
        this.dayOfWeek = dayOfWeek
        return this
    }

    fun hasNothing(): Boolean = !hasMinute() && !hasHour() && !hasDayNumber() && !hasMonth() && !hasDayOfWeek()

    override fun toString(): String = "%s %s %s %s %s".format(
        if (hasMinute()) minute else "0",
        if (hasHour()) hour else "0",
        if (hasDayNumber()) dayNumber else "*",
        if (hasMonth()) month else "*",
        if (hasDayOfWeek()) dayOfWeek else "*"
    )
}


