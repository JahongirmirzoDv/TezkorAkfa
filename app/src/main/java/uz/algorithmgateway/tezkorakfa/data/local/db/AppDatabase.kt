package uz.algorithmgateway.tezkorakfa.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.algorithmgateway.tezkorakfa.data.local.dao.DrawingsDao
import uz.algorithmgateway.tezkorakfa.data.local.entity.Pdf
import uz.algorithmgateway.tezkorakfa.presenter.measurer.ui.select_type.models.Drawing

@Database(entities = [Drawing::class,Pdf::class], version = 6)
abstract class AppDatabase : RoomDatabase() {
    abstract fun drawingsDao(): DrawingsDao
}