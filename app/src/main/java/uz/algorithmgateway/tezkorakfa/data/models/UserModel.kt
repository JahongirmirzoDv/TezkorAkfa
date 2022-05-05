package uz.algorithmgateway.tezkorakfa.data.models

import com.google.gson.annotations.SerializedName


data class UserResponse(

    @SerializedName("user_id")
    val userId: Int,

    @SerializedName("token")
    val token: String,

    @SerializedName("role_id")
    val roleId: String
)

data class UserRequest(
    @SerializedName("phone_number")
    val phoneName: String,

    @SerializedName("password")
    val password: String
)
