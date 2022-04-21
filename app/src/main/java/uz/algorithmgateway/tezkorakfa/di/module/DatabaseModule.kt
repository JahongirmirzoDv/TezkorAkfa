package uz.algorithmgateway.tezkorakfa.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import uz.algorithmgateway.tezkorakfa.data.local.dao.DrawingsDao
import uz.algorithmgateway.tezkorakfa.data.local.db.AppDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideLeagueDao(appDatabase: AppDatabase): DrawingsDao = appDatabase.drawingsDao()


}