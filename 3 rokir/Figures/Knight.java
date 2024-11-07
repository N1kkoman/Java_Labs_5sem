package Figures;

public class Knight extends Figure {
    public Knight(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        if (!super.canMove(row, col, row1, col1)) return false;
        return (Math.abs(row - row1) == 2 && Math.abs(col - col1) == 1) ||
                (Math.abs(row - row1) == 1 && Math.abs(col - col1) == 2);
    }
}