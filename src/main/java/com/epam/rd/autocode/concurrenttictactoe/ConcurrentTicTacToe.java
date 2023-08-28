package com.epam.rd.autocode.concurrenttictactoe;

public class ConcurrentTicTacToe implements TicTacToe {
    private final char[][] gameTable;
    private char lastMark;

    public ConcurrentTicTacToe() {
        gameTable = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameTable[i][j] = ' ';
            }
        }
        lastMark = ' ';
    }

    @Override
    public void setMark(int x, int y, char mark) {
        if (gameTable[x][y] == ' ') {
            gameTable[x][y] = mark;
            lastMark = mark;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public char[][] table() {
        char[][] copy = new char[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(gameTable[i], 0, copy[i], 0, 3);
        }
        return copy;
    }

    @Override
    public char lastMark() {
        return lastMark;
    }


}

