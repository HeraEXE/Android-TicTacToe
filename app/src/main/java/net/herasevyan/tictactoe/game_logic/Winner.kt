package net.herasevyan.tictactoe.game_logic

enum class Winner(val i: Int) {
    X(1), O(2), DRAW(3), NONE(0)
}