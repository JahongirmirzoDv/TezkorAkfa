package uz.algorithmgateway.tezkorakfa.data.local.repository

import uz.algorithmgateway.tezkorakfa.data.local.entity.Pdf
import uz.algorithmgateway.tezkorakfa.presenter.measurer.ui.select_type.models.Drawing


interface DbRepository {
    fun addDrawing(drawing: Drawing)

    fun updateDrawing(drawing: Drawing)
    fun updatePdf(pdf: Pdf)

    fun getAlldrawing(): List<Drawing>

    fun addPdf(pdf: Pdf)

    fun getPdf(): List<Pdf>

    fun delete()
}