package uz.algorithmgateway.tezkorakfa.data.local.dao

import androidx.room.*
import uz.algorithmgateway.tezkorakfa.measurer.ui.select_type.models.Drawing

@Dao
interface DrawingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDrawing(drawing: Drawing)

    @Update
    fun updateDrawing(drawing: Drawing)

    @Query("select * from drawing")
    fun getAllDrawing(): List<Drawing>
}