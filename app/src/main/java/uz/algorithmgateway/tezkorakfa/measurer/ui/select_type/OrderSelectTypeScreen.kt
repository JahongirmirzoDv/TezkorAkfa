package uz.algorithmgateway.tezkorakfa.measurer.ui.select_type

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.base.MyApplication
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

    var id: String? = null
    private var _binding: ScreenSelectTypeOrderBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    lateinit var cardsAdapter: CardsAdapter
    lateinit var drawing: Drawing

    var oyna_ = ""
    var shelf_ = ""
    var mirror_layer_List = arrayListOf("1-qavat", "2-qavat")
    var shelf_List = arrayListOf("Universal", "Elita", "Lux")
    var profile_type = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.ordersSelect(this)
        arguments.let {
            id = it?.getString("id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ScreenSelectTypeOrderBinding.inflate(inflater, container, false)
        drawing = dbViewmodel.getAllDrawing().last()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (id != null) binding.projectId.text =
            "Loyiha ${drawing.id}_$id" else binding.projectId.text = "Loyiha ${drawing.id}"
        backClick()
        loadSpinnerDoorOrWindow()
        profile()
        window()
        shelf()
        accessories()

        navigateButton()
    }

    private fun accessories() {
        launch(Dispatchers.Main) {
            viewmodel.accessory.collect { access ->
                binding.layoutAccessory.apply {
                    if (access != null) {
                        val petlaAdapter = SpinnerTextAdapter(requireContext())
                        spinnerAccType.adapter = petlaAdapter
                        val petlaList = ArrayList<String>()

                        val dastakAdapter = SpinnerTextAdapter(requireContext())
                        spinnerRaw.adapter = dastakAdapter
                        val dastakList = ArrayList<String>()

                        val textureAdapter = SpinnerTextAdapter(requireContext())
                        spinnerTypeTexture.adapter = textureAdapter
                        val textureList = ArrayList<String>()

                        access.results.forEach { result ->
                            tabLayoutType.addTab(tabLayoutType.newTab().setText(result.name))
                        }
                        access.results[0].type.forEach { type ->
                            petlaList.add(type.type)
                            petlaAdapter.list = petlaList
                            petlaAdapter.notifyDataSetChanged()
                        }
                        access.results[0].type[0].raw_material.forEach { raw ->
                            dastakList.add(raw.name)
                            dastakAdapter.list = dastakList
                            dastakAdapter.notifyDataSetChanged()
                        }
                        access.results[0].type[0].raw_material[0].color.forEach { color ->
                            textureList.add(color.name)
                            textureAdapter.list = textureList
                            textureAdapter.notifyDataSetChanged()
                        }
                        tabLayoutType.addOnTabSelectedListener(object :
                            TabLayout.OnTabSelectedListener {
                            override fun onTabSelected(tab: TabLayout.Tab?) {
                                dastakList.clear()
                                petlaList.clear()
                                textureList.clear()
                                access.results[tab?.position ?: 0].type.forEach { type ->
                                    petlaList.add(type.type)
                                    petlaAdapter.list = petlaList
                                    petlaAdapter.notifyDataSetChanged()
                                }
                                val selectedItemPosition = spinnerAccType.selectedItemPosition
                                if (access.results[tab?.position
                                        ?: 0].type[selectedItemPosition].raw_material.isNotEmpty()
                                ) {
                                    access.results[tab?.position
                                        ?: 0].type[selectedItemPosition].raw_material.forEach { raw ->
                                        dastakList.add(raw.name)
                                        dastakAdapter.list = dastakList
                                        dastakAdapter.notifyDataSetChanged()
                                    }
                                    access.results[tab?.position
                                        ?: 0].type[selectedItemPosition].raw_material[0].color.forEach { color ->
                                        textureList.add(color.name)
                                        textureAdapter.list = textureList
                                        textureAdapter.notifyDataSetChanged()
                                    }
                                }
                            }

                            override fun onTabUnselected(tab: TabLayout.Tab?) {

                            }

                            override fun onTabReselected(tab: TabLayout.Tab?) {

                            }

                        })
                        spinnerAccType.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long,
                                ) {
                                    dastakList.clear()
                                    textureList.clear()
                                    access.results[tabLayoutType.selectedTabPosition].type[position].raw_material.forEach { raw ->
                                        dastakList.add(raw.name)
                                        dastakAdapter.list = dastakList
                                        dastakAdapter.notifyDataSetChanged()
                                    }
                                    access.results[tabLayoutType.selectedTabPosition].type[position].raw_material[0].color.forEach { color ->
                                        textureList.add(color.name)
                                        textureAdapter.list = textureList
                                        textureAdapter.notifyDataSetChanged()
                                    }
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {

                                }

                            }
                        spinnerRaw.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long,
                                ) {
                                    textureList.clear()
                                    access.results[tabLayoutType.selectedTabPosition].type[spinnerRaw.selectedItemPosition].raw_material[position].color.forEach { color ->
                                        textureList.add(color.name)
                                        textureAdapter.list = textureList
                                        textureAdapter.notifyDataSetChanged()
                                    }
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {

                                }

                            }
                    }
                }
            }
        }
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
                            shelf_ = width.name
                        }
                    }, requireContext())
                    cardsRv.adapter = adapter

                    val cards = ArrayList<Width>()
                    shelf?.results?.get(0)?.width?.forEach {
                        cards.add(Width(id = it.id, name = it.name))
                    }
                    adapter.list = cards
                    adapter.notifyDataSetChanged()
                    tablayoutShelf.addOnTabSelectedListener(object :
                        TabLayout.OnTabSelectedListener {
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
                            oyna_ = width.name
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
                            if (window?.results?.get(
                                    tab?.position
                                        ?: 0
                                )?.width?.isNotEmpty() == true
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
                    profile_type = profile?.results?.get(0)?.name ?: ""
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
                            profile_type = profile?.results?.get(tab?.position ?: 0)?.name ?: ""
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
                val profile_type = profile_type
                val profile_type_two =
                    binding.layoutProfile.spinnerTypeProfile.selectedItem.toString().ifEmpty { "" }
                val uiSpinner1 =
                    binding.layoutProfile.spinnerTypeTexture.selectedItem.toString().ifEmpty { "" }
                val profile_texture = uiSpinner1
                val mirror_layer =
                    mirror_layer_List[binding.layoutWindow.tabLayoutWindow.selectedTabPosition]
                val mirror_mm = oyna_
                val uiSpinner =
                    binding.layoutWindow.spinnerWindowColor.selectedItem.toString().ifEmpty { "" }
                val mirror_color = uiSpinner
                val window_sill = shelf_List[binding.layoutShelf.tablayoutShelf.selectedTabPosition]
                val sill_mm = shelf_
                val sill_comment = binding.layoutShelf.comment.text.toString().ifEmpty { "" }
                val handle =
                    binding.layoutAccessory.spinnerAccType.selectedItem.toString().ifEmpty { "" }
                val handle_petla =
                    binding.layoutAccessory.spinnerRaw.selectedItem.toString().ifEmpty { "" }
                val handle_texture =
                    binding.layoutAccessory.spinnerTypeTexture.selectedItem.toString()
                        .ifEmpty { "" }
                val net = binding.comment.text.toString().ifEmpty { "" }
                drawing = Drawing(
                    id = if (id != null) "${drawing.id}_$id" else drawing.id,
                    type,
                    drawing.count,
                    drawing.type_type,
                    external_or_Internal,
                    profile_type,
                    profile_type_two,
                    profile_texture,
                    mirror_layer,
                    mirror_mm,
                    mirror_color,
                    window_sill,
                    sill_mm,
                    sill_comment,
                    handle,
                    handle_petla,
                    handle_texture,
                    net
                )
                Log.e("TAG", "navigateButton: $id")
                if (id != null) dbViewmodel.addDrawing(drawing) else dbViewmodel.updateDrawing(
                    drawing)
            }
            navController.navigate(R.id.itemCountScreen)
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

    override fun onDestroy() {
        super.onDestroy()
        dbViewmodel.delete()
    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}