package uz.algorithmgateway.tezkorakfa.data.local.repository

import uz.algorithmgateway.tezkorakfa.measurer.ui.select_type.models.Drawing


interface DbRepository {
    fun addDrawing(drawing: Drawing)

    fun updateDrawing(drawing: Drawing)

    fun getAlldrawing(): List<Drawing>

    fun delete()
}