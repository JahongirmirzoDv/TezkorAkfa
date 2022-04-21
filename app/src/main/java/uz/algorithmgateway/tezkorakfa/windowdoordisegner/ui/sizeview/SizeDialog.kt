package uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.sizeview

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import uz.algorithmgateway.tezkorakfa.R


class SizeDialog : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.layout_change_size_dialog, null)

            val layoutNumbers = dialogView.findViewById<LinearLayout>(R.id.ll_numbers)
            for (i in 0 until layoutNumbers.childCount) {
                if (layoutNumbers.getChildAt(i).tag != null) {
                    onNumberClick(layoutNumbers.getChildAt(i).tag.toString())
                }
            }


            builder.setView(dialogView)

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun onNumberClick(number: String) {

    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}