package net.herasevyan.tictactoe.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.herasevyan.tictactoe.data.GameHistoryDao
import net.herasevyan.tictactoe.data.GameHistoryDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GameHistoryDatabaseModule {

    @Provides
    @Singleton
    fun provideGameHistoryDao(@ApplicationContext context: Context): GameHistoryDao {
        val db = Room.databaseBuilder(
            context,
            GameHistoryDatabase::class.java,
            "game-history"
        ).build()
        return db.gameHistoryDao()
    }
}