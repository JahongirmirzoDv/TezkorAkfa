package uz.algorithmgateway.tezkorakfa.data.retrofit.models.accessory

data class Type(
    val accessory_type: String,
    val id: Int,
    val name: String,
    val raw_material: List<RawMaterial>
)