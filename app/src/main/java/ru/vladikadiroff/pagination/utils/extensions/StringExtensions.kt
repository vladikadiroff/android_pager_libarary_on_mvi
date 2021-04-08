package ru.vladikadiroff.pagination.utils.extensions

import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.ln
import kotlin.math.pow

fun Int.truncateNumber(): String {
    return try {
        val number = this.toDouble()
        val suffixChars = "KMGTPE"
        val formatter = DecimalFormat("###.#").also { it.roundingMode = RoundingMode.DOWN }
        if (number < 1000.0) formatter.format(number)
        else {
            val exp = (ln(number) / ln(1000.0)).toInt()
            formatter.format(number / 1000.0.pow(exp.toDouble())) + suffixChars[exp - 1]
        }
    } catch (e: Exception) {
        this.toString()
    }
}