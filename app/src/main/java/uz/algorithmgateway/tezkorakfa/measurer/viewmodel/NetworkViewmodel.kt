package uz.algorithmgateway.tezkorakfa.measurer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.algorithmgateway.tezkorakfa.data.retrofit.ApiService
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.accessory.Accessory
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.profile.Profile
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.shelf.Shelf
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.window.Windows
import uz.algorithmgateway.tezkorakfa.data.retrofit.repository.NetworkRepository
import javax.inject.Inject

class NetworkViewmodel @Inject constructor(
    val apiService: ApiService,
    var networkRepository: NetworkRepository,
) : ViewModel() {
    init {
        getProfile()
        getWindow()
        getShelf()
        getAccessory()
    }

    private val _order = MutableStateFlow<Profile?>(null)
    val order: StateFlow<Profile?>
        get() = _order

    private fun getProfile() {
        viewModelScope.launch {
            networkRepository.getProfile().catch {

            }.collect {
                if (it.isSuccess) {
                    _order.emit(it.getOrNull())
                } else if (it.isFailure) _order.emit(null)
            }
        }
    }

    private val _window = MutableStateFlow<Windows?>(null)
    val window: StateFlow<Windows?>
        get() = _window

    private fun getWindow() {
        viewModelScope.launch {
            networkRepository.getWindow().catch {

            }.collect {
                if (it.isSuccess) {
                    _window.emit(it.getOrNull())
                } else if (it.isFailure) _window.emit(null)
            }
        }
    }


    private val _shelf = MutableStateFlow<Shelf?>(null)
    val shelf: StateFlow<Shelf?>
        get() = _shelf

    private fun getShelf() {
        viewModelScope.launch {
            networkRepository.getShelf().catch {

            }.collect {
                if (it.isSuccess) {
                    _shelf.emit(it.getOrNull())
                } else if (it.isFailure) _shelf.emit(null)
            }
        }
    }


    private val _accessory = MutableStateFlow<Accessory?>(null)
    val accessory: StateFlow<Accessory?>
        get() = _accessory


    private fun getAccessory() {
        viewModelScope.launch {
            networkRepository.getAccsessory().catch {

            }.collect {
                if (it.isSuccess) {
                    _accessory.emit(it.getOrNull())
                } else if (it.isFailure) _accessory.emit(null)
            }
        }
    }
}