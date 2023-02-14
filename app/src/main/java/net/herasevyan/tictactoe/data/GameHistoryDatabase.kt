package net.herasevyan.tictactoe.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [GameField::class], version = 1)
@TypeConverters(Converters::class)
abstract class GameHistoryDatabase : RoomDatabase() {

    abstract fun gameHistoryDao(): GameHistoryDao
}