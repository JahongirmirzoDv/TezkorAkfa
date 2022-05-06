package uz.algorithmgateway.tezkorakfa.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.algorithmgateway.tezkorakfa.data.models.UserRequest
import uz.algorithmgateway.tezkorakfa.data.models.UserResponse
import uz.algorithmgateway.tezkorakfa.data.retrofit.ApiService
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.OrderList
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
            networkRepository.loginUser(UserRequest(phone, password))
                .catch {
                    _state.value = UIState.Error(it.message.toString())
                }.collect {
                    if (it.isSuccess) {
                        _state.value = UIState.Success(it.getOrNull())
                    } else if (it.isFailure) {
                        _state.value = UIState.Error((it.exceptionOrNull()?.message ?: it.exceptionOrNull()).toString())
                    }
                }
        }
    }

    private val _order = MutableStateFlow<UIState<OrderList?>>(UIState.Loading)
    val order: StateFlow<UIState<OrderList?>>
        get() = _order

    fun getOrder(status:String) {
        viewModelScope.launch {
            networkRepository.salesOrderList(status)
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