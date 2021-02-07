package ru.vladikadiroff.pagination.utils.extensions

import java.math.RoundingMode
import java.text.DateFormatSymbols
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import kotlin.math.ln
import kotlin.math.pow

fun String.truncateNumber(): String {
    return try {
        val number = this.toDouble()
        val suffixChars = "KMGTPE"
        val formatter = DecimalFormat("###.#").also { it.roundingMode = RoundingMode.DOWN }
        if (number < 1000.0) formatter.format(number)
        else {
            val exp = (ln(number) / ln(1000.0)).toInt()
            formatter.format(number / 1000.0.pow(exp.toDouble())) + suffixChars[exp - 1]
        }
    } catch (e: Exception){
        this
    }
}

fun String.fromApiToStringData(): String {
    return try {
        val data = this.substringBefore("T")
        val year = data.substringBefore("-")
        val monthNumber = data.substring(5).substringBefore("-")
        val day = data.substring(8)
        val month = DateFormatSymbols().months[monthNumber.toInt() - 1]
        "$month $day, $year"
    } catch (e: Exception) {
        this
    }
}

fun String.withSeparatorBetweenThousands(): String {
    return try {
        val formatter = DecimalFormat()
        val symbols: DecimalFormatSymbols = DecimalFormatSymbols.getInstance()
        symbols.groupingSeparator = ' '
        formatter.decimalFormatSymbols = symbols
        formatter.format(this.toLong()).toString()
    } catch (e: Exception) {
        this
    }
}
