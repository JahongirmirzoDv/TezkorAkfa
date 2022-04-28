package uz.algorithmgateway.tezkorakfa.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.algorithmgateway.data.api.models.UserRequest
import uz.algorithmgateway.data.api.models.UserResponse
import uz.algorithmgateway.tezkorakfa.data.retrofit.ApiService
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.OderList
import uz.algorithmgateway.tezkorakfa.data.retrofit.repository.NetworkRepository
import uz.algorithmgateway.tezkorakfa.ui.utils.UIState
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    var apiService: ApiService,
    var networkRepository: NetworkRepository,
) : ViewModel() {
    private val _state = MutableStateFlow<UIState<UserResponse?>>(UIState.Loading)
    val state: StateFlow<UIState<UserResponse?>>
        get() = _state

    fun loginUser(phone: String, password: String) {
        viewModelScope.launch {
            val responce = apiService.loginUser(UserRequest(phone, password))
            if (responce.isSuccessful) {
                _state.value = UIState.Success(responce.body())
            } else _state.value = UIState.Error(responce.message())
        }
    }

    private val _order = MutableStateFlow<UIState<OderList?>>(UIState.Loading)
    val order: StateFlow<UIState<OderList?>>
        get() = _order

    fun getOrder() {
        viewModelScope.launch {
            networkRepository.salesOrderList()
                .catch {
                    _order.emit(UIState.Error("Internet bilan muammo, Qayta urining"))
                }.collect {
                    if (it.isSuccess) {
                        _order.emit(UIState.Success(it.getOrNull()))
                    } else if (it.isFailure) {
                        _order.emit(UIState.Error("Server bilan muammo"))
                    }
                }
        }
    }
}