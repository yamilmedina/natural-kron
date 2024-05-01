package io.github.yamilmedina.kron

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class NaturalKronQuartzStyleParserTest {

    @ParameterizedTest
    @EnumSource(TestParams::class)
    fun testParser(params: TestParams) {
        val actual = io.github.yamilmedina.kron.NaturalKronParser().parse(params.input)
        assertEquals(params.expected, actual.toQuartzStyle(), "Failed for input: <${params.input}>")
    }

    @ParameterizedTest
    @EnumSource(TestParams::class)
    fun testParserFromString(params: TestParams) {
        val actual = io.github.yamilmedina.kron.NaturalKronParser().fromString(params.input, KronStyle.QUARTZ)
        assertEquals(params.expected, actual, "Failed for input: <${params.input}>")
    }

    @ParameterizedTest
    @EnumSource(TestParams::class)
    fun testIsValid(params: TestParams) {
        val actual = io.github.yamilmedina.kron.NaturalKronParser().isValid(params.input, KronStyle.QUARTZ)
        assertEquals(true, actual, "Failed for input: <${params.input}>")
    }

    companion object {

        enum class TestParams(val input: String, val expected: String) {
            CASE_1("@yearly", "0 0 0 1 1 ?"),
            CASE_2("@annually", "0 0 0 1 1 ?"),
            CASE_3("@monthly", "0 0 0 1 * ?"),
            CASE_4("@weekly", "0 0 0 ? * 0"),
            CASE_5("@daily", "0 0 0 * * ?"),
            CASE_6("@midnight", "0 0 0 * * ?"),
            CASE_7("@hourly", "0 0 * * * ?"),
            CASE_8("each day", "0 0 0 * * ?"),
            CASE_9("every day", "0 0 0 * * ?"),
            CASE_10("daily", "0 0 0 * * ?"),
            CASE_11("every day at 3 AM", "0 0 3 * * ?"),
            CASE_12("5am", "0 0 5 * * ?"),
            CASE_13("daily at 5am", "0 0 5 * * ?"),
            CASE_14("every friday at 5am", "0 0 5 ? * 5"),
            CASE_15("daily at 17:30", "0 30 17 * * ?"),
            CASE_16("every week", "0 0 0 ? * 0"),
            CASE_17("weekly", "0 0 0 ? * 0"),
            CASE_18("every minute", "0 * * * * ?"),
            CASE_19("every 5 minutes", "0 */5 * * * ?"),
            CASE_20("every 30 minutes", "0 */30 * * * ?"),
            CASE_21("every month", "0 0 0 1 * ?"),
            CASE_22("monthly", "0 0 0 1 * ?"),
            CASE_23("every Monday", "0 0 0 ? * 1"),
            CASE_24("every Wednesday", "0 0 0 ? * 3"),
            CASE_25("every Friday", "0 0 0 ? * 5"),
            CASE_26("every hour", "0 0 * * * ?"),
            CASE_27("every 6 hours", "0 0 */6 * * ?"),
            CASE_28("hourly", "0 0 * * * ?"),
            CASE_29("every year", "0 0 0 1 1 ?"),
            CASE_30("yearly", "0 0 0 1 1 ?"),
            CASE_31("annually", "0 0 0 1 1 ?"),
            CASE_32("every day at 9am", "0 0 9 * * ?"),
            CASE_33("every day at 5pm", "0 0 17 * * ?"),
            CASE_34("every day at 5:45pm", "0 45 17 * * ?"),
            CASE_35("every day at 17:00", "0 0 17 * * ?"),
            CASE_36("every day at 17:25", "0 25 17 * * ?"),
            CASE_37("5:15am every Tuesday", "0 15 5 ? * 2"),
            CASE_38("7pm every Thursday", "0 0 19 ? * 4"),
            CASE_39("every May", "0 0 0 1 5 ?"),
            CASE_40("every december", "0 0 0 1 12 ?"),
            CASE_41("midnight", "0 0 0 * * ?"),
            CASE_42("midnight on tuesdays", "0 0 0 ? * 2"),
            CASE_43("every 5 minutes on Tuesdays", "0 */5 * ? * 2"),
            CASE_44("noon", "0 0 12 * * ?"),
            CASE_45("every 25th", "0 0 0 25 * ?"),
            CASE_46("every 3rd of January", "0 0 0 3 1 ?"),
            CASE_47("11pm every 3rd of december", "0 0 23 3 12 ?"),
            CASE_48("every day at 17:01", "0 1 17 * * ?"),
            CASE_49("every day at 5:01pm", "0 1 17 * * ?"),
        }
    }

}