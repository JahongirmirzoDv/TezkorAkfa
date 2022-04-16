package uz.algorithmgateway.core

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class Sheet(@LayoutRes val contentLayoutId: Int) : BottomSheetDialogFragment(), PresentationStructure {
    override val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    var canMatchParent = false

    override fun initialize() {}
    override fun setup() {}
    override fun observe() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    @CallSuper
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply { setOnShowListener(this@Sheet::onShow) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(contentLayoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setup()
        observe()
    }

    /**
     * Set [canMatchParent] to true and call this method to make dialog fill full height.
     * Example of usage:
     * ```
     * override fun onShow(dialogInterface: DialogInterface) {
     *      canMatchParent = true
     *      super.onShow(dialogInterface)
     * }
     * ```
     * */
    @CallSuper
    open fun onShow(dialogInterface: DialogInterface) {
        if (canMatchParent) {
            val dialog = dialogInterface as BottomSheetDialog
            val sheet: FrameLayout? =
                dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)

            val layoutParams = sheet?.layoutParams

//            val windowHeight = screenResolution.height

//            layoutParams?.height = windowHeight
            sheet?.layoutParams = layoutParams
        }
    }
}