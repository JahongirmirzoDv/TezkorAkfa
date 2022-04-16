package uz.algorithmgateway.core.util

import android.text.SpannedString
import androidx.core.text.buildSpannedString
import androidx.core.text.strikeThrough
//import com.redmadrobot.inputmask.MaskedTextChangedListener
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

//class InputChangeListener(private val onTextChange: (maskFilled: Boolean, extractedValue: String, formattedValue: String) -> Unit = { _, _, _ -> }) :
//    MaskedTextChangedListener.ValueListener {
//    var value: String = ""
//
//    override fun onTextChanged(maskFilled: Boolean, extractedValue: String, formattedValue: String) {
//        value = extractedValue
//        onTextChange(maskFilled, extractedValue, formattedValue)
//    }
//}

// String format
enum class FormatOptions {
    PHONE
}

fun String.formatTo(option: FormatOptions): String {
    return when (option) {
        FormatOptions.PHONE -> formatToPhone()
    }
}

private fun String.formatToPhone(): String {
    val uzPhoneLength = 13
    if (length != uzPhoneLength || !startsWith('+')) return this

    return "${substring(0, 5)} (${substring(5, 7)}) ${substring(7, 10)}-${substring(10, 12)}-${substring(12, 14)}"
}

fun String.shorten(length: Int = 2, capLetters: Boolean = true): String {
    var shortened = ""

    split(" ").forEach { str ->
        str.firstOrNull()?.let { shortened += if (capLetters) it.uppercase(Locale.getDefault()) else it }
        if (shortened.length == length) return shortened
    }

    return shortened
}

// Double format
fun Double?.money(maximumFractionDigits: Int = 0): String {
    return try {
        val formatter = NumberFormat.getCurrencyInstance()
        val symbols = (formatter as DecimalFormat).decimalFormatSymbols
        symbols.groupingSeparator = ' '

        symbols.currencySymbol = ""
        formatter.decimalFormatSymbols = symbols
        formatter.maximumFractionDigits = maximumFractionDigits
        return formatter.format(this).trim()
    } catch (exception: Exception) {
        exception.printStackTrace()
        toString()
    }
}

fun Double?.unit(maximumFractionDigits: Int = 0): String {
    return try {
        val formatter = NumberFormat.getCurrencyInstance()
        val symbols = (formatter as DecimalFormat).decimalFormatSymbols
        symbols.groupingSeparator = ','

        symbols.currencySymbol = ""
        formatter.decimalFormatSymbols = symbols
        formatter.maximumFractionDigits = maximumFractionDigits
        return formatter.format(this).trim()
    } catch (exception: Exception) {
        exception.printStackTrace()
        toString()
    }
}

fun Double?.withOldPrice(price: Double, suffix: String? = null): SpannedString {
    if (this == null || this == price || price <= 0) return buildSpannedString {
        append(money())
        suffix?.let {
            append(" ")
            append(suffix)
        }
    }

    return buildSpannedString {
        append(this@withOldPrice.money())
        append(" ")
        strikeThrough {
            append(price.money())
        }
        suffix?.let {
            append(" ")
            append(suffix)
        }
    }
}