package io.github.yamilmedina.kron

internal enum class DayKind {
    EVERY_DAY,
    WEEKDAY,
    SPECIFIC_DAY
}

internal data class NormalizedSchedule(
    val dayKind: DayKind,
    val specificDay: String? = null,
    val hour: Int,
    val minute: Int
)
