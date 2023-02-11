package net.herasevyan.tictactoe.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GameHistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGameField(gameField: GameField)

    @Delete
    suspend fun deleteGameField(gameField: GameField)

    @Query("SELECT * FROM game_field_table ORDER BY id DESC")
    fun loadHistory(): Flow<List<GameField>>

    @Query("DELETE FROM game_field_table")
    suspend fun deleteHistory()
}