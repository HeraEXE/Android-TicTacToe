package net.herasevyan.tictactoe.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GameField::class], version = 1)
abstract class GameHistoryDatabase : RoomDatabase() {

    abstract fun gameHistoryDao(): GameHistoryDao
}