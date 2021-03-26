package com.davidhowe.cryeate

import com.davidhowe.cryeate.utils.CustomFormatter
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CustomFormatterUnitTest {

    @Test
    fun formatting_isCorrect() {
        assertEquals("10", CustomFormatter.getPriceDecimalFormatter(10.5).format(10.5))
        assertEquals("7.5", CustomFormatter.getPriceDecimalFormatter(7.4562345).format(7.4562345))
        assertEquals("2.33", CustomFormatter.getPriceDecimalFormatter(2.325).format(2.325))
        assertEquals("0.03", CustomFormatter.getPriceDecimalFormatter(0.034).format(0.034))
    }
}