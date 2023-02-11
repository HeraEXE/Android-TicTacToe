package net.herasevyan.tictactoe.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_field_table")
class GameField(
    val field: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)