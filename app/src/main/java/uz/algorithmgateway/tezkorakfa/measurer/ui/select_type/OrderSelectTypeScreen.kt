package uz.algorithmgateway.tezkorakfa.measurer.ui.select_type

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.algorithmgateway.data.models.UISpinner
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.ScreenSelectTypeOrderBinding
import uz.algorithmgateway.tezkorakfa.measurer.SpinnerMultiItemAdapter
import uz.algorithmgateway.tezkorakfa.measurer.SpinnerTextAdapter

class OrderSelectTypeScreen : Fragment() {

    private var _binding: ScreenSelectTypeOrderBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ScreenSelectTypeOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backClick()
        loadSpinnerDoorOrWindow()

        //profi
        loadTabProfilData()
        loadSpinnerProfilType()
        loadSpinnerTextureType()

        //window
        loadTabWindowData()
        loadSpinnerWindowType()

        //shelf
        loadTabShelfData()

        //accessory
        loadSpinnerAccessoryTexture()
        loadSpinnerAccessoryDastak()
        loadSpinnerAccessoryPetla()
        loadTabDastak()
        loadTabPetla()

        //cage
        loadTabCage()

        navigateButton()
    }

    private fun navigateButton() {
        binding.btnNext.setOnClickListener {
            navController.navigate(OrderSelectTypeScreenDirections.toItemCountScreen())
        }

        binding.btnBack.setOnClickListener {
            navController.navigateUp()
        }

    }

    private fun loadTabCage() {
        with(binding.layoutCage) {
            tablayoutCage.addTab(tablayoutCage.newTab().setText("550- sinax"))
            tablayoutCage.addTab(tablayoutCage.newTab().setText("544"))
            tablayoutCage.addTab(tablayoutCage.newTab().setText("Surma"))
        }
    }

    private fun loadTabPetla() {
        with(binding.layoutAccessory) {
            tabLayoutPetla.addTab(tabLayoutPetla.newTab().setText("Rom"))
            tabLayoutPetla.addTab(tabLayoutPetla.newTab().setText("Eshik"))
        }
    }

    private fun loadTabDastak() {
        with(binding.layoutAccessory) {
            tabLayoutDastak.addTab(tabLayoutDastak.newTab().setText("Rom"))
            tabLayoutDastak.addTab(tabLayoutDastak.newTab().setText("Eshik"))
        }
    }

    private fun loadSpinnerAccessoryPetla() {
        val adapter = SpinnerTextAdapter(requireContext(), listSpPetlaData(), true)
        binding.layoutAccessory.spinnerPetla.adapter = adapter
    }

    private fun listSpPetlaData(): List<String> {
        return arrayListOf<String>(
            "Akfa 28 fornax",
            "Akfa 28 fornax",
            "Akfa 28 fornax"
        )
    }

    private fun loadSpinnerAccessoryDastak() {
        val adapter = SpinnerTextAdapter(requireContext(), listSpDastakData(), true)
        binding.layoutAccessory.spinnerDastak.adapter = adapter
    }

    private fun listSpDastakData(): List<String> {
        return arrayListOf<String>(
            "Akfa 28 fornax",
            "Akfa 28 fornax",
            "Akfa 28 fornax"
        )
    }

    private fun loadSpinnerAccessoryTexture() {
        val adapter = SpinnerMultiItemAdapter(requireContext(), listAccessoryColorData())
        binding.layoutAccessory.spinnerTypeTexture.adapter = adapter
    }

    private fun listAccessoryColorData(): List<UISpinner> {
        return arrayListOf<UISpinner>(
            UISpinner(R.drawable.wood, "Zolotoy dub"),
            UISpinner(R.drawable.wood, "Zolotoy dub"),
            UISpinner(R.drawable.wood, "Zolotoy dub")
        )
    }

    private fun loadTabShelfData() {
        with(binding.layoutShelf) {
            tablayoutShelf.addTab(tablayoutShelf.newTab().setText("Universal"))
            tablayoutShelf.addTab(tablayoutShelf.newTab().setText("Elita"))
            tablayoutShelf.addTab(tablayoutShelf.newTab().setText("Lux"))
        }
    }

    private fun loadSpinnerWindowType() {
        val adapter = SpinnerMultiItemAdapter(requireContext(), listWindowColorData())
        binding.layoutWindow.spinnerWindowColor.adapter = adapter
    }

    private fun loadTabWindowData() {
        with(binding.layoutWindow) {
            tabLayoutWindow.addTab(tabLayoutWindow.newTab().setText("1-qavat"))
            tabLayoutWindow.addTab(tabLayoutWindow.newTab().setText("2-qavat"))
        }
    }

    private fun loadSpinnerTextureType() {
        val adapter = SpinnerMultiItemAdapter(requireContext(), listProfiTextureData())
        binding.layoutProfile.spinnerTypeTexture.adapter = adapter
    }

    private fun listProfiTextureData() = arrayListOf<UISpinner>(
        UISpinner(R.drawable.wood, "Zolotoy dub"),
        UISpinner(R.drawable.wood, "Kumush dub"),
        UISpinner(R.drawable.wood, "Bronza dub"),
        UISpinner(R.drawable.wood, "Zolotoy dub"),
    )

    private fun listWindowColorData() = arrayListOf<UISpinner>(
        UISpinner(R.drawable.ic_windows, "Yodoviy"),
        UISpinner(R.drawable.ic_windows, "Yodoviy"),
        UISpinner(R.drawable.ic_windows, "Yodoviy")
    )


    private fun loadSpinnerProfilType() {
        val adapter = SpinnerTextAdapter(requireContext(), listProfiTypeData(), true)
        binding.layoutProfile.spinnerTypeProfile.adapter = adapter
    }

    private fun listProfiTypeData() = arrayListOf<String>(
        "7000 Engelberg",
        "7200 Engelberg",
        "7400 Engelberg"
    )

    private fun loadSpinnerDoorOrWindow() {
        val adapter = SpinnerTextAdapter(requireContext(), listDoorOrWindowData(), false)
        binding.spinnerRoomOrDoor.adapter = adapter
    }

    private fun listDoorOrWindowData() = arrayListOf<String>(
        "Eshik",
        "Oyna"
    )

    private fun backClick() {
        binding.imageBack.setOnClickListener {
            navController.navigateUp()
        }
    }

    private fun loadTabProfilData() {
        with(binding.layoutProfile) {
            tabLayoutProfile.addTab(tabLayoutProfile.newTab().setText("Aldoks"))
            tabLayoutProfile.addTab(tabLayoutProfile.newTab().setText("PVH"))
            tabLayoutProfile.addTab(tabLayoutProfile.newTab().setText("Exclusive"))
        }
    }

}