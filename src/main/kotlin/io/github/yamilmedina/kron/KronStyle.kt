package io.github.yamilmedina.kron

enum class KronStyle {
    /**
     * Unix style cron expression
     * 5 segments: minute, hour, day of month, month, day of week
     */
    UNIX,

    /**
     * Quartz style cron expression
     * 6-7 segments: second, minute, hour, day of month, month, day of week, (year)
     */
    QUARTZ
}