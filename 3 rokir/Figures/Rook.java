package Figures;

public class Rook extends Figure {
    private boolean isFirstStep = true;

    public Rook(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        if (!super.canMove(row, col, row1, col1)) return false;
        return row == row1 || col == col1;
    }

    public boolean isFirstStep() {
        return isFirstStep;
    }

    public void setFirstStep(boolean firstStep) {
        this.isFirstStep = firstStep;
    }
}