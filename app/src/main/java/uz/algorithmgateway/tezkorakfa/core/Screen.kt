package uz.algorithmgateway.core

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class Screen(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId),
    PresentationStructure {

    override val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
//
//    private var onStartListener: OnStartListener? = null
//    private var onStopListener: OnStopListener? = null

    override fun initialize() {}
    override fun setup() {}
    override fun observe() {}

//
//    open fun started(listener: OnStartListener) {
//        onStartListener = listener
//    }
//
//    open fun stopped(listener: OnStopListener) {
//        onStopListener = listener
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setup()
        observe()
    }

    override fun onStart() {
        super.onStart()
//        onStartListener?.onStart()
    }

    override fun onStop() {
        super.onStop()
//        onStopListener?.onStop()
    }

}