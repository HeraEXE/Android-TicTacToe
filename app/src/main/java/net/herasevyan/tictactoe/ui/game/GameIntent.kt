package net.herasevyan.tictactoe.ui.game

import android.widget.ImageView

sealed class GameIntent {
    object Start: GameIntent()
    object PlayAgain : GameIntent()
    class MakeMove(val row: Int, val column: Int, val imageView: ImageView): GameIntent()
}