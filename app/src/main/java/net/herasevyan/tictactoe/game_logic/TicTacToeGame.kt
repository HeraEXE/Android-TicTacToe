package net.herasevyan.tictactoe.game_logic

import android.os.Parcel
import android.os.Parcelable

class TicTacToeGame() : Parcelable {

    private var winner: Winner = Winner.NONE

    private var turn = Turn.O

    private val gameField = arrayOf(
        intArrayOf(0, 0, 0),
        intArrayOf(0, 0, 0),
        intArrayOf(0, 0, 0)
    )

    constructor(parcel: Parcel) : this() {
        winner = Winner.values().find { it.i == parcel.readInt() } ?: Winner.NONE
        turn = Turn.values().find { it.i == parcel.readInt() } ?: Turn.O
        for (i in gameField.indices) {
            for (j in gameField[i].indices) {
                gameField[i][j] = parcel.readInt()
            }
        }
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(winner.i)
        dest.writeInt(turn.i)
        for (i in gameField.indices) {
            for (j in gameField[i].indices) {
                dest.writeInt(gameField[i][j])
            }
        }
    }

    override fun describeContents() = 0

    fun makeMove(row: Int, column: Int): MoveResult? {
        if (gameField[row][column] != 0) {
            return null
        }
        turn = when (turn) {
            Turn.O -> Turn.X
            Turn.X -> Turn.O
        }
        gameField[row][column] = turn.i

        val hasWinner = (gameField[0][0] != 0 && gameField[0][0] == gameField[0][1] && gameField[0][1] == gameField[0][2])
                    || (gameField[1][0] != 0 && gameField[1][0] == gameField[1][1] && gameField[1][1] == gameField[1][2])
                    || (gameField[2][0] != 0 && gameField[2][0] == gameField[2][1] && gameField[2][1] == gameField[2][2])
                    || (gameField[0][0] != 0 && gameField[0][0] == gameField[1][0] && gameField[1][0] == gameField[2][0])
                    || (gameField[0][1] != 0 && gameField[0][1] == gameField[1][1] && gameField[1][1] == gameField[2][1])
                    || (gameField[0][2] != 0 && gameField[0][2] == gameField[1][2] && gameField[1][2] == gameField[2][2])
                    || (gameField[0][0] != 0 && gameField[0][0] == gameField[1][1] && gameField[1][1] == gameField[2][2])
                    || (gameField[2][0] != 0 && gameField[2][0] == gameField[1][1] && gameField[1][1] == gameField[0][2])

        if (hasWinner) {
            winner = when (turn) {
                Turn.X -> Winner.X
                Turn.O -> Winner.O
            }
            return MoveResult(winner, turn)
        }
        for (gameRow in gameField) {
            if (gameRow.any { it == 0 }) {
                winner = Winner.NONE
                return MoveResult(winner, turn)
            }
        }
        winner = Winner.DRAW
        return MoveResult(winner, turn)
    }

    fun clear() {
        for (row in gameField) {
            for (i in row.indices) {
                row[i] = 0
            }
        }
    }

    fun getCurrentGame() = CurrentGame(
        gameField.flatMap { it.asList() },
        winner,
        turn
    )

    companion object CREATOR : Parcelable.Creator<TicTacToeGame> {
        override fun createFromParcel(parcel: Parcel): TicTacToeGame {
            return TicTacToeGame(parcel)
        }

        override fun newArray(size: Int): Array<TicTacToeGame?> {
            return arrayOfNulls(size)
        }
    }
}