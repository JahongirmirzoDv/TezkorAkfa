package uz.algorithmgateway.tezkorakfa.data.local.dao

import androidx.room.*
import uz.algorithmgateway.tezkorakfa.data.local.entity.Pdf
import uz.algorithmgateway.tezkorakfa.measurer.ui.select_type.models.Drawing

@Dao
interface DrawingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDrawing(drawing: Drawing)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPdf(pdf: Pdf)

    @Update
    fun updateDrawing(drawing: Drawing)

    @Update
    fun updatePdf(pdf: Pdf)

    @Query("select * from drawing")
    fun getAllDrawing(): List<Drawing>

    @Query("select * from pdf")
    fun getPdf(): List<Pdf>

    @Query("delete from drawing")
    fun delete()
}