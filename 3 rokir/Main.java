import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.init();
        board.setColorGame('w');

        boolean isGame = true;
        Scanner in = new Scanner(System.in);

        while (isGame) {
            board.print_board();
            System.out.println();

            char currentColor = board.getColorGame();

            // Проверка на шах
            if (board.isCheck(currentColor)) {
                System.out.println("ШАХ!");

                // Проверка на мат
                if (board.isCheckmate(currentColor)) {
                    System.out.println("МАТ! Игра окончена!");
                    System.out.println("Победили " + (currentColor == 'w' ? "черные" : "белые"));
                    break;
                }
            }

            System.out.println("Управление:");
            System.out.println("row col row1 col1: Ход фигуры из клетки (row, col) в (row1, col1)");
            System.out.println("castle row col row1 col1: Рокировка");

            switch (currentColor) {
                case 'w': System.out.println("Ход белых"); break;
                case 'b': System.out.println("Ход черных"); break;
            }

            System.out.print("Введите ход: ");
            String inputLine = in.nextLine();
            String[] inputs = inputLine.split(" ");

            if (inputs[0].equals("castle")) {
                int row = Integer.parseInt(inputs[1]);
                int col = Integer.parseInt(inputs[2]);
                int row1 = Integer.parseInt(inputs[3]);
                int col1 = Integer.parseInt(inputs[4]);

                if (board.canCastle(row, col, row1, col1)) {
                    board.doCastle(row, col, row1, col1);
                    switch (board.getColorGame()) {
                        case 'w': board.setColorGame('b'); break;
                        case 'b': board.setColorGame('w'); break;
                    }
                    continue;
                } else {
                    System.out.println("Невозможно выполнить рокировку!");
                    continue;
                }
            }

            // Обычный ход
            int row = Integer.parseInt(inputs[0]);
            int col = Integer.parseInt(inputs[1]);
            int row1 = Integer.parseInt(inputs[2]);
            int col1 = Integer.parseInt(inputs[3]);

            while (!board.move_figure(row, col, row1, col1)) {
                System.out.println("Ошибка! Повторите ход фигуры!");
                System.out.print("Введите ход: ");
                inputLine = in.nextLine();
                inputs = inputLine.split(" ");
                row = Integer.parseInt(inputs[0]);
                col = Integer.parseInt(inputs[1]);
                row1 = Integer.parseInt(inputs[2]);
                col1 = Integer.parseInt(inputs[3]);
            }

            switch (board.getColorGame()) {
                case 'w': board.setColorGame('b'); break;
                case 'b': board.setColorGame('w'); break;
            }
        }
    }
}