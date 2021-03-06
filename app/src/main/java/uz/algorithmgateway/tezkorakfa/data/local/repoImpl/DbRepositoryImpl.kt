package uz.algorithmgateway.tezkorakfa.data.local.repoImpl

import uz.algorithmgateway.tezkorakfa.data.local.dao.DrawingsDao
import uz.algorithmgateway.tezkorakfa.data.local.entity.Pdf
import uz.algorithmgateway.tezkorakfa.data.local.repository.DbRepository
import uz.algorithmgateway.tezkorakfa.presenter.measurer.ui.select_type.models.Drawing
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbRepositoryImpl @Inject constructor(
    val drawingsDao: DrawingsDao,
) : DbRepository {
    override fun addDrawing(drawing: Drawing) = drawingsDao.addDrawing(drawing)
    override fun updateDrawing(drawing: Drawing) = drawingsDao.updateDrawing(drawing)
    override fun updatePdf(pdf: Pdf)  = drawingsDao.updatePdf(pdf)

    override fun getAlldrawing(): List<Drawing> = drawingsDao.getAllDrawing()
    override fun addPdf(pdf: Pdf) = drawingsDao.addPdf(pdf)

    override fun getPdf():  List<Pdf> = drawingsDao.getPdf()

    override fun delete() = drawingsDao.delete()
}