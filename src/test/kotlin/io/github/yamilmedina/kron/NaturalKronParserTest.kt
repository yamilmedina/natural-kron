package io.github.yamilmedina.kron

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class NaturalKronParserTest {

    @ParameterizedTest
    @EnumSource(TestParams::class)
    fun testParser(params: TestParams) {
        val actual = io.github.yamilmedina.kron.NaturalKronParser().parse(params.input)
        assertEquals(params.expected, actual.toString(), "Failed for input: <${params.input}>")
    }

    companion object {

        enum class TestParams(val input: String, val expected: String) {
            PARAM_1("@yearly", "0 0 1 1 *"),
            PARAM_2("@annually", "0 0 1 1 *"),
            PARAM_3("@monthly", "0 0 1 * *"),
            PARAM_4("@weekly", "0 0 * * 0"),
            PARAM_5("@daily", "0 0 * * *"),
            PARAM_6("@midnight", "0 0 * * *"),
            PARAM_7("@hourly", "0 * * * *"),
            PARAM_8("each day", "0 0 * * *"),
            PARAM_9("every day", "0 0 * * *"),
            PARAM_10("daily", "0 0 * * *"),
            PARAM_11("every day at 3 AM", "0 3 * * *"),
            PARAM_12("5am", "0 5 * * *"),
            PARAM_13("daily at 5am", "0 5 * * *"),
            PARAM_14("every friday at 5am", "0 5 * * 5"),
            PARAM_15("daily at 17:30", "30 17 * * *"),
            PARAM_16("every week", "0 0 * * 0"),
            PARAM_17("weekly", "0 0 * * 0"),
            PARAM_18("every minute", "* * * * *"),
            PARAM_19("every 5 minutes", "*/5 * * * *"),
            PARAM_20("every 30 minutes", "*/30 * * * *"),
            PARAM_21("every month", "0 0 1 * *"),
            PARAM_22("monthly", "0 0 1 * *"),
            PARAM_23("every Monday", "0 0 * * 1"),
            PARAM_24("every Wednesday", "0 0 * * 3"),
            PARAM_25("every Friday", "0 0 * * 5"),
            PARAM_26("every hour", "0 * * * *"),
            PARAM_27("every 6 hours", "0 */6 * * *"),
            PARAM_28("hourly", "0 * * * *"),
            PARAM_29("every year", "0 0 1 1 *"),
            PARAM_30("yearly", "0 0 1 1 *"),
            PARAM_31("annually", "0 0 1 1 *"),
            PARAM_32("every day at 9am", "0 9 * * *"),
            PARAM_33("every day at 5pm", "0 17 * * *"),
            PARAM_34("every day at 5:45pm", "45 17 * * *"),
            PARAM_35("every day at 17:00", "0 17 * * *"),
            PARAM_36("every day at 17:25", "25 17 * * *"),
            PARAM_37("5:15am every Tuesday", "15 5 * * 2"),
            PARAM_38("7pm every Thursday", "0 19 * * 4"),
            PARAM_39("every May", "0 0 1 5 *"),
            PARAM_40("every december", "0 0 1 12 *"),
            PARAM_41("midnight", "0 0 * * *"),
            PARAM_42("midnight on tuesdays", "0 0 * * 2"),
            PARAM_43("every 5 minutes on Tuesdays", "*/5 * * * 2"),
            PARAM_44("noon", "0 12 * * *"),
            PARAM_45("every 25th", "0 0 25 * *"),
            PARAM_46("every 3rd of January", "0 0 3 1 *"),
            PARAM_47("11pm every 3rd of december", "0 23 3 12 *"),
            PARAM_48("every day at 17:01", "1 17 * * *"),
            PARAM_49("every day at 5:01pm", "1 17 * * *"),
        }
    }

}