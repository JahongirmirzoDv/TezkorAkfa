package uz.algorithmgateway.tezkorakfa.data.const

import androidx.annotation.StringDef


@StringDef(
    Value.SCALER,
    Value.SUPPLIER,
    Value.MONTAGER,
    Value.SERVICER
)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class UserRole

@StringDef(
    Value.NEW,
    Value.ACTIVE,
    Value.DONE
)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class MeasurerOrderStatus


class Value {
    companion object {

        const val TYPE = "user_role"

        //UserRole
        const val SCALER = "show_room"
        const val SUPPLIER = "supplier"
        const val MONTAGER = "montager"
        const val SERVICER = "servicer"


        //MeasurerOrderStatus
        const val DONE = "completed"
        const val ACTIVE = "on_scaler"
        const val NEW = "all"
    }
}