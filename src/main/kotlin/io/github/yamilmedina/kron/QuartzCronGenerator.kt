package io.github.yamilmedina.kron

internal object QuartzCronGenerator {

    fun generate(schedule: NormalizedSchedule): String {
        val dayOfWeek = when (schedule.dayKind) {
            DayKind.EVERY_DAY -> "*"
            DayKind.WEEKDAY -> "MON-FRI"
            DayKind.SPECIFIC_DAY -> schedule.specificDay
                ?: throw IllegalArgumentException("Missing specific day for specific-day schedule")
        }

        return "0 ${schedule.minute} ${schedule.hour} ? * $dayOfWeek"
    }
}
