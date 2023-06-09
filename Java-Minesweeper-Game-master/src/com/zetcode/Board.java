package com.zetcode;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;
import javax.swing.*;

public class Board extends JPanel {

    private final int NUM_IMAGES = 13;
    private final int CELL_SIZE = 15;

    private final int COVER_FOR_CELL = 10;
    private final int MARK_FOR_CELL = 10;
    private final int EMPTY_CELL = 0;
    private final int MINE_CELL = 9;
    private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
    private final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;

    private final int DRAW_MINE = 9;
    private final int DRAW_COVER = 10;
    private final int DRAW_MARK = 11;
    private final int DRAW_WRONG_MARK = 12;

    private final int N_MINES = 40;
    private final int N_ROWS = 16;
    private final int N_COLS = 16;

    private final int BOARD_WIDTH = N_COLS * CELL_SIZE + 1;
    private final int BOARD_HEIGHT = N_ROWS * CELL_SIZE + 1;

    private int[] field;
    private boolean inGame;
    private int minesLeft;
    private Image[] img;

    private int allCells;
    private final JLabel statusbar;

    private Stack<int[]> gameStateStack = new Stack<>();

    public Board(JLabel statusbar) {

        this.statusbar = statusbar;
        initBoard();
    }

    private void initBoard() {

        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

        // Array to store images
        img = new Image[NUM_IMAGES];

        for (int i = 0; i < NUM_IMAGES; i++) {

            var path = "src/resources/" + i + ".png";

            // Use ImageIcon to load the image file specified by the path variable
            img[i] = (new ImageIcon(path)).getImage();
        }

        addMouseListener(new MinesAdapter());
        newGame();
    }

