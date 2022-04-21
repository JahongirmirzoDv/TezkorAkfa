package uz.algorithmgateway.data.api.models

import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("count")
    val count: Int,

    @SerializedName("next")
    val next: String?,

    @SerializedName("previous")
    val previous: String?,

    @SerializedName("results")
    val results: List<Order>
)

data class Order(
    @SerializedName("id")
    val id: Int,

    @SerializedName("contract_number")
    val contractNumber: String,

    @SerializedName("client")
    val client: Client,

    @SerializedName("measurement_time")
    val measurementTime: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("comment")
    val comment: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("district")
    val district: Int,

    @SerializedName("show_room")
    val showRoom: ShowRoom,

    @SerializedName("tm")
    val tm: String
)


data class Client(
    @SerializedName("id")
    val id: Int,

    @SerializedName("created_date")
    val createdDate: String,

    @SerializedName("modified_date")
    val modifiedDate: String,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,

    @SerializedName("main_phone_number")
    val mainPhoneNumber: String,

    @SerializedName("phone_number")
    val phoneNumber: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("comment")
    val comment: String,

    @SerializedName("found")
    val found: String,

    @SerializedName("district")
    val district: Int
)

data class ShowRoom(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("district")
    val district: District,
)

data class District(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,
)