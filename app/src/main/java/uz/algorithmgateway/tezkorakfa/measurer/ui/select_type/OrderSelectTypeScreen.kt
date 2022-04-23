package uz.algorithmgateway.tezkorakfa.measurer.ui.select_type

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.data.models.UISpinner
import uz.algorithmgateway.tezkorakfa.databinding.ScreenSelectTypeOrderBinding
import uz.algorithmgateway.tezkorakfa.measurer.SpinnerMultiItemAdapter
import uz.algorithmgateway.tezkorakfa.measurer.SpinnerTextAdapter
import uz.algorithmgateway.tezkorakfa.measurer.ui.select_type.models.Drawing
import uz.algorithmgateway.tezkorakfa.measurer.viewmodel.DbViewmodel
import javax.inject.Inject

class OrderSelectTypeScreen : Fragment() {
    @Inject
    lateinit var dbViewmodel: DbViewmodel

    lateinit var id: String
    private var _binding: ScreenSelectTypeOrderBinding? = null
    private val binding get() = _binding!!
    lateinit var drawing: Drawing
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    var profile_List = arrayListOf("Aldoks", "PVH", "Exclusive")
    var mirror_layer_List = arrayListOf("1-qavat", "2-qavat")
    var shelf_List = arrayListOf("Universal", "Elita", "Lux")
    var type_handle = arrayListOf("Rom", "Eshik")
    var type_cage = arrayListOf("550- sinax", "544", "Surma")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.ordersSelect(this)
        arguments.let {
            id = it?.getString("id").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ScreenSelectTypeOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.projectId.text = "Loyiha $id"
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
            binding.apply {
                val type = binding.spinnerRoomOrDoor.selectedItem.toString()
                val external_or_Internal = when (binding.radioGroup.checkedRadioButtonId) {
                    R.id.radioOut -> {
                        "tashqi"
                    }
                    else -> {
                        "ichki"
                    }
                }
                val profile_type =
                    profile_List[binding.layoutProfile.tabLayoutProfile.selectedTabPosition]
                val profile_type_two =
                    binding.layoutProfile.spinnerTypeProfile.selectedItem.toString()
                val uiSpinner1 = binding.layoutProfile.spinnerTypeTexture.selectedItem as UISpinner
                val profile_texture = uiSpinner1.title
                val mirror_layer =
                    mirror_layer_List[binding.layoutWindow.tabLayoutWindow.selectedTabPosition]
                val uiSpinner = binding.layoutWindow.spinnerWindowColor.selectedItem as UISpinner
                val mirror_color = uiSpinner.title
                val window_sill = shelf_List[binding.layoutShelf.tablayoutShelf.selectedTabPosition]
                val handle = binding.layoutAccessory.spinnerDastak.selectedItem.toString()
                val handle_petla = binding.layoutAccessory.spinnerPetla.selectedItem.toString()
                val handle_texture =
                    binding.layoutAccessory.spinnerTypeTexture.selectedItem.toString()
                val handle_type =
                    type_handle[binding.layoutAccessory.tabLayoutDastak.selectedTabPosition]
                val handle_petla_type =
                    type_handle[binding.layoutAccessory.tabLayoutPetla.selectedTabPosition]
                val net = binding.comment.text.toString()
                drawing = Drawing(
                    id,
                    type,
                    external_or_Internal,
                    profile_type,
                    profile_type_two,
                    profile_texture,
                    mirror_layer,
                    mirror_color,
                    window_sill,
                    handle,
                    handle_petla,
                    handle_texture,
                    handle_type,
                    handle_petla_type,
                    net
                )
                dbViewmodel.addDrawing(drawing)
            }
            val bundle = Bundle()
            val toJson = Gson().toJson(drawing)
            bundle.putString("id", id)
            bundle.putString("drawing", toJson)
            navController.navigate(R.id.itemCountScreen, bundle)
        }

        binding.btnBack.setOnClickListener {
            navController.navigateUp()
        }

    }

    private fun loadTabCage() {
//        with(binding.layoutCage) {
//            tablayoutCage.addTab(tablayoutCage.newTab().setText("550- sinax"))
//            tablayoutCage.addTab(tablayoutCage.newTab().setText("544"))
//            tablayoutCage.addTab(tablayoutCage.newTab().setText("Surma"))
//        }
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