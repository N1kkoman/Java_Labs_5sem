package Figures;

public class Pawn extends Figure {
    private boolean isFirstStep = true;

    public Pawn(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        if (!super.canMove(row, col, row1, col1)) return false;

        if (col != col1) return false;

        if (this.isFirstStep) {
            if (this.getColor() == 'w') {
                if (row1 == row + 1 || row1 == row + 2) {
                    this.isFirstStep = false;
                    return true;
                }
            } else {
                if (row1 == row - 1 || row1 == row - 2) {
                    this.isFirstStep = false;
                    return true;
                }
            }
        } else {
            if (this.getColor() == 'w') {
                return row1 == row + 1;
            } else {
                return row1 == row - 1;
            }
        }
        return false;
    }

    @Override
    public boolean canAttack(int row, int col, int row1, int col1) {
        if (!super.canMove(row, col, row1, col1)) return false;

        if (Math.abs(col - col1) != 1) return false;

        if (this.getColor() == 'w') {
            return row1 == row + 1;
        } else {
            return row1 == row - 1;
        }
    }
}