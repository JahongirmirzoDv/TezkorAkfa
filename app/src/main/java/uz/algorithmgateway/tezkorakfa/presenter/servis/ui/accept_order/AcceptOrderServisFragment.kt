package uz.algorithmgateway.tezkorakfa.presenter.servis.ui.accept_order

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.AlertDialogMontageBinding
import uz.algorithmgateway.tezkorakfa.databinding.ConfirmationSmsCodeServisBinding
import uz.algorithmgateway.tezkorakfa.databinding.FragmentAcceptOrderServisBinding
import uz.algorithmgateway.tezkorakfa.presenter.servis.ui.home.ServisHomeFragment


class AcceptOrderServisFragment : Fragment() {
    private var _binding: FragmentAcceptOrderServisBinding? = null
    private val binding get() = _binding ?: throw NullPointerException("null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAcceptOrderServisBinding.inflate(inflater, container, false)

        installToolbar()
        return binding.root
    }

    private fun installToolbar() {
        binding.toolbar.apply {
            backArrowFragment.setOnClickListener {
                findNavController().popBackStack()
            }
            otherToolbar.visibility = View.GONE
            searchToolbar.visibility = View.GONE
        }
        binding.configOrderBtn.setOnClickListener {
            showAlertDialog()
        }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = ConfirmationSmsCodeServisBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        dialogBinding.configOrderBtn.setOnClickListener {
            alertDialog.dismiss()
            showAlertDialog2()
        }

        alertDialog.show()
    }

    private fun showAlertDialog2() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = AlertDialogMontageBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        dialogBinding.configOrderBtn.setOnClickListener {
            alertDialog.dismiss()
//            ((AppCompatActivity)getContext()).getSupportFragmentManager().popBackStack(String name, FragmentManager.POP_BACK_STACK_INCLUSIVE)

//            val fm = activity?.supportFragmentManager
//            for (i in 0 until (fm?.backStackEntryCount ?: 1)) {
//                fm?.popBackStack()
//            }
//            childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            findNavController().navigate(R.id.servisHomeFragment,null,
                NavOptions.Builder().setPopUpTo(findNavController().graph.startDestinationId, true).build())
        }

        alertDialog.show()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}