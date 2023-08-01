package com.devexperto.testingexpert.domain

import org.junit.Assert.*
import org.junit.Test

class TicTacToeTest {
    @Test
    fun `Board empty at beginning of the game`() {
        val ticTacToe = TicTacToe()

        assertTrue(ticTacToe.board.flatten().all { it == Empty })
    }

    @Test
    fun `First player is X`() {
        val ticTacToe = TicTacToe()

        assertEquals(X, ticTacToe.currentPlayer)
    }

    @Test
    fun `First move is done by X`() {
        val ticTacToe = TicTacToe().move(0,0)

        assertEquals(X, ticTacToe.board[0][0])
    }

    @Test
    fun `Second move is done by O`() {
        val ticTacToe = TicTacToe()
            .move(0,0)
            .move(1, 1)

        assertEquals(O, ticTacToe.board[1][1])
    }

    @Test
    fun `Occupied cell cannot be played`() {
        val ticTacToe = TicTacToe()
            .move(0,0)
            .move(0, 0)

        assertEquals(X, ticTacToe.board[0][0])
    }

    @Test
    fun `The game ends when all cells in a row are taken by a player`() {
        val ticTacToe = TicTacToe()
            .move(0,0)
            .move(1,0)
            .move(0, 1)
            .move(1, 1)
            .move(0, 2)

        assertEquals(X, ticTacToe.findWinner())
    }

    @Test
    fun `The game ends when all cells in a column are taken by a player`() {
        val ticTacToe = TicTacToe()
            .move(0,0)
            .move(1,1)
            .move(1, 0)
            .move(1, 2)
            .move(2, 0)

        assertEquals(X, ticTacToe.findWinner())
    }

    @Test
    fun `The game ends when all cells in a diagonal are taken by a player`() {
        val ticTacToe = TicTacToe()
            .move(0,0)
            .move(2,1)
            .move(1, 1)
            .move(1, 2)
            .move(2, 2)

        assertEquals(X, ticTacToe.findWinner())
    }

    @Test
    fun `The game ends when all cells are taken and there is no winner`() {
        val ticTacToe = TicTacToe()
            .move(0,0) // X
            .move(0,2) // O
            .move(0, 1) // X
            .move(1, 0) // O
            .move(1, 2) // X
            .move(1, 1) // O
            .move(2, 2) // X
            .move(2, 1) // O
            .move(2, 0) // X

        assertEquals(Draw, ticTacToe.findWinner())
    }

    @Test
    fun `Return null if asking for a winner when the game is not finished`() {
        val ticTacToe = TicTacToe()
            .move(0,0)
            .move(2,1)

        assertNull(ticTacToe.findWinner())
    }

    @Test
    fun `The game ends if O wins`() {
        val ticTacToe = TicTacToe()
            .move(1,0)
            .move(0,0)
            .move(1, 1)
            .move(0, 2)
            .move(2, 1)
            .move(0, 1)

        assertEquals(O, ticTacToe.findWinner())
    }
}