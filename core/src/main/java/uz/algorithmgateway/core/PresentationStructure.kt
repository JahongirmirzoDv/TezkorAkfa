package uz.algorithmgateway.core

import androidx.navigation.NavController

/**
 * Internal interface used to give presentation rules and order to ui.
 * It can be used only inside this module and can not be shared with others
 * */
internal interface PresentationStructure {
    /**
     * Navigation controller to navigate between destinations
     * */
    val navController: NavController

    /**
     * Calls when presentation initialized before showing view
     * */
    fun initialize()

    /**
     * Calls when after view initialized
     * */
    fun setup()

    /**
     * Calls to observe or listen events
     * */
    fun observe()
}