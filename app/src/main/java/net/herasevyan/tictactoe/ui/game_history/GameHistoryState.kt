package net.herasevyan.tictactoe.ui.game_history

import net.herasevyan.tictactoe.data.GameField

sealed class GameHistoryState {
    object Inactive : GameHistoryState()
    class LoadHistory(val history: List<GameField>) : GameHistoryState()
}