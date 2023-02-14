package net.herasevyan.tictactoe.game_logic

class CurrentGame(
    val flattenField: List<Int>,
    val winner: Winner,
    val turn: Turn
)