package uz.algorithmgateway.tezkorakfa.data.retrofit.models.accessory

data class Type(
    val id: Int,
    val raw_material: List<RawMaterial>,
    val type: String
)