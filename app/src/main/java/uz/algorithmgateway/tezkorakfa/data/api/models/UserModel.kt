package uz.algorithmgateway.data.api.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Abrorjon Berdiyorov on 11.03.2022
 */

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
