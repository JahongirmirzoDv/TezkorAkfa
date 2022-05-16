package uz.algorithmgateway.tezkorakfa.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pdf(
    @PrimaryKey var id:String, var pdf: String, var signature: String? = null, var image: String? = null,
)
