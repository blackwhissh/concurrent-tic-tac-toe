package com.epam.rd.autocode.concurrenttictactoe;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentPlayer implements Player {
    private final TicTacToe ticTacToe;
    private final char mark;
    private final PlayerStrategy playerStrategy;
    private boolean hasWon;
    private final Lock moveLock = new ReentrantLock();

    public ConcurrentPlayer(TicTacToe ticTacToe, char mark, PlayerStrategy playerStrategy) {
        this.ticTacToe = ticTacToe;
        this.mark = mark;
        this.playerStrategy = playerStrategy;

    }

    @Override
    public void run() {
        synchronized (ticTacToe){
            while (!hasWon) {
                Move move = playerStrategy.computeMove(mark, ticTacToe);
                try {
                    moveLock.lock();
                    ticTacToe.setMark(move.row, move.column, mark);

                } finally {
                    moveLock.unlock();
                }
                checkWin();
            }
        }

    }

    private synchronized void checkWin() {
        synchronized (ticTacToe){
            char[][] table = ticTacToe.table();

            for (int i = 0; i < 3; i++) {
                if (table[i][0] == mark && table[i][1] == mark && table[i][2] == mark) {
                    hasWon = true;
                    return;
                }
            }

            for (int j = 0; j < 3; j++) {
                if (table[0][j] == mark && table[1][j] == mark && table[2][j] == mark) {
                    hasWon = true;
                    return;
                }
            }

            if ((table[0][0] == mark && table[1][1] == mark && table[2][2] == mark) ||
                    (table[0][2] == mark && table[1][1] == mark && table[2][0] == mark)) {
                hasWon = true;
                return;
            }

            hasWon = false;
        }


    }


}
