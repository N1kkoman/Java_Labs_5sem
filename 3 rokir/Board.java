import Figures.*;
import java.util.*;

public class Board {
    private char colorGame;
    private Figure[][] fields = new Figure[8][8];

    public void setColorGame(char colorGame) {
        this.colorGame = colorGame;
    }

    public char getColorGame() {
        return colorGame;
    }

    public void init() {
        // Белые фигуры
        this.fields[0] = new Figure[]{
                new Rook("R", 'w'), new Knight("N", 'w'), new Bishop("B", 'w'), new Queen("Q", 'w'),
                new King("K", 'w'), new Bishop("B", 'w'), new Knight("N", 'w'), new Rook("R", 'w')
        };
        this.fields[1] = new Figure[]{
                new Pawn("P", 'w'), new Pawn("P", 'w'), new Pawn("P", 'w'), new Pawn("P", 'w'),
                new Pawn("P", 'w'), new Pawn("P", 'w'), new Pawn("P", 'w'), new Pawn("P", 'w')
        };

        // Черные фигуры
        this.fields[7] = new Figure[]{
                new Rook("R", 'b'), new Knight("N", 'b'), new Bishop("B", 'b'), new Queen("Q", 'b'),
                new King("K", 'b'), new Bishop("B", 'b'), new Knight("N", 'b'), new Rook("R", 'b')
        };
        this.fields[6] = new Figure[]{
                new Pawn("P", 'b'), new Pawn("P", 'b'), new Pawn("P", 'b'), new Pawn("P", 'b'),
                new Pawn("P", 'b'), new Pawn("P", 'b'), new Pawn("P", 'b'), new Pawn("P", 'b')
        };
    }

    public String getCell(int row, int col) {
        Figure figure = this.fields[row][col];
        if (figure == null) {
            return "    ";
        }
        return " " + figure.getColor() + figure.getName() + " ";
    }

    public void print_board() {
        System.out.println(" +----+----+----+----+----+----+----+----+");
        for (int row = 7; row > -1; row--) {
            System.out.print(row);
            for (int col = 0; col < 8; col++) {
                System.out.print("|" + getCell(row, col));
            }
            System.out.println("|");
            System.out.println(" +----+----+----+----+----+----+----+----+");
        }
        for (int col = 0; col < 8; col++) {
            System.out.print("    " + col);
        }
        System.out.println();
    }

    private boolean isPathClear(int row, int col, int row1, int col1) {
        int rowStep = Integer.compare(row1, row);
        int colStep = Integer.compare(col1, col);

        int currentRow = row + rowStep;
        int currentCol = col + colStep;

        while (currentRow != row1 || currentCol != col1) {
            if (fields[currentRow][currentCol] != null) {
                return false;
            }
            currentRow += rowStep;
            currentCol += colStep;
        }
        return true;
    }

