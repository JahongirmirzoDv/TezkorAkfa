package uz.algorithmgateway.core.util

import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import uz.algorithmgateway.tezkorakfa.core.util.px

fun <T> ArrayList<T>.cleanup(collection: Collection<T>) {
    clear()
    addAll(collection)
}

fun EditText.textOrNull(): String? {
    return if (text.isNullOrEmpty()) null
    else text.toString()
}

val Fragment.appCompatActivity: AppCompatActivity
    get() {
        return requireActivity() as AppCompatActivity
    }

fun ViewPager2.setOnPageSelected(onPageSelected: (Int) -> Unit) {
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            onPageSelected.invoke(position)
        }
    })
}

fun View.px(dp: Float) = context.px(dp)

fun View.px(dp: Int) = context.px(dp)

fun Fragment.px(dp: Float) = requireContext().px(dp)

fun Fragment.px(dp: Int) = requireContext().px(dp)

fun Fragment.toast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(@StringRes message: Int) {
    Toast.makeText(context, getString(message), Toast.LENGTH_SHORT).show()
}
fun Fragment.toastLong(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Fragment.toastLong(@StringRes message: Int) {
    Toast.makeText(context, getString(message), Toast.LENGTH_LONG).show()
}



fun Fragment.snackBar(message: String) {
    Snackbar.make(
        requireView(),
        message,
        Snackbar.LENGTH_SHORT
    ).show()
}

fun Fragment.snackBarLong(message: String) {
    Snackbar.make(
        requireView(),
        message,
        Snackbar.LENGTH_LONG
    ).show()
}


fun Fragment.snackBar(@StringRes message: Int) {
    Snackbar.make(
        requireView(),
        getString(message),
        Snackbar.LENGTH_SHORT
    ).show()
}