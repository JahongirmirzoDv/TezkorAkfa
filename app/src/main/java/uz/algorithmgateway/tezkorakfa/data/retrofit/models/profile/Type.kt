package uz.algorithmgateway.tezkorakfa.data.retrofit.models.profile

data class Type(
    val id: Int,
    val name: String,
    val raw_material: List<RawMaterial>
)