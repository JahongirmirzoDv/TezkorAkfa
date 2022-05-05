package uz.algorithmgateway.tezkorakfa.measurer.ui.customer_photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

class CustomerSendDataViewModel @Inject constructor(private val retrofit: Retrofit) : ViewModel() {


    fun sendData(): StateFlow<CusterSendResource> {
        val flow = MutableStateFlow<CusterSendResource>(CusterSendResource.Loading)

        viewModelScope.launch {


        }

        return flow
    }
}