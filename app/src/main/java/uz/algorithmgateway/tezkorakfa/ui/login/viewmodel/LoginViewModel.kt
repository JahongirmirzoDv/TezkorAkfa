package uz.algorithmgateway.tezkorakfa.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.algorithmgateway.data.api.models.UserRequest
import uz.algorithmgateway.data.api.models.UserResponse
import uz.algorithmgateway.tezkorakfa.data.retrofit.ApiService
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.OderList
import uz.algorithmgateway.tezkorakfa.ui.utils.UIState
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    var apiService: ApiService,
) : ViewModel() {
    private val _state = MutableStateFlow<UIState<UserResponse?>>(UIState.Loading)
    val state: StateFlow<UIState<UserResponse?>>
        get() = _state

    fun loginUser(phone: String, password: String) {
        viewModelScope.launch {
            var responce = apiService.loginUser(UserRequest(phone, password))
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
            coroutineScope {
                var responce = apiService.salesOrderList()
                if (responce.isSuccessful) {
                    _order.value = UIState.Success(responce.body())
                } else _order.value = UIState.Error(responce.message())
            }
        }
    }

}