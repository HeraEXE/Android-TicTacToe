package net.herasevyan.tictactoe.ui.game

import android.widget.ImageView
import net.herasevyan.tictactoe.game_logic.CurrentGame
import net.herasevyan.tictactoe.game_logic.MoveResult
import net.herasevyan.tictactoe.game_logic.Winner

sealed class GameState {
    object Inactive : GameState()
    object ClearField : GameState()
    class Restore(val currentGame: CurrentGame) : GameState()
    class UpdateMove(val imageView: ImageView, val moveResult: MoveResult) : GameState()
}