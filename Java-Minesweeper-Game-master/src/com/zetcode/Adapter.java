//package com.zetcode;
//
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//
//public class Adapter implements MouseListener {
//    private class MinesAdapter extends MouseAdapter {
//
//        @Override
//        public void mousePressed(MouseEvent e) {
//
//            int x = e.getX();
//            int y = e.getY();
//
//            int cCol = x / CELL_SIZE;
//            int cRow = y / CELL_SIZE;
//
//            boolean doRepaint = false;
//
//            if (!inGame) {
//
//                newGame();
//                repaint();
//            }
//
//            if ((x < N_COLS * CELL_SIZE) && (y < N_ROWS * CELL_SIZE)) {
//
//                if (e.getButton() == MouseEvent.BUTTON3) {
//
//                    if (field[(cRow * N_COLS) + cCol] > MINE_CELL) {
//
//                        doRepaint = true;
//
//                        if (field[(cRow * N_COLS) + cCol] <= COVERED_MINE_CELL) {
//
//                            if (minesLeft > 0) {
//                                field[(cRow * N_COLS) + cCol] += MARK_FOR_CELL;
//                                minesLeft--;
//                                String msg = Integer.toString(minesLeft);
//                                statusbar.setText(msg);
//                            } else {
//                                statusbar.setText("No marks left");
//                            }
//                        } else {
//
//                            field[(cRow * N_COLS) + cCol] -= MARK_FOR_CELL;
//                            minesLeft++;
//                            String msg = Integer.toString(minesLeft);
//                            statusbar.setText(msg);
//                        }
//                    }
//
//                } else {
//
//                    if (field[(cRow * N_COLS) + cCol] > COVERED_MINE_CELL) {
//
//                        return;
//                    }
//
//                    if ((field[(cRow * N_COLS) + cCol] > MINE_CELL)
//                            && (field[(cRow * N_COLS) + cCol] < MARKED_MINE_CELL)) {
//
//                        field[(cRow * N_COLS) + cCol] -= COVER_FOR_CELL;
//                        doRepaint = true;
//
//                        if (field[(cRow * N_COLS) + cCol] == MINE_CELL) {
//                            inGame = false;
//                        }
//
//                        if (field[(cRow * N_COLS) + cCol] == EMPTY_CELL) {
//                            find_empty_cells((cRow * N_COLS) + cCol);
//                        }
//                    }
//                }
//
//                if (doRepaint) {
//                    repaint();
//                }
//            }
//        }
//    }
//}