    private boolean isKingUnderAttack(char color) {
        int kingRow = -1, kingCol = -1;

        // Находим короля
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (fields[i][j] instanceof King && fields[i][j].getColor() == color) {
                    kingRow = i;
                    kingCol = j;
                    break;
                }
            }
        }

        // Проверяем атаки всех вражеских фигур
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (fields[i][j] != null && fields[i][j].getColor() != color) {
                    if (fields[i][j].canAttack(i, j, kingRow, kingCol) &&
                            isPathClear(i, j, kingRow, kingCol)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isCheck(char color) {
        return isKingUnderAttack(color);
    }

    public boolean isCheckmate(char color) {
        if (!isCheck(color)) {
            return false;
        }

        // Проверяем все возможные ходы
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Figure figure = fields[row][col];
                if (figure != null && figure.getColor() == color) {
                    for (int newRow = 0; newRow < 8; newRow++) {
                        for (int newCol = 0; newCol < 8; newCol++) {
                            Figure tempTarget = fields[newRow][newCol];
                            if (move_figure(row, col, newRow, newCol)) {
                                fields[row][col] = figure;
                                fields[newRow][newCol] = tempTarget;
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean canCastle(int row, int col, int row1, int col1) {
        Figure king = fields[row][col];
        Figure rook = fields[row1][col1];

        if (!(king instanceof King) || !(rook instanceof Rook)) {
            return false;
        }

        if (king.getColor() != rook.getColor()) {
            return false;
        }

        if (((King)king).isFirstStep() && ((Rook)rook).isFirstStep()) {
            int direction = col1 > col ? 1 : -1;

            // Проверка пустых клеток между королем и ладьей
            for (int i = col + direction; i != col1; i += direction) {
                if (fields[row][i] != null) {
                    return false;
                }
            }

            // Проверка безопасности пути короля
            if (isKingUnderAttack(king.getColor())) {
                return false;
            }

            int tempCol = col;
            while (tempCol != col + 2 * direction) {
                tempCol += direction;
                fields[row][tempCol] = king;
                fields[row][col] = null;
                if (isKingUnderAttack(king.getColor())) {
                    fields[row][col] = king;
                    fields[row][tempCol] = null;
                    return false;
                }
                fields[row][col] = king;
                fields[row][tempCol] = null;
            }

            return true;
        }
        return false;
    }

    public void doCastle(int row, int col, int row1, int col1) {
        if (col1 > col) { // Короткая рокировка
            fields[row][col + 2] = fields[row][col];
            fields[row][col + 1] = fields[row][col1];
            fields[row][col] = null;
            fields[row][col1] = null;
        } else { // Длинная рокировка
            fields[row][col - 2] = fields[row][col];
            fields[row][col - 1] = fields[row][col1];
            fields[row][col] = null;
            fields[row][col1] = null;
        }
    }

    private void promotePawn(int row, int col) {
        Scanner scanner = new Scanner(System.in);
        Figure pawn = fields[row][col];

        System.out.println("Выберите фигуру для превращения пешки:");
        System.out.println("1 - Ферзь");
        System.out.println("2 - Ладья");
        System.out.println("3 - Слон");
        System.out.println("4 - Конь");

        int choice = scanner.nextInt();
        char color = pawn.getColor();

        switch (choice) {
            case 1:
                fields[row][col] = new Queen("Q", color);
                break;
            case 2:
                fields[row][col] = new Rook("R", color);
                break;
            case 3:
                fields[row][col] = new Bishop("B", color);
                break;
            case 4:
                fields[row][col] = new Knight("N", color);
                break;
            default:
                fields[row][col] = new Queen("Q", color);
        }
    }

    public boolean move_figure(int row, int col, int row1, int col1) {
        Figure figure = this.fields[row][col];
        if (figure == null || figure.getColor() != this.colorGame) {
            return false;
        }

        boolean canMove = false;

        // Проверка возможности хода
        if (figure.canMove(row, col, row1, col1)) {
            if (fields[row1][col1] == null) {
                canMove = true;
            } else if (fields[row1][col1].getColor() != figure.getColor()) {
                canMove = figure.canAttack(row, col, row1, col1);
            }
        }

        if (canMove && isPathClear(row, col, row1, col1)) {
            // Сохраняем состояние для возможной отмены хода
            Figure tempTarget = fields[row1][col1];

            // Выполняем ход
            fields[row1][col1] = figure;
            fields[row][col] = null;

            // Проверяем, не под шахом ли король после хода
            if (isKingUnderAttack(figure.getColor())) {
                // Отменяем ход
                fields[row][col] = figure;
                fields[row1][col1] = tempTarget;
                return false;
            }

            // Проверяем превращение пешки
            if (figure instanceof Pawn) {
                if ((figure.getColor() == 'w' && row1 == 7) ||
                        (figure.getColor() == 'b' && row1 == 0)) {
                    promotePawn(row1, col1);
                }
            }

            // Обновляем состояние первого хода для соответствующих фигур
            if (figure instanceof King) {
                ((King)figure).setFirstStep(false);
            }
            if (figure instanceof Rook) {
                ((Rook)figure).setFirstStep(false);
            }

            return true;
        }
        return false;
    }
}