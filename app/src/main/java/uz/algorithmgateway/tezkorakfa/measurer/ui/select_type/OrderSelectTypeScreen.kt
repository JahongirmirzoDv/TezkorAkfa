package uz.algorithmgateway.tezkorakfa.measurer.ui.select_type

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.data.models.UISpinner
import uz.algorithmgateway.tezkorakfa.data.models.profile.Profile
import uz.algorithmgateway.tezkorakfa.databinding.ScreenSelectTypeOrderBinding
import uz.algorithmgateway.tezkorakfa.measurer.SpinnerTextAdapter
import uz.algorithmgateway.tezkorakfa.measurer.ui.select_type.models.Drawing
import uz.algorithmgateway.tezkorakfa.measurer.viewmodel.DbViewmodel
import uz.algorithmgateway.tezkorakfa.measurer.viewmodel.NetworkViewmodel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class OrderSelectTypeScreen : Fragment(), CoroutineScope {
    @Inject
    lateinit var dbViewmodel: DbViewmodel

    @Inject
    lateinit var viewmodel: NetworkViewmodel

    lateinit var id: String
    private var _binding: ScreenSelectTypeOrderBinding? = null
    private val binding get() = _binding!!
    lateinit var drawing: Drawing
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    var profile_List: Profile? = null
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
        profile()

        navigateButton()
    }

    private fun profile() {
        launch(Dispatchers.Main) {
            viewmodel.order.collect { profile ->
                val profileType = ArrayList<String>()
                val spinnerProfile = ArrayList<String>()
                val spinnerTexture = ArrayList<String>()

                profile?.results?.forEach { profile2 ->
                    profileType.add(profile2.name)
                }
                binding.layoutProfile.apply {
                    profileType.forEach {
                        tabLayoutProfile.addTab(tabLayoutProfile.newTab().setText(it))
                    }

                    profile?.results?.get(0)?.type?.forEach {
                        spinnerProfile.add(it.name)
                    }
                    val spinnerProfileAdapter = SpinnerTextAdapter(requireContext())
                    spinnerProfileAdapter.list = spinnerProfile
                    spinnerTypeProfile.adapter = spinnerProfileAdapter

                    tabLayoutProfile.addOnTabSelectedListener(object :
                        TabLayout.OnTabSelectedListener {
                        override fun onTabSelected(tab: TabLayout.Tab?) {
                            spinnerProfile.clear()
                            profile?.results?.get(tab?.position ?: 0)?.type?.forEach {
                                spinnerProfile.add(it.name)
                            }
                            spinnerProfileAdapter.list = spinnerProfile
                            spinnerProfileAdapter.notifyDataSetChanged()

                            profile?.results?.get(0)?.type?.forEach {
                                spinnerProfile.add(it.raw_material[0].name)
                            }
                            val spinnerTextureAdapter = SpinnerTextAdapter(requireContext())
                            spinnerTextureAdapter.list = spinnerTexture
                            spinnerTypeTexture.adapter = spinnerTextureAdapter
                            spinnerTypeProfile.onItemSelectedListener =
                                object : AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        parent: AdapterView<*>?,
                                        view: View?,
                                        position: Int,
                                        id: Long,
                                    ) {
                                        spinnerTexture.clear()
                                        profile?.results?.get(tab?.position ?: 0)?.type?.forEach {
                                            spinnerTexture.add(it.raw_material[position].name)
                                        }
                                        spinnerTextureAdapter.list = spinnerTexture
                                        spinnerTextureAdapter.notifyDataSetChanged()
                                    }

                                    override fun onNothingSelected(parent: AdapterView<*>?) {

                                    }

                                }
                        }

                        override fun onTabUnselected(tab: TabLayout.Tab?) {

                        }

                        override fun onTabReselected(tab: TabLayout.Tab?) {

                        }
                    })


                }
            }
        }
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
                    profile_List?.results?.get(binding.layoutProfile.tabLayoutProfile.selectedTabPosition)?.name
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


    private fun loadSpinnerDoorOrWindow() {
        val adapter = SpinnerTextAdapter(requireContext())
        adapter.list = listDoorOrWindowData()
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


    override val coroutineContext: CoroutineContext
        get() = Job()
}