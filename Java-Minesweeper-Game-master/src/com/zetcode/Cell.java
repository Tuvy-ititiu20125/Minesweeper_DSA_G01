//package com.zetcode;
//
//import javax.swing.*;
//import java.util.Random;
//
//public class Cell extends Board {
//    private final int CELL_SIZE = 15;
//
//    private final int COVER_FOR_CELL = 10;
//    private final int MARK_FOR_CELL = 10;
//    private final int EMPTY_CELL = 0;
//    private final int MINE_CELL = 9;
//    private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
//    private final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;
//
//    private final int N_MINES = 40;
//    private final int N_ROWS = 16;
//    private final int N_COLS = 16;
//
//    private final int BOARD_WIDTH = N_COLS * CELL_SIZE + 1;
//    private final int BOARD_HEIGHT = N_ROWS * CELL_SIZE + 1;
//
//    public Cell(JLabel statusbar) {
//        super(statusbar);
//    }
//
//    private int allCells;
//
//    private void newGame() {
//
//        int cell;
//
//        var random = new Random();
//        inGame = true;
//        minesLeft = N_MINES;
//
//        allCells = N_ROWS * N_COLS;
//        field = new int[allCells];
//
//        for (int i = 0; i < allCells; i++) {
//
//            field[i] = COVER_FOR_CELL;
//        }
//
//        statusbar.setText(Integer.toString(minesLeft));
//
//        int i = 0;
//
//        while (i < N_MINES) {
//
//            int position = (int) (allCells * random.nextDouble());
//
//            if ((position < allCells)
//                    && (field[position] != COVERED_MINE_CELL)) {
//
//                int current_col = position % N_COLS;
//                field[position] = COVERED_MINE_CELL;
//                i++;
//
//                if (current_col > 0) {
//                    cell = position - 1 - N_COLS;
//                    if (cell >= 0) {
//                        if (field[cell] != COVERED_MINE_CELL) {
//                            field[cell] += 1;
//                        }
//                    }
//                    cell = position - 1;
//                    if (cell >= 0) {
//                        if (field[cell] != COVERED_MINE_CELL) {
//                            field[cell] += 1;
//                        }
//                    }
//
//                    cell = position + N_COLS - 1;
//                    if (cell < allCells) {
//                        if (field[cell] != COVERED_MINE_CELL) {
//                            field[cell] += 1;
//                        }
//                    }
//                }
//
//                cell = position - N_COLS;
//                if (cell >= 0) {
//                    if (field[cell] != COVERED_MINE_CELL) {
//                        field[cell] += 1;
//                    }
//                }
//
//                cell = position + N_COLS;
//                if (cell < allCells) {
//                    if (field[cell] != COVERED_MINE_CELL) {
//                        field[cell] += 1;
//                    }
//                }
//
//                if (current_col < (N_COLS - 1)) {
//                    cell = position - N_COLS + 1;
//                    if (cell >= 0) {
//                        if (field[cell] != COVERED_MINE_CELL) {
//                            field[cell] += 1;
//                        }
//                    }
//                    cell = position + N_COLS + 1;
//                    if (cell < allCells) {
//                        if (field[cell] != COVERED_MINE_CELL) {
//                            field[cell] += 1;
//                        }
//                    }
//                    cell = position + 1;
//                    if (cell < allCells) {
//                        if (field[cell] != COVERED_MINE_CELL) {
//                            field[cell] += 1;
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private void find_empty_cells(int j) {
//
//        int current_col = j % N_COLS;
//        int cell;
//
//        if (current_col > 0) {
//            cell = j - N_COLS - 1;
//            if (cell >= 0) {
//                if (field[cell] > MINE_CELL) {
//                    field[cell] -= COVER_FOR_CELL;
//                    if (field[cell] == EMPTY_CELL) {
//                        find_empty_cells(cell);
//                    }
//                }
//            }
//
//            cell = j - 1;
//            if (cell >= 0) {
//                if (field[cell] > MINE_CELL) {
//                    field[cell] -= COVER_FOR_CELL;
//                    if (field[cell] == EMPTY_CELL) {
//                        find_empty_cells(cell);
//                    }
//                }
//            }
//
//            cell = j + N_COLS - 1;
//            if (cell < allCells) {
//                if (field[cell] > MINE_CELL) {
//                    field[cell] -= COVER_FOR_CELL;
//                    if (field[cell] == EMPTY_CELL) {
//                        find_empty_cells(cell);
//                    }
//                }
//            }
//        }
//
//        cell = j - N_COLS;
//        if (cell >= 0) {
//            if (field[cell] > MINE_CELL) {
//                field[cell] -= COVER_FOR_CELL;
//                if (field[cell] == EMPTY_CELL) {
//                    find_empty_cells(cell);
//                }
//            }
//        }
//
//        cell = j + N_COLS;
//        if (cell < allCells) {
//            if (field[cell] > MINE_CELL) {
//                field[cell] -= COVER_FOR_CELL;
//                if (field[cell] == EMPTY_CELL) {
//                    find_empty_cells(cell);
//                }
//            }
//        }
//
//        if (current_col < (N_COLS - 1)) {
//            cell = j - N_COLS + 1;
//            if (cell >= 0) {
//                if (field[cell] > MINE_CELL) {
//                    field[cell] -= COVER_FOR_CELL;
//                    if (field[cell] == EMPTY_CELL) {
//                        find_empty_cells(cell);
//                    }
//                }
//            }
//
//            cell = j + N_COLS + 1;
//            if (cell < allCells) {
//                if (field[cell] > MINE_CELL) {
//                    field[cell] -= COVER_FOR_CELL;
//                    if (field[cell] == EMPTY_CELL) {
//                        find_empty_cells(cell);
//                    }
//                }
//            }
//
//            cell = j + 1;
//            if (cell < allCells) {
//                if (field[cell] > MINE_CELL) {
//                    field[cell] -= COVER_FOR_CELL;
//                    if (field[cell] == EMPTY_CELL) {
//                        find_empty_cells(cell);
//                    }
//                }
//            }
//        }
//
//    }
//
//
//}
