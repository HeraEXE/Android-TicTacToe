package net.herasevyan.tictactoe.ui.game

import android.widget.ImageView

sealed class GameState {
    object Inactive : GameState()
    object ClearField : GameState()
    class Restore(val winner: Winner, val flattenGameField: List<Int>) : GameState()
    class UpdateMove(val imageView: ImageView, val winner: Winner, val isPrevPlayer1: Boolean?) : GameState()
}