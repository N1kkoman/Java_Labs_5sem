import java.util.HashSet;
import java.util.Arrays;

class Main {

    // 1. Метод для нахождения наибольшей подстроки без повторяющихся символов
    public static String longestUniqueSubstring(String s) {
        int maxLength = 0; // Длина наибольшей уникальной подстроки
        String longestSubstring = ""; // Наибольшая уникальная подстрока
        HashSet<Character> charSet = new HashSet<>(); // Множество для хранения уникальных символов

        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            // Если символ уже существует в множестве, сдвигаем левую границу
            while (charSet.contains(s.charAt(right))) {
                charSet.remove(s.charAt(left));
                left++;
            }
            charSet.add(s.charAt(right)); // Добавляем текущий символ

            // Проверяем, является ли текущая подстрока самой длинной
            if (right - left + 1 > maxLength) {
                maxLength = right - left + 1;
                longestSubstring = s.substring(left, right + 1);
            }
        }
        return longestSubstring; // Возвращаем наибольшую уникальную подстроку
    }

    // 2. Метод для объединения двух отсортированных массивов
    public static int[] mergeArrays(int[] arr1, int[] arr2) {
        int n = arr1.length; // Длина первого массива
        int m = arr2.length; // Длина второго массива
        int[] mergedArray = new int[n + m]; // Новый массив для хранения объединенных данных
        int i = 0, j = 0, k = 0; // Индексы для обхода массивов

        // Объединяем два массива в отсортированном порядке
        while (i < n && j < m) {
            if (arr1[i] <= arr2[j]) {
                mergedArray[k++] = arr1[i++]; // Добавляем элемент из первого массива
            } else {
                mergedArray[k++] = arr2[j++]; // Добавляем элемент из второго массива
            }
        }

        // Копируем оставшиеся элементы первого массива
        while (i < n) {
            mergedArray[k++] = arr1[i++];
        }
        // Копируем оставшиеся элементы второго массива
        while (j < m) {
            mergedArray[k++] = arr2[j++];
        }

        return mergedArray;
    }

    // 3. Метод для нахождения максимальной суммы подмассива
    public static int maxSubArray(int[] nums) {
        int maxSum = nums[0]; // Инициализируем максимальную сумму первым элементом
        int currentSum = nums[0]; // Текущая сумма подмассива

        // Обходим массив, начиная со второго элемента
        for (int i = 1; i < nums.length; i++) {
            // Если текущий элемент больше, чем сумма текущего подмассива + этот элемент,
            // начинаем новый подмассив с текущего элемента
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            maxSum = Math.max(maxSum, currentSum); // Обновляем максимальную сумму
        }

        return maxSum;
    }

    // 4. Метод для поворота матрицы на 90 градусов по часовой стрелке
    public static int[][] rotate(int[][] matrix) {
        int n = matrix.length; // Количество строк
        int m = matrix[0].length; // Количество столбцов
        int[][] rotated = new int[m][n]; // Новый массив для хранения повернутой матрицы

        // Заполняем новый массив, поворачивая элементы
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                rotated[j][n - 1 - i] = matrix[i][j]; // Меняем местами элементы
            }
        }

        return rotated;
    }

    // 5. Метод для нахождения пары элементов в массиве, сумма которых равна заданному числу
    public static int[] findPairWithSum(int[] nums, int target) {
        // Обходим массив, используя два вложенных цикла
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                // Проверяем, равна ли сумма двух элементов целевому значению
                if (nums[i] + nums[j] == target) {
                    return new int[] {nums[i], nums[j]}; // Возвращаем пару
                }
            }
        }
        return null; // Если пара не найдена, возвращаем null
    }

    // 6. Метод для нахождения суммы всех элементов в двумерном массиве
    public static int sumOf2DArray(int[][] matrix) {
        int sum = 0; // Инициализируем сумму
        // Обходим все строки и все элементы в каждой строке
        for (int[] row : matrix) {
            for (int num : row) {
                sum += num; // Добавляем значение элемента к сумме
            }
        }
        return sum;
    }

    // 7. Метод для нахождения максимального элемента в каждой строке двумерного массива
    public static int[] maxInRows(int[][] matrix) {
        int[] maxInEachRow = new int[matrix.length]; // Массив для хранения максимальных значений
        // Обходим каждую строку
        for (int i = 0; i < matrix.length; i++) {
            int max = Integer.MIN_VALUE;
            for (int num : matrix[i]) {
                if (num > max) {
                    max = num; // Обновляем максимальное значение
                }
            }
            maxInEachRow[i] = max; // Сохраняем максимальное значение в массив
        }
        return maxInEachRow; // Возвращаем массив максимальных значений
    }

    // 8. Метод для поворота двумерного массива на 90 градусов против часовой стрелки
    public static int[][] rotateCounterClockwise(int[][] matrix) {
        int n = matrix.length; // Количество строк
        int m = matrix[0].length; // Количество столбцов
        int[][] rotated = new int[m][n]; // Новый массив для хранения повернутой матрицы

        // Заполняем новый массив, поворачивая элементы
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                rotated[m - j - 1][i] = matrix[i][j]; // Меняем местами элементы
            }
        }

        return rotated;
    }

    public static void main(String[] args) {
        // 1. Наибольшая подстрока без повторяющихся символов
        String s = "abcabcbb";
        System.out.println("1. Наибольшая подстрока без повторяющихся символов: " + longestUniqueSubstring(s));

        // 2. Объединение двух отсортированных массивов
        int[] arr1 = {13, 32, 35, 75};
        int[] arr2 = {2, 4, 6, 8};
        int[] merged = mergeArrays(arr1, arr2);
        System.out.println("2. Объединенный массив: " + Arrays.toString(merged));

        // 3. Максимальная сумма подмассива
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("3. Максимальная сумма подмассива: " + maxSubArray(nums));

        // 4. Поворот неквадратной матрицы на 90 градусов по часовой стрелке
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6}
        };
        int[][] rotated = rotate(matrix);
        System.out.println("4. Повернутая матрица по часовой стрелке:");
        for (int[] row : rotated) {
            System.out.println(Arrays.toString(row));
        }

        // 5. Найти пару элементов в массиве, сумма которых равна заданному числу
        int[] arrForPair = {10, 15, 3, 7};
        int target = 17;
        int[] pair = findPairWithSum(arrForPair, target);
        if (pair != null) {
            System.out.println("5. Пара с суммой " + target + ": " + Arrays.toString(pair));
        } else {
            System.out.println("5. Пара с суммой " + target + " не найдена.");
        }

        // 6. Сумма всех элементов в двумерном массиве
        int[][] twoDArray = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        System.out.println("6. Сумма всех элементов в двумерном массиве: " + sumOf2DArray(twoDArray));

        // 7. Максимальный элемент в каждой строке двумерного массива
        int[][] matrixForMax = {
                {1, 3, 5},
                {2, 4, 6},
                {3, 1, 0}
        };
        int[] maxElements = maxInRows(matrixForMax);
        System.out.println("7. Максимальные элементы в каждой строке: " + Arrays.toString(maxElements));

        // 8. Поворот двумерного массива на 90 градусов против часовой стрелки
        int[][] matrixCounterClockwise = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        int[][] rotatedCCW = rotateCounterClockwise(matrixCounterClockwise);
        System.out.println("8. Повернутая матрица против часовой стрелки:");
        for (int[] row : rotatedCCW) {
            System.out.println(Arrays.toString(row));
        }
    }
}