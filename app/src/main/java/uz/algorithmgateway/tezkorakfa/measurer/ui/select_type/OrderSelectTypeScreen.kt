package uz.algorithmgateway.tezkorakfa.measurer.ui.select_type

import android.annotation.SuppressLint
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
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.profile.Profile
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.window.Width
import uz.algorithmgateway.tezkorakfa.databinding.ScreenSelectTypeOrderBinding
import uz.algorithmgateway.tezkorakfa.measurer.SpinnerTextAdapter
import uz.algorithmgateway.tezkorakfa.measurer.ui.select_type.adapters.CardsAdapter
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
    lateinit var cardsAdapter: CardsAdapter

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
        window()
        shelf()

        navigateButton()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun shelf() {
        launch(Dispatchers.Main) {
            viewmodel.shelf.collect { shelf ->
                binding.layoutShelf.apply {
                    if (shelf != null) {
                        shelf.results.forEach {
                            tablayoutShelf.addTab(tablayoutShelf.newTab().setText(it.name))
                        }
                    }
                    val adapter = CardsAdapter(object : CardsAdapter.onPress {
                        override fun click(width: Width, position: Int) {

                        }
                    }, requireContext())
                    cardsRv.adapter = adapter

                    val cards = ArrayList<Width>()
                    shelf?.results?.get(0)?.width?.forEach {
                        cards.add(Width(id = it.id, name = it.name))
                    }
                    adapter.list = cards
                    adapter.notifyDataSetChanged()
                    tablayoutShelf.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
                        override fun onTabSelected(tab: TabLayout.Tab?) {
                            cards.clear()
                            shelf?.results?.get(tab?.position ?: 0)?.width?.forEach {
                                cards.add(Width(id = it.id, name = it.name))
                            }
                            adapter.list = cards
                            adapter.notifyDataSetChanged()
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

    @SuppressLint("ResourceAsColor", "NotifyDataSetChanged")
    private fun window() {
        launch(Dispatchers.Main) {
            viewmodel.window.collect { window ->
                binding.layoutWindow.apply {
                    val tabs = ArrayList<String>()
                    window?.results?.forEach {
                        tabs.add(it.name)
                        tabLayoutWindow.addTab(tabLayoutWindow.newTab().setText(it.name))
                    }
                    val spinnerColor = SpinnerTextAdapter(requireContext())
                    cardsAdapter = CardsAdapter(object : CardsAdapter.onPress {
                        override fun click(width: Width, position: Int) {
                            val color = ArrayList<String>()
                            if (window?.results?.get(tabLayoutWindow.selectedTabPosition)?.width?.isNotEmpty() == true
                            ) {
                                window.results[tabLayoutWindow.selectedTabPosition].width[position].color?.forEach {
                                    color.add(it.name)
                                }
                            }
                            spinnerColor.list = color
                            spinnerWindowColor.adapter = spinnerColor
                        }
                    }, requireContext())
                    cardsRv.adapter = cardsAdapter

                    val tabPosition = tabLayoutWindow.selectedTabPosition
                    if (tabPosition == 0) {
                        val list = window?.results?.get(tabPosition)?.width ?: emptyList()
                        cardsAdapter.list = list
                        cardsAdapter.notifyDataSetChanged()

                        val color = ArrayList<String>()
                        if (window?.results?.get(0)?.width?.isNotEmpty() == true
                        ) {
                            window.results[0].width[0].color?.forEach {
                                color.add(it.name)
                            }
                        }
                        spinnerColor.list = color
                        spinnerWindowColor.adapter = spinnerColor
                    }

                    tabLayoutWindow.addOnTabSelectedListener(object :
                        TabLayout.OnTabSelectedListener {
                        override fun onTabSelected(tab: TabLayout.Tab?) {
                            val list =
                                window?.results?.get(tab?.position ?: 0)?.width
                            if (list != null) {
                                cardsAdapter.list = list
                                cardsAdapter.notifyDataSetChanged()
                            }

                            val color = ArrayList<String>()
                            if (window?.results?.get(tab?.position
                                    ?: 0)?.width?.isNotEmpty() == true
                            ) {
                                window.results[tab?.position ?: 0].width[0].color?.forEach {
                                    color.add(it.name)
                                }
                            }
                            spinnerColor.list = color
                            spinnerColor.notifyDataSetChanged()
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
                    //profile
                    profile?.results?.get(0)?.type?.forEach {
                        spinnerProfile.add(it.name)
                    }
                    val spinnerProfileAdapter = SpinnerTextAdapter(requireContext())
                    spinnerProfileAdapter.list = spinnerProfile
                    spinnerTypeProfile.adapter = spinnerProfileAdapter

                    //texture
                    profile?.results?.get(0)?.type?.get(0)?.raw_material?.forEach {
                        spinnerTexture.add(it.name)
                    }
                    val spinnerTextureAdapter = SpinnerTextAdapter(requireContext())
                    spinnerTextureAdapter.list = spinnerTexture
                    spinnerTypeTexture.adapter = spinnerTextureAdapter

                    tabLayoutProfile.addOnTabSelectedListener(object :
                        TabLayout.OnTabSelectedListener {
                        override fun onTabSelected(tab: TabLayout.Tab?) {
                            //profile
                            spinnerProfile.clear()
                            profile?.results?.get(tab?.position ?: 0)?.type?.forEach {
                                spinnerProfile.add(it.name)
                            }
                            spinnerProfileAdapter.list = spinnerProfile
                            spinnerProfileAdapter.notifyDataSetChanged()

                            //texture
                            val selectedItemPosition = spinnerTypeProfile.selectedItemPosition
                            spinnerTexture.clear()
                            if (profile != null) {
                                if (tab != null) {
                                    if (profile.results[tab.position].type.isNotEmpty()) {
                                        if (profile.results[tab.position].type[0].raw_material.isNotEmpty()) {
                                            profile.results[tab.position].type[0].raw_material.forEach {
                                                spinnerTexture.add(it.name)
                                            }
                                        }
                                    }
                                }
                            }
                            spinnerTextureAdapter.list = spinnerTexture
                            spinnerTextureAdapter.notifyDataSetChanged()
                        }

                        override fun onTabUnselected(tab: TabLayout.Tab?) {

                        }

                        override fun onTabReselected(tab: TabLayout.Tab?) {

                        }
                    })

                    spinnerTypeProfile.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long,
                            ) {
                                //texture
                                val selectedItemPosition = spinnerTypeProfile.selectedItemPosition
                                val tab = tabLayoutProfile.selectedTabPosition
                                spinnerTexture.clear()
                                if (profile != null) {
                                    if (profile.results[tab].type.isNotEmpty()) {
                                        if (profile.results[tab].type[position].raw_material.isNotEmpty()) {
                                            profile.results[tab].type[position].raw_material.forEach {
                                                spinnerTexture.add(it.name)
                                            }
                                        }
                                    }
                                }
                                spinnerTextureAdapter.list = spinnerTexture
                                spinnerTextureAdapter.notifyDataSetChanged()
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }
                        }
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