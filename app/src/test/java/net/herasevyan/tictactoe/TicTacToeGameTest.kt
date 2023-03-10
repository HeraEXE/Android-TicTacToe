package net.herasevyan.tictactoe

import net.herasevyan.tictactoe.game_logic.TicTacToeGame
import net.herasevyan.tictactoe.game_logic.Winner
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class TicTacToeGameTest {

    private lateinit var game: TicTacToeGame

    @Before
    fun setup() {
        game = TicTacToeGame()
    }

    @Test
    fun test1() {
        assertEquals(Winner.NONE, game.makeMove(0, 0))
        assertEquals(Winner.NONE, game.makeMove(1, 0))
        assertEquals(Winner.NONE, game.makeMove(0,1))
        assertEquals(Winner.NONE, game.makeMove(1, 1))
        assertEquals(Winner.X, game.makeMove(0, 2))
        game.clear()
        assertEquals(Winner.NONE, game.makeMove(0, 0))
    }

    @Test
    fun test2() {
        assertEquals(Winner.NONE, game.makeMove(0, 0))
        assertEquals(Winner.NONE, game.makeMove(1, 0))
        assertEquals(Winner.NONE, game.makeMove(0,1))
        assertEquals(Winner.NONE, game.makeMove(1, 1))
        assertEquals(Winner.NONE, game.makeMove(2, 2))
        assertEquals(Winner.O, game.makeMove(1, 2))
    }

    @Test
    fun test3() {
        assertEquals(Winner.NONE, game.makeMove(0, 0)) // 1
        assertEquals(Winner.NONE, game.makeMove(0, 1)) // 2
        assertEquals(Winner.NONE, game.makeMove(0, 2)) // 1
        assertEquals(Winner.NONE, game.makeMove(1, 1)) // 2
        assertEquals(Winner.NONE, game.makeMove(0, 1)) // 1
        assertEquals(Winner.NONE, game.makeMove(1, 0)) // 1
        assertEquals(Winner.NONE, game.makeMove(2, 2)) // 2
        assertEquals(Winner.X, game.makeMove(2, 0)) // 1
    }

    @Test
    fun test4() {
        assertEquals(Winner.NONE, game.makeMove(1, 1)) // 1
        assertEquals(Winner.NONE, game.makeMove(2, 0)) // 2
        assertEquals(Winner.NONE, game.makeMove(1, 2)) // 1
        assertEquals(Winner.NONE, game.makeMove(1, 0)) // 2
        assertEquals(Winner.NONE, game.makeMove(0, 0)) // 1
        assertEquals(Winner.NONE, game.makeMove(2, 2)) // 2
        assertEquals(Winner.NONE, game.makeMove(0, 2)) // 1
        assertEquals(Winner.NONE, game.makeMove(0, 1)) // 2
        assertEquals(Winner.DRAW, game.makeMove(2, 1)) // 1
        assertTrue(game.winner != Winner.NONE)
        assertEquals(false, game.isXTurn)
        game.clear()
        assertTrue(game.winner != Winner.NONE)
        assertEquals(true, game.isXTurn)
    }
}