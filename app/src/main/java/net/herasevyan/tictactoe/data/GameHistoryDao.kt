package net.herasevyan.tictactoe.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameHistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGameField(gameField: GameField)

    @Query("SELECT * FROM game_field_table ORDER BY id DESC")
    fun loadHistory(): Flow<List<GameField>>
}