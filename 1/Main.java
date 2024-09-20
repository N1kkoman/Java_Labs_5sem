import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Выберите задачу (1-5) или введите 0 для выхода:");
            int taskNumber = scanner.nextInt();

            if (taskNumber == 0) {
                System.out.println("Выход из программы.");
                break;
            }
            switch (taskNumber) {
                case 1:
                    System.out.println("Введите число для сиракузской последовательности:");
                    int n = scanner.nextInt();
                    System.out.println("Количество шагов: " + collatzSequence(n));
                    break;
                case 2:
                    System.out.println("Введите количество чисел и сами числа ряда:");
                    System.out.println("Сумма ряда: " + alternatingSum(scanner));
                    break;
                case 3:
                    System.out.println("Введите координаты клада:");
                    System.out.println("Минимальное количество указаний карты: " + findTreasure(scanner));
                    break;
                case 4:
                    System.out.println("Введите количество дорог:");
                    int roadCount = scanner.nextInt();
                    System.out.println("Номер дороги и максимальная высота грузовика: " + logisticMaximin(scanner, roadCount));
                    break;
                case 5:
                    System.out.println("Введите трехзначное число:");
                    System.out.println("Является ли число «дважды четным»? " + isDoubleEven(scanner));
                    break;
                default:
                    System.out.println("Задача не найдена");
            }
        }
    }

    // Функция для вычисления количества шагов в сиракузской последовательности.
    public static int collatzSequence(int n) {
        int steps = 0;
        while (n != 1) {
            if (n % 2 == 0) {
                n = n / 2;
            } else {
                n = 3 * n + 1;
            }
            steps++;
        }
        return steps;
    }

    // Функция для вычисления знакочередующейся суммы ряда.
    public static int alternatingSum(Scanner scanner) {
        int n = scanner.nextInt();
        int sum = 0;
        for (int i = 0; i < n; i++) {
            int num = scanner.nextInt();
            if (i % 2 == 0) {
                sum += num;
            } else {
                sum -= num;
            }
        }
        return sum;
    }

    // Функция для поиска клада
    public static int findTreasure(Scanner scanner) {
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        int currentX = 0;
        int currentY = 0;
        int steps = 0;
        int minSteps = Integer.MAX_VALUE; // Минимальное количество шагов до клада

        while (true) {
            String direction = scanner.next();
            if (direction.equals("стоп")) {
                break;
            }
            int distance = scanner.nextInt();

            switch (direction) {
                case "север":
                    currentY += distance;
                    break;
                case "юг":
                    currentY -= distance;
                    break;
                case "восток":
                    currentX += distance;
                    break;
                case "запад":
                    currentX -= distance;
                    break;
            }

            steps++; // Увеличиваем счетчик шагов

            // Проверка, достигли ли мы клада
            if (currentX == x && currentY == y) {
                minSteps = Math.min(minSteps, steps); // Обновляем минимальное количество шагов
            }
        }

        return minSteps == Integer.MAX_VALUE ? 0 : minSteps;
    }

    // Функция для вычисления максимальной высоты грузовика.
    public static String logisticMaximin(Scanner scanner, int roadCount) {
        int bestRoad = 1;
        int maxHeight = 0;
        for (int i = 1; i <= roadCount; i++) {
            int tunnelCount = scanner.nextInt();
            int minTunnelHeight = Integer.MAX_VALUE;
            for (int j = 0; j < tunnelCount; j++) {
                int height = scanner.nextInt();
                minTunnelHeight = Math.min(minTunnelHeight, height);
            }
            if (minTunnelHeight > maxHeight || (minTunnelHeight == maxHeight && i < bestRoad)) {
                bestRoad = i;
                maxHeight = minTunnelHeight;
            }
        }
        return bestRoad + " " + maxHeight;
    }


    // Функция для проверки, является ли число «дважды четным».
    public static boolean isDoubleEven(Scanner scanner) {
        int num = scanner.nextInt();
        int sum = 0;
        int product = 1;

        while (num > 0) {
            int digit = num % 10;
            sum += digit;
            product *= digit;
            num /= 10;
        }

        return sum % 2 == 0 && product % 2 == 0;
    }
}