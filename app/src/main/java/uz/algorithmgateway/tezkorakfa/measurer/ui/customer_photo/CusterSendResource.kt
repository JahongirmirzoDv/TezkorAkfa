package uz.algorithmgateway.tezkorakfa.measurer.ui.customer_photo

sealed class CusterSendResource {

    object Loading : CusterSendResource()
    data class Error(val message: String) : CusterSendResource()
    data class Succful(val succful: Boolean) : CusterSendResource()

}