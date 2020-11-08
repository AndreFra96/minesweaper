package minesweeper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Playground {

    private final int height;
    private final int width;
    private final Cell[][] grid;

    public Playground(int height, int width, int bees) {
        if (bees <= 0 || height <= 0 || width <= 0)
            throw new IllegalStateException("Sono ammessi solo valori > 0");
        this.height = height;
        this.width = width;
        this.grid = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.grid[i][j] = Cell.emptyCell(i, j);
            }
        }

        do {
            Random randomizer = new Random();
            int row = randomizer.nextInt(height);
            int col = randomizer.nextInt(width);
            if (!(this.grid[row][col].isBee())) {
                this.grid[row][col] = Cell.beeCell(row, col);
                bees--;
                numberNextSpots(this.grid[row][col]);
            }
        } while (bees > 0);
    }

    private boolean isIncluded(int row, int col) {
        if (row >= 0 && row < this.height && col >= 0 && col < this.width)
            return true;
        return false;
    }

    /**
     * effettua il click su una cella del playground
     * 
     * @param row riga della cella
     * @param col colonna della cella
     * @return true se la partita può continuare, false altrimenti
     */
    public boolean click(int row, int col) {
        if (row < 0 || col < 0 || row >= height || col >= width) {
            return true;
        } else if (this.grid[row][col].isBee()) {
            this.grid[row][col].reveal();
            return false;
        } else if (this.grid[row][col].isNumber()) {
            this.grid[row][col].reveal();
        } else if (this.grid[row][col].isEmpty()) {
            revealEmpty(this.grid[row][col]);
        }
        return true;
    }

    /**
     * Rivela tutti gli spazi vicini ad uno spazio selezionato
     */
    public void revealEmpty(Cell input) {
        if (input.isEmpty()) {
            input.reveal();
            Iterator<Cell> iterator = nextSpots(input).iterator();
            while (iterator.hasNext()) {
                Cell next = iterator.next();
                if (next.isEmpty() && !(next.revealed())) {
                    revealEmpty(this.grid[next.getRow()][next.getCol()]);
                }
                this.grid[next.getRow()][next.getCol()].reveal();
            }
        }
    }

    public void revealAll() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!(this.grid[i][j].revealed()))
                    this.grid[i][j].reveal();
            }
        }
    }

    /**
     * Inserisce il flag sulla casella indicata
     * 
     * @param row
     * @param col
     * @return true se tutte le api sono state flaggate, false altrimenti
     */
    public boolean flag(int row, int col) {
        if (row < 0 || col < 0 || row >= height || col >= height) {
            return false;
        }
        this.grid[row][col].flag();
        if (allBeesFound())
            return true;
        return false;
    }

    private boolean allBeesFound() {
        boolean allFound = true;
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {

                if (!(this.grid[i][j].isBee()) && this.grid[i][j].isFlagged() && !(this.grid[i][j].revealed())) {
                    allFound = false;
                } else if (this.grid[i][j].isBee() && !(this.grid[i][j].isFlagged())) {
                    allFound = false;
                }

            }
        }
        return allFound;
    }

    public void unflag(int row, int col) {
        if (row < 0 || col < 0 || row >= height || col >= height) return;
        this.grid[row][col].unflag();
    }

    public ArrayList<Cell> nextSpots(Cell input) {
        ArrayList<Cell> spots = new ArrayList<Cell>();
        // movimento riga e movimento colonna (coppie da 2)
        int[] changes = { -1, -1, -1, 0, -1, 1, 0, 1, 1, 1, 1, 0, 1, -1, 0, -1 };
        for (int i = 0; i < changes.length - 1; i = i + 2) {
            int rowIncr = changes[i];
            int colIncr = changes[i + 1];
            if (isIncluded(input.getRow() + rowIncr, input.getCol() + colIncr)) {
                Cell actual = this.grid[input.getRow() + rowIncr][input.getCol() + colIncr];
                spots.add(actual);
            }
        }
        return spots;
    }

    public void numberNextSpots(Cell input) {
        // movimento riga e movimento colonna (coppie da 2)
        int[] changes = { -1, -1, -1, 0, -1, 1, 0, 1, 1, 1, 1, 0, 1, -1, 0, -1 };
        for (int i = 0; i < changes.length - 1; i = i + 2) {
            int rowIncr = changes[i];
            int colIncr = changes[i + 1];
            if (isIncluded(input.getRow() + rowIncr, input.getCol() + colIncr)) {
                Cell actual = this.grid[input.getRow() + rowIncr][input.getCol() + colIncr];
                if (!(actual.isBee())) {
                    if (actual.isNumber()) {
                        this.grid[input.getRow() + rowIncr][input.getCol() + colIncr] = Cell
                                .numberCell(actual.number() + 1, actual.getRow(), actual.getCol());
                    } else {
                        this.grid[input.getRow() + rowIncr][input.getCol() + colIncr] = Cell.numberCell(1,
                                actual.getRow(), actual.getCol());
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder();
        int rowCount = -1;
        int colCount = 0;

        for (int i = -1; i < height + 1; i++) {
            for (int j = -1; j < width + 1; j++) {
                if (i == -1 || j == -1) {
                    if (i == -1) {
                        if (rowCount == -1 || rowCount == width) {
                            stringbuilder.append("#  ");
                        } else {
                            stringbuilder.append(rowCount + " ");
                            if (rowCount < 10)
                                stringbuilder.append((" "));
                        }
                        rowCount++;

                    } else {
                        if (colCount == height) {
                            stringbuilder.append("#  ");
                        } else {
                            stringbuilder.append(colCount + " ");
                            if (colCount < 10)
                                stringbuilder.append((" "));
                        }
                        colCount++;
                    }

                } else if (j == width || i == height) {
                    stringbuilder.append("#  ");
                } else {
                    stringbuilder.append(grid[i][j].toString());
                }
            }
            stringbuilder.append("\n");
        }
        return stringbuilder.toString();
    }
}
