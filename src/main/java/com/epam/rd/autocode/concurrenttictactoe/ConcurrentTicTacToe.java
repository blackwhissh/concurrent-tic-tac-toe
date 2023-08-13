package com.epam.rd.autocode.concurrenttictactoe;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentTicTacToe implements TicTacToe {
    private final char[][] gameTable;
    private char lastMark;
    private final Lock lock = new ReentrantLock();

    public ConcurrentTicTacToe() {
        gameTable = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameTable[i][j] = ' ';
            }
        }
        lastMark = 'O'; // 'O' plays first
    }

    @Override
    public synchronized void setMark(int x, int y, char mark) {
        lock.lock();
        try {
            if (gameTable[x][y] == ' ') {
                gameTable[x][y] = mark;
                lastMark = mark;
            }else {
                throw new IllegalArgumentException();
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public char[][] table() {
        lock.lock();
        try {
            char[][] copy = new char[3][3];
            for (int i = 0; i < 3; i++) {
                System.arraycopy(gameTable[i], 0, copy[i], 0, 3);
            }
            return copy;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public char lastMark() {
        lock.lock();
        try {
            return lastMark;
        } finally {
            lock.unlock();
        }
    }


}
