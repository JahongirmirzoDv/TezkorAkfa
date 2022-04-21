package uz.algorithmgateway.tezkorakfa.core.util

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Environment
import android.util.DisplayMetrics
import android.util.Size
import android.util.TypedValue
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.io.File

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
fun Fragment.hideKeyboard() {
    val imm: InputMethodManager =
        activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view: View? = activity?.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
fun View.hideKeyboard() {
    val imm: InputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

    imm.hideSoftInputFromWindow(windowToken, 0)
}

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
fun View.showSoftInput() {
    val imm: InputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

    imm.showSoftInput(this, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
}

@Suppress("DEPRECATION")
val Context.screenResolution: Size
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    get() {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display: Display? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) display
        else wm.defaultDisplay

        val metrics = DisplayMetrics()
        display?.getRealMetrics(metrics)

        val width = metrics.widthPixels
        val height = metrics.heightPixels

        return Size(width, height)
    }

fun Context.px(dp: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)

fun Context.px(dp: Int) = px(dp.toFloat()).toInt()

@ColorInt
fun Context.getColorFromAttr(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true,
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}

@RequiresApi(Build.VERSION_CODES.FROYO)
fun Context.getOutputDirectory(): File {
    val appContext = applicationContext
    val mediaDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return if (mediaDir != null && mediaDir.exists())
        mediaDir else appContext.filesDir
}

@RequiresApi(Build.VERSION_CODES.FROYO)
fun Context.clearCacheImages() {
    val externalFilesDir = getOutputDirectory()
    if (externalFilesDir.isDirectory) {
        val listFiles = externalFilesDir.listFiles()
        if (!listFiles.isNullOrEmpty()) {
            listFiles.forEach {
                it.delete()
            }
        }
    }
}

fun Context.getStringFromPrefs(prefName: String, key: String): String? {
    val prefs = getSharedPreferences(prefName, Context.MODE_PRIVATE)

    return prefs.getString(key, null)
}

fun Context.compatColor(@ColorRes colorResId: Int): Int {
    return ContextCompat.getColor(this, colorResId)
}