    private void newGame() {

        int cell;

        var random = new Random(); // generate random numbers
        inGame = true; // indicate game is in progress
        minesLeft = N_MINES; // total mines in game

        allCells = N_ROWS * N_COLS; // total number of cells
        field = new int[allCells]; // present state of a cell

        for (int i = 0; i < allCells; i++) {

            field[i] = COVER_FOR_CELL;
        }

        statusbar.setText(Integer.toString(minesLeft)); // show number of mines left

        int i = 0;

        // use while to place randomly mines
        while (i < N_MINES) {

            int position = (int) (allCells * random.nextDouble());

            // check if position is in range of field array
            // and that cell is not a mine
            if ((position < allCells)
                    && (field[position] != COVERED_MINE_CELL)) {

                int current_col = position % N_COLS; // determine neighboring cells
                field[position] = COVERED_MINE_CELL; // set as a mine
                i++;

                // check the current position is not in the first column
                if (current_col > 0) {

                    // check diagonally above to the left
                    cell = position - 1 - N_COLS;
                    if (cell >= 0) {
                        // if not a mine -> increase by 1
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }

                    // check diagonally below to the left
                    cell = position - 1;
                    if (cell >= 0) {
                        // if not a mine -> increase by 1
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }

                    // to the left of the current position
                    cell = position + N_COLS - 1;
                    if (cell < allCells) {
                        // if not a mine -> increase by 1
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                }

                cell = position - N_COLS;
                if (cell >= 0) {
                    // if not a mine -> increase by 1
                    if (field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                cell = position + N_COLS;
                if (cell < allCells) {
                    // if not a mine -> increase by 1
                    if (field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                // check current position is not the last column of game board
                if (current_col < (N_COLS - 1)) {
                    // above
                    cell = position - N_COLS + 1;
                    if (cell >= 0) {
                        // if not a mine -> increase by 1
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }

                    // below
                    cell = position + N_COLS + 1;
                    if (cell < allCells) {
                        // if not a mine -> increase by 1
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }

                    // to the right
                    cell = position + 1;
                    if (cell < allCells) {
                        // if not a mine -> increase by 1
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                }
            }
        }
    }

    private void find_empty_cells(int j) {

        int current_col = j % N_COLS;
        int cell;

        // check if current cell is in the leftmost column
        if (current_col > 0) {
            cell = j - N_COLS - 1; // upper left cell
            if (cell >= 0) {
                // upper left cell is a mine or not
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j - 1; // index of cell to the left of current cell
            // check if outside the leftmost of game board
            if (cell >= 0) {
                // check is a mine or not
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + N_COLS - 1; // lower left of current cell
            // check if lower left is outside bottom edge
            if (cell < allCells) {
                // check mine or not
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }
        }

        cell = j - N_COLS; // cell above the current cell
        // check if cell above is outside top edge
        if (cell >= 0) {
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
        }

        cell = j + N_COLS; // cell below the current cell
        // check if cell below is outside bottom edge
        if (cell < allCells) {
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
        }

        // check if current cell is not in the rightmost column of game board
        if (current_col < (N_COLS - 1)) {
            cell = j - N_COLS + 1; // diagonally up and to the right of current cell
            if (cell >= 0) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + N_COLS + 1; // diagonally down and to the right of current cell
            if (cell < allCells) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + 1; // the right cell of current cell
            if (cell < allCells) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }
        }

    }

    @Override
    public void paintComponent(Graphics g) {

        int uncover = 0;

        for (int i = 0; i < N_ROWS; i++) {

            for (int j = 0; j < N_COLS; j++) {

                int cell = field[(i * N_COLS) + j];

                // checks if the game is still in progress and if cell contains a mine
                if (inGame && cell == MINE_CELL) {

                    inGame = false; // game is over
                }

                // if game is over
                if (!inGame) {

                    // checks cell is mine
                    if (cell == COVERED_MINE_CELL) {
                        cell = DRAW_MINE; // draw mine picture
                    } else if (cell == MARKED_MINE_CELL) { // a marked mine
                        cell = DRAW_MARK; // draw marked mine picture
                    } else if (cell > COVERED_MINE_CELL) { // check if the cell contains a wrongly marked cell
                        cell = DRAW_WRONG_MARK; // draw wrong mark picture
                    } else if (cell > MINE_CELL) { // not a mine
                        cell = DRAW_COVER; // draw cover image
                    }

                } else { // if game is not over

                    if (cell > COVERED_MINE_CELL) { // cell contains a marked cell
                        cell = DRAW_MARK; // draw marked cell image
                    } else if (cell > MINE_CELL) { // cell is covered
                        cell = DRAW_COVER; // draw cover image
                        uncover++;
                    }
                }

                g.drawImage(img[cell], (j * CELL_SIZE),
                        (i * CELL_SIZE), this);
            }
        }

        // check win or not
        if (uncover == 0 && inGame) {
            // if win set game is over & print "game won"
            inGame = false;
            statusbar.setText("Game won");

        } else if (!inGame) {
            // print "game lost"
            statusbar.setText("Game lost");
        }
    }

    private class MinesAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();

            // calculates the column and row of the corresponding cell
            int cCol = x / CELL_SIZE;
            int cRow = y / CELL_SIZE;

            boolean doRepaint = false;

            // game í not in progress
            if (!inGame) {
                newGame();
                repaint();
            }

            // check that the mouse click coordinates are within the bounds of the board
            if ((x < N_COLS * CELL_SIZE) && (y < N_ROWS * CELL_SIZE)) {
                // checks whether the left or right mouse button was clicked
                if (e.getButton() == MouseEvent.BUTTON3) {
                    // right mouse button was clicked and not a mine
                    if (field[(cRow * N_COLS) + cCol] > MINE_CELL) {

                        doRepaint = true;

                        if (field[(cRow * N_COLS) + cCol] <= COVERED_MINE_CELL) {

                            if (minesLeft > 0) {
                                field[(cRow * N_COLS) + cCol] += MARK_FOR_CELL;
                                minesLeft--;
                                String msg = Integer.toString(minesLeft);
                                statusbar.setText(msg);
                            } else {
                                statusbar.setText("No marks left");
                            }

                        } else {

                            field[(cRow * N_COLS) + cCol] -= MARK_FOR_CELL;
                            minesLeft++;
                            String msg = Integer.toString(minesLeft);
                            statusbar.setText(msg);
                        }
                    }

                } else { // the left mouse button was clicked

                    if (field[(cRow * N_COLS) + cCol] > COVERED_MINE_CELL) {

                        return; // do nothing
                    }

                    // checks whether the clicked location is not a mine cell and is not already
                    // marked as a mine
                    if ((field[(cRow * N_COLS) + cCol] > MINE_CELL)
                            && (field[(cRow * N_COLS) + cCol] < MARKED_MINE_CELL)) {

                        field[(cRow * N_COLS) + cCol] -= COVER_FOR_CELL; // uncovers the cell
                        doRepaint = true;

                        // checks whether the uncovered cell is a mine cell
                        if (field[(cRow * N_COLS) + cCol] == MINE_CELL) {
                            inGame = false;
                        }

                        // uncovered cell is not a mine cell -> checks whether it is an empty cell
                        if (field[(cRow * N_COLS) + cCol] == EMPTY_CELL) {
                            find_empty_cells((cRow * N_COLS) + cCol); // uncover all adjacent empty cells
                        }
                    }
                }

                if (doRepaint) {
                    repaint();
                }
            }

            // save current game state before making a move
            int[] gameState = Arrays.copyOf(field, field.length); /* copy of the current game state and */
            gameStateStack.push(gameState); /* store it in an array called gameState */

            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK), "undo");
            getActionMap().put("undo", new AbstractAction() {
                @Override
                // actionPerformed method of the AbstractAction
                public void actionPerformed(ActionEvent e) {
                    if (!gameStateStack.isEmpty()) {
                        // undo last move and restore previous game state
                        field = gameStateStack.pop();
                        inGame = true;
                        repaint();
                    }
                }
            });

        }
    }

}
