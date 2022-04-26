package uz.algorithmgateway.tezkorakfa.measurer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.algorithmgateway.tezkorakfa.data.retrofit.ApiService
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.profile.Profile
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.window.Windows
import uz.algorithmgateway.tezkorakfa.measurer.ui.accept_order.model.Locations
import javax.inject.Inject

class NetworkViewmodel @Inject constructor(
    val apiService: ApiService,
) : ViewModel() {
    init {
        getProfile()
        getWindow()
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

    private val _window = MutableStateFlow<Windows?>(null)
    val window: StateFlow<Windows?>
        get() = _window

    private fun getWindow() {
        viewModelScope.launch {
            val responce = apiService.getWindow()
            if (responce.isSuccessful) {
                _window.value = responce.body()
            } else {

            }
        }
    }

}