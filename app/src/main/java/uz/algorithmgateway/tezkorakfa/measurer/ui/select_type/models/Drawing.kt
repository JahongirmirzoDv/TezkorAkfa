package uz.algorithmgateway.tezkorakfa.measurer.ui.select_type.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Drawing(
    @PrimaryKey
    var id: String,
    var type: String,
    var external_or_internal: String,
    var profile_type: String?,
    var profile_type_two: String,
    var profile_texture: String,
    var mirror_layer: String,
    var mirror_color: String,
    var window_sill: String,
    var handle: String,
    var handle_petla: String,
    var handle_texture: String,
    var handle_type: String,
    var handle_petla_type: String,
    var net: String,
    var projet_image_path: String? = null,
    var width:Int? = null,
    var heigth:Int? = null,
) : Serializable
