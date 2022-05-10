package uz.algorithmgateway.tezkorakfa.measurer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.algorithmgateway.tezkorakfa.data.local.entity.Pdf
import uz.algorithmgateway.tezkorakfa.data.local.repoImpl.DbRepositoryImpl
import uz.algorithmgateway.tezkorakfa.measurer.ui.select_type.models.Drawing
import javax.inject.Inject

class DbViewmodel @Inject constructor(
    val db: DbRepositoryImpl,
) : ViewModel() {
    fun addDrawing(drawing: Drawing) {
        viewModelScope.launch {
            db.drawingsDao.addDrawing(drawing)
        }
    }

    fun addPdf(pdf: Pdf) {
        viewModelScope.launch {
            db.drawingsDao.addPdf(pdf)
        }
    }

    fun updateDrawing(drawing: Drawing) {
        viewModelScope.launch {
            db.drawingsDao.updateDrawing(drawing)
        }
    }

    fun updatePdf(pdf: Pdf) {
        viewModelScope.launch {
            db.drawingsDao.updatePdf(pdf)
        }
    }

    fun getAllDrawing(): List<Drawing> {
        var list = listOf<Drawing>()
        viewModelScope.launch {
            list = db.drawingsDao.getAllDrawing()
        }
        return list
    }

    fun getPdf(): List<Pdf> {
        var list:  List<Pdf> = emptyList()
        viewModelScope.launch {
            list = db.drawingsDao.getPdf()
        }
        return list
    }

    fun delete() {
        viewModelScope.launch {
            db.drawingsDao.delete()
        }
    }
}