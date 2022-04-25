package uz.algorithmgateway.tezkorakfa.data.models.profile

data class Type(
    val id: Int,
    val name: String,
    val raw_material: List<RawMaterial>
)