package  io.github.yamilmedina.kron

internal interface ExpressionElementProvider {
    /**
     * @param string
     * @return Boolean
     */
    fun matches(string: String): Boolean

    /**
     * @return Boolean
     */
    fun canProvideMinute(): Boolean

    /**
     * @return String
     */
    fun getMinuteElement(): String?

    /**
     * @return Boolean
     */
    fun isMinuteElementLocked(): Boolean

    /**
     * @return Boolean
     */
    fun canProvideHour(): Boolean

    /**
     * @return String
     */
    fun getHourElement(): String?

    /**
     * @return Boolean
     */
    fun isHourElementLocked(): Boolean

    /**
     * @return Boolean
     */
    fun canProvideDayNumber(): Boolean

    /**
     * @return String
     */
    fun getDayNumberElement(): String?

    /**
     * @return Boolean
     */
    fun isDayNumberElementLocked(): Boolean

    /**
     * @return Boolean
     */
    fun canProvideMonth(): Boolean

    /**
     * @return String
     */
    fun getMonthElement(): String?

    /**
     * @return Boolean
     */
    fun isMonthElementLocked(): Boolean

    /**
     * @return Boolean
     */
    fun canProvideDayOfWeek(): Boolean

    /**
     * @return String
     */
    fun getDayOfWeekElement(): String?

    /**
     * @return Boolean
     */
    fun isDayOfWeekElementLocked(): Boolean
}


