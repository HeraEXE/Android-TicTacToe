package com.example.tictactoe

import com.example.tictactoe.Winner.NONE
import com.example.tictactoe.Winner.PLAYER1
import com.example.tictactoe.Winner.PLAYER2
import com.example.tictactoe.Winner.DRAW

class TicTacToeGame {

    var isPlayer1 = true
        private set

    var isGameOver = false
    private set

    private val gameField = arrayOf(
        arrayOf(0, 0, 0),
        arrayOf(0, 0, 0),
        arrayOf(0, 0, 0)
    )

    fun makeMove(row: Int, column: Int): Winner {
        if (gameField[row][column] != 0) return NONE
        gameField[row][column] = if (isPlayer1) 1 else 2
        isPlayer1 = !isPlayer1
        if (checkWinner()) {
            isGameOver = true
            return if (!isPlayer1) PLAYER1 else PLAYER2
        }
        for (gameRow in gameField) {
            if (gameRow.any { it == 0 }) {
                return NONE
            }
        }
        isGameOver = true
        return DRAW
    }

    fun clear() {
        isPlayer1 = true
        isGameOver = false
        for (row in gameField) {
            for (i in row.indices) {
                row[i] = 0
            }
        }
    }

    private fun checkWinner(): Boolean {
        return (gameField[0][0] != 0 && gameField[0][0] == gameField[0][1] && gameField[0][1] == gameField[0][2])
            || (gameField[1][0] != 0 && gameField[1][0] == gameField[1][1] && gameField[1][1] == gameField[1][2])
            || (gameField[2][0] != 0 && gameField[2][0] == gameField[2][1] && gameField[2][1] == gameField[2][2])
            || (gameField[0][0] != 0 && gameField[0][0] == gameField[1][0] && gameField[1][0] == gameField[2][0])
            || (gameField[0][1] != 0 && gameField[0][1] == gameField[1][1] && gameField[1][1] == gameField[2][1])
            || (gameField[0][2] != 0 && gameField[0][2] == gameField[1][2] && gameField[1][2] == gameField[2][2])
            || (gameField[0][0] != 0 && gameField[0][0] == gameField[1][1] && gameField[1][1] == gameField[2][2])
            || (gameField[2][0] != 0 && gameField[2][0] == gameField[1][1] && gameField[1][1] == gameField[0][2])
    }
}