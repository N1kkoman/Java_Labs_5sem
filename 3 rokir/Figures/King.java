package Figures;

public class King extends Figure {
    private boolean isFirstStep = true;

    public King(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        if (!super.canMove(row, col, row1, col1)) return false;
        return Math.abs(row - row1) <= 1 && Math.abs(col - col1) <= 1;
    }

    public boolean isFirstStep() {
        return isFirstStep;
    }

    public void setFirstStep(boolean firstStep) {
        this.isFirstStep = firstStep;
    }
}