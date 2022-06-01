package uz.algorithmgateway.tezkorakfa.presenter.measurer.ui.select_type.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Drawing(
    @PrimaryKey
    var id: String,
    var type: String? = null,
    var count: Int? = null,
    var type_type:String? = null,
    var external_or_internal: String? = null,
    var profile_type: String? = null,
    var profile_type_two: String? = null,
    var profile_texture: String? = null,
    var mirror_layer: String? = null,
    var mirror_mm: String? = null,
    var mirror_color: String? = null,
    var window_sill: String? = null,
    var sill_mm: String? = null,
    var sill_comment: String? = null,
    var handle: String? = null,
    var handle_petla: String? = null,
    var handle_texture: String? = null,
    var net: String? = null,
    var projet_image_path: String? = null,
    var width: Int? = null,
    var heigth: Int? = null,
    var comment_user: String = ""
) : Serializable
