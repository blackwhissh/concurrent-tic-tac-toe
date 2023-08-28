package com.epam.rd.autocode.concurrenttictactoe;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentPlayer implements Player {
    private final TicTacToe ticTacToe;
    private final char mark;
    private final PlayerStrategy playerStrategy;
    private final Lock moveLock = new ReentrantLock();

    public ConcurrentPlayer(TicTacToe ticTacToe, char mark, PlayerStrategy playerStrategy) {
        this.ticTacToe = ticTacToe;
        this.mark = mark;
        this.playerStrategy = playerStrategy;

    }

    @Override
    public void run() {
        while (gameOver()) {
            if (ticTacToe.lastMark() != mark && !(ticTacToe.lastMark() == ' ' && mark == 'O')) {
                Move move = playerStrategy.computeMove(mark, ticTacToe);
                try {
                    moveLock.lock();
                    if (gameOver()) {
                        ticTacToe.setMark(move.row, move.column, mark);
                    }

                } finally {
                    moveLock.unlock();
                }
            }
        }
    }

    private boolean gameOver() {
        char[][] table = ticTacToe.table();

        return ((table[0][0] != 'X' || table[0][1] != 'X' || table[0][2] != 'X') &&
                (table[0][0] != 'O' || table[0][1] != 'O' || table[0][2] != 'O') &&
                (table[1][0] != 'X' || table[1][1] != 'X' || table[1][2] != 'X') &&
                (table[1][0] != 'O' || table[1][1] != 'O' || table[1][2] != 'O') &&
                (table[2][0] != 'X' || table[2][1] != 'X' || table[2][2] != 'X') &&
                (table[2][0] != 'O' || table[2][1] != 'O' || table[2][2] != 'O') &&
                (table[0][0] != 'X' || table[1][0] != 'X' || table[2][0] != 'X') &&
                (table[0][0] != 'O' || table[1][0] != 'O' || table[2][0] != 'O') &&
                (table[0][1] != 'X' || table[1][1] != 'X' || table[2][1] != 'X') &&
                (table[0][1] != 'O' || table[1][1] != 'O' || table[2][1] != 'O') &&
                (table[0][2] != 'X' || table[1][2] != 'X' || table[2][2] != 'X') &&
                (table[0][2] != 'O' || table[1][2] != 'O' || table[2][2] != 'O') &&
                (table[0][0] != 'X' || table[1][1] != 'X' || table[2][2] != 'X') &&
                (table[0][0] != 'O' || table[1][1] != 'O' || table[2][2] != 'O') &&
                (table[0][2] != 'X' || table[1][1] != 'X' || table[2][0] != 'X') &&
                (table[0][2] != 'O' || table[1][1] != 'O' || table[2][0] != 'O'));
    }

}


