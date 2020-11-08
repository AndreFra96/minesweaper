package minesweeper;
import java.util.Objects;

public class Cell {
    private boolean revealed = false;
    private final boolean isbee;
    private final boolean isnumber;
    private final boolean isempty;
    private final int row;
    private final int col;
    private int number;
    private boolean flagged = false;

    public Cell(char type, int row, int col) {
        this.row = Objects.requireNonNull(row);
        this.col = Objects.requireNonNull(col);

        switch (type) {
            case 'N':
                this.isnumber = true;
                this.isbee = false;
                this.isempty = false;
                this.number = 1;
                break;
            case 'B':
                this.isnumber = false;
                this.isbee = true;
                this.isempty = false;
                break;
            case 'E':
                this.isnumber = false;
                this.isbee = false;
                this.isempty = true;
                break;
            default:
                throw new IllegalArgumentException("Tipo di cella non valido");
        }
    }

    public Cell(int row, int col, int number) {
        this.isnumber = true;
        this.isbee = false;
        this.isempty = false;
        this.number = Objects.requireNonNull(number);
        this.row = Objects.requireNonNull(row);
        this.col = Objects.requireNonNull(col);
    }
    
    public static Cell numberCell(int number, int row, int col) {
        return new Cell(Objects.requireNonNull(row), Objects.requireNonNull(col), Objects.requireNonNull(number));
    }

    public static Cell emptyCell(int row, int col) {
        return new Cell('E', Objects.requireNonNull(row), Objects.requireNonNull(col));
    }

    public static Cell beeCell(int row, int col) {
        return new Cell('B', Objects.requireNonNull(row), Objects.requireNonNull(col));
    }

    

    public boolean isBee() {
        if (isbee)
            return true;
        return false;
    }

    public boolean isNumber() {
        if (isnumber)
            return true;
        return false;
    }

    public boolean isEmpty() {
        if (isempty)
            return true;
        return false;
    }

    public int number() {
        if (!isnumber)
            throw new IllegalStateException("La cella non è di tipo number");
        else
            return this.number;
    }

    public boolean revealed() {
        if (revealed)
            return true;
        return false;
    }

    public void reveal() {
        this.revealed = true;
    }

    public int[] coord() {
        int[] coord = { this.getRow(), this.getCol() };
        return coord;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public void flag(){
        this.flagged = true;
    }

    public void unflag(){
        this.flagged = false;
    }

    public boolean isFlagged(){
        return this.flagged;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cell))
            return false;
        Cell mcolObj = (Cell) obj;
        if (mcolObj.getRow() == this.getRow() && mcolObj.getCol() == mcolObj.getCol() && this.isBee() == mcolObj.isBee()
                && this.isNumber() == mcolObj.isNumber() && this.isEmpty() == mcolObj.isEmpty())
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash("" + this.row + this.col + this.isBee() + this.isNumber() + this.isEmpty());
    }

    @Override
    public String toString() {
        String representation = ".  ";
        if (this.revealed()) {
            if (this.isBee()) {
                representation = "^  ";
            } else if (this.isNumber()) {
                representation = this.number + "  ";
            }else{
                representation = "   ";
            }
        }else{
            if(this.isFlagged()){
                representation = "@  ";
            }
        }

        return representation;
    }

}