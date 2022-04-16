package uz.algorithmgateway.core.util

import android.util.Size
import uz.algorithmgateway.core.Sheet

val Sheet.screenResolution: Size
    get() = requireContext().screenResolution