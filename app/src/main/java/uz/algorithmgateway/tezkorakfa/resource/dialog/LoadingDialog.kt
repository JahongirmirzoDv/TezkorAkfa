package uz.algorithmgateway.tezkorakfa.resource.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import uz.algorithmgateway.tezkorakfa.R


/**
 * Created by Abrorjon Berdiyorov on 29.03.2022
 */

class LoadingDialog(context: Context) : AlertDialog(context) {
    private var builder: Builder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        builder = Builder(context, 0)
        val customLayout: View = layoutInflater
            .inflate(
                R.layout.layout_loading_dialog,
                null
            )
        builder!!.setView(customLayout)
    }

    override fun show() {
        builder?.create()?.show()
    }

}