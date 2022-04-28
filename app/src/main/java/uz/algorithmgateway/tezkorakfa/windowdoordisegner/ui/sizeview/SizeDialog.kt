package uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.sizeview

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import uz.algorithmgateway.core.util.toast
import uz.algorithmgateway.tezkorakfa.databinding.ActivityDesignerBinding
import uz.algorithmgateway.tezkorakfa.databinding.LayoutChangeSizeDialogBinding


class SizeDialog : DialogFragment() {

    private lateinit var dialogBinding: LayoutChangeSizeDialogBinding
    private lateinit var binding: ActivityDesignerBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
//            val inflater = requireActivity().layoutInflater
//            val dialogView = inflater.inflate(R.layout.layout_change_size_dialog, null)
            dialogBinding =
                LayoutChangeSizeDialogBinding.inflate(layoutInflater)
            dialogBinding.apply {
                btn0.setOnClickListener { setview(0) }
                btn1.setOnClickListener { setview(1) }
                btn2.setOnClickListener { setview(2) }
                btn3.setOnClickListener { setview(3) }
                btn4.setOnClickListener { setview(4) }
                btn5.setOnClickListener { setview(5) }
                btn6.setOnClickListener { setview(6) }
                btn7.setOnClickListener { setview(7) }
                btn8.setOnClickListener { setview(8) }
                btn9.setOnClickListener { setview(9) }

                ivBackspace.setOnClickListener { backspace() }

                btnOk.setOnClickListener { okBtnClick() }
                btnCancel.setOnClickListener { dialog?.dismiss() }
            }

            binding = ActivityDesignerBinding.inflate(layoutInflater)


            builder.setView(dialogBinding.root)

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun okBtnClick() {
        dialogBinding.apply {
            val H = etSize.text.toString().toInt()
            toast("$H")
        }
    }

    private fun setview(number: Int) {
        val text = dialogBinding.etSize.text.toString()
        dialogBinding.etSize.setText(text.plus(number.toString()))
    }

    private fun backspace() {
        dialogBinding.apply {
            val text = etSize.text
            if (text != null) {
                if (text.length > 0) {
                    etSize.setText(text?.substring(0, text.length.minus(1)))
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}