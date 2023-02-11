package net.herasevyan.tictactoe.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "game_field_table")
class GameField(
    val field: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameField

        if (field != other.field) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = field.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}