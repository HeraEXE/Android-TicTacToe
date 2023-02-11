package net.herasevyan.tictactoe.ui.game_history

import net.herasevyan.tictactoe.data.GameField

sealed class GameHistoryIntent {
    object LoadHistory : GameHistoryIntent()
    object DeleteHistory : GameHistoryIntent()
    class DeleteGameField(val gameField: GameField) : GameHistoryIntent()
}