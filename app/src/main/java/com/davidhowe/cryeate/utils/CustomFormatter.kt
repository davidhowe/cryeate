package com.davidhowe.cryeate.utils

import java.text.DecimalFormat

object CustomFormatter {
    fun getPriceDecimalFormatter(value: Double) : DecimalFormat {
        return when {
                value>10 -> DecimalFormat("#0")
                value>5 -> DecimalFormat("#0.0")
                else -> DecimalFormat("#0.00")
            }
    }

    fun getLastUpdatedPattern() : String {
        return "YYYY-MM-dd HH:mm"
    }
}