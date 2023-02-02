package net.herasevyan.tictactoe

import android.os.Parcel
import android.os.Parcelable
import net.herasevyan.tictactoe.Winner.NONE
import net.herasevyan.tictactoe.Winner.PLAYER1
import net.herasevyan.tictactoe.Winner.PLAYER2
import net.herasevyan.tictactoe.Winner.DRAW

class TicTacToeGame() : Parcelable {

    var isPlayer1 = true
        private set

    var winner: Winner = NONE
        private set

    val flattenGameField get() = gameField.flatten()

    private val gameField = arrayOf(
        arrayOf(0, 0, 0),
        arrayOf(0, 0, 0),
        arrayOf(0, 0, 0)
    )

    constructor(parcel: Parcel) : this() {
        isPlayer1 = parcel.readInt() == 0
        winner = Winner.values()[parcel.readInt()]
        for (i in gameField.indices) {
            for (j in gameField[i].indices) {
                gameField[i][j] = parcel.readInt()
            }
        }
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(if (isPlayer1) 0 else 1)
        dest.writeInt(winner.ordinal)
        for (i in gameField.indices) {
            for (j in gameField[i].indices) {
                dest.writeInt(gameField[i][j])
            }
        }
    }

    override fun describeContents() = 0

    fun makeMove(row: Int, column: Int): Winner {
        if (gameField[row][column] != 0) {
            winner = NONE
            return winner
        }
        gameField[row][column] = if (isPlayer1) 1 else 2
        isPlayer1 = !isPlayer1

        val hasWinner = (gameField[0][0] != 0 && gameField[0][0] == gameField[0][1] && gameField[0][1] == gameField[0][2])
                    || (gameField[1][0] != 0 && gameField[1][0] == gameField[1][1] && gameField[1][1] == gameField[1][2])
                    || (gameField[2][0] != 0 && gameField[2][0] == gameField[2][1] && gameField[2][1] == gameField[2][2])
                    || (gameField[0][0] != 0 && gameField[0][0] == gameField[1][0] && gameField[1][0] == gameField[2][0])
                    || (gameField[0][1] != 0 && gameField[0][1] == gameField[1][1] && gameField[1][1] == gameField[2][1])
                    || (gameField[0][2] != 0 && gameField[0][2] == gameField[1][2] && gameField[1][2] == gameField[2][2])
                    || (gameField[0][0] != 0 && gameField[0][0] == gameField[1][1] && gameField[1][1] == gameField[2][2])
                    || (gameField[2][0] != 0 && gameField[2][0] == gameField[1][1] && gameField[1][1] == gameField[0][2])

        if (hasWinner) {
            winner = if (!isPlayer1) PLAYER1 else PLAYER2
            return winner
        }
        for (gameRow in gameField) {
            if (gameRow.any { it == 0 }) {
                winner = NONE
                return winner
            }
        }
        winner = DRAW
        return winner
    }

    fun clear() {
        isPlayer1 = true
        for (row in gameField) {
            for (i in row.indices) {
                row[i] = 0
            }
        }
    }

    companion object CREATOR : Parcelable.Creator<TicTacToeGame> {
        override fun createFromParcel(parcel: Parcel): TicTacToeGame {
            return TicTacToeGame(parcel)
        }

        override fun newArray(size: Int): Array<TicTacToeGame?> {
            return arrayOfNulls(size)
        }
    }
}