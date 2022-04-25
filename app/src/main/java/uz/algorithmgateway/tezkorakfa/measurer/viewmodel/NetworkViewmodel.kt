package uz.algorithmgateway.tezkorakfa.measurer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.algorithmgateway.tezkorakfa.data.models.profile.Profile
import uz.algorithmgateway.tezkorakfa.data.retrofit.ApiService
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.OderList
import uz.algorithmgateway.tezkorakfa.ui.utils.UIState
import javax.inject.Inject

class NetworkViewmodel @Inject constructor(
    val apiService: ApiService,
) : ViewModel() {
    init {
        getProfile()
    }

    private val _order = MutableStateFlow<Profile?>(null)
    val order: StateFlow<Profile?>
        get() = _order

    private fun getProfile() {
        viewModelScope.launch {
            val responce = apiService.getProfile()
            if (responce.isSuccessful) {
                _order.value = responce.body()
            } else {

            }
        }
    }
}