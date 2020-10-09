import java.util.Random;
import java.util.Scanner;

public class FourthLesson {
    public static final String USER_SIGN = "X";
    public static final String AI_SIGN = "O";
    public static final String DOT_EMPTY = ".";
    public static int aiLevel = 0;
    public static final int SIZE = 5;
    public static char[][] map = new char[SIZE][SIZE];

    public static void main(String[] args) {
        aiLevel();
    }

    public static void ticTacToe() {
        int count = 0;
        initField();
        while (true) {
            printField();
            humanTurn(USER_SIGN, 0);
            count++;
            if (checkWin(USER_SIGN)) {
                System.out.println("Вы победили");
                printField();
                break;
            }
            aiTurn();
            count++;
            if (checkWin(AI_SIGN)) {
                System.out.println("Компьютер победил");
                printField();
                break;
            }
            if (isFull()) {
                System.out.println("Ничья!");
                break;
            }
            if (count == Math.pow(SIZE, 2)) {
                printField();
                break;
            }
        }
    }

      public static void aiLevel() {                                //По советам в интеренете решил применить конструкцию switch-case для меню
        System.out.println("Выберите сложность компьютера: ");
        System.out.println("1. Простой.");                          //Исключительно Random
        System.out.println("2. Продвинутый.");                      //Блокирует выигрышный ход, в остальном Random
        System.out.println("3. Выход.");
        int i = 0;
        Scanner sc = new Scanner(System.in);
        i = sc.nextInt();
        switch (i) {
            case 1: {
                aiLevel = 0;
                ticTacToe();
                break;
            }
            case 2: {
                aiLevel = 1;
                ticTacToe();
                break;
            }
            case 3: {
                System.exit(0);
                break;
            }
            default: {
                System.out.println("Было введено неверное значение!");
            }
        }
    }

    public static void initField() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    public static void printField() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    static boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void humanTurn(String sign, int i) {
        int x = -1;
        int y = -1;
        do {
            System.out.println("Введите координаты x y (1 - " + SIZE + "): ");
            Scanner sc = new Scanner(System.in);
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        }
        while (isCellBusy(x, y));
        map[x][y] = sign;
    }

    public static void aiTurn() {
        int x = -1;
        int y = -1;
        boolean ai_win = false;
        boolean user_win = false;

        if (aiLevel == 1) {
            if (!ai_win) {
                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        if (!isCellBusy(i, j)) {
                            map[i][j] = USER_SIGN;
                            if (checkWin(USER_SIGN)) {
                                x = i;
                                y = j;
                                user_win = true;
                            }
                            map[i][j] = DOT_EMPTY;
                        }
                    }
                }
            }
        }
        if (!ai_win && !user_win) {
            do {
                Random rnd = new Random();
                x = rnd.nextInt(SIZE);
                y = rnd.nextInt(SIZE);
            }
            while (isCellBusy(x, y));
        }
        map[x][y] = AI_SIGN;
    }


    public static boolean isCellBusy(int x, int y) {
        if (x < 0 || y < 0 || x > SIZE - 1 || y > SIZE - 1) {
            return false;
        }
        return map[x][y] != DOT_EMPTY;
    }

    public static boolean checkLine(int start_x, int start_y, int dx, int dy, String sign) {
        for (int i = 0; i < SIZE; i++) {
            if (map[start_x + i * dx][start_y + i * dy] != sign)
                return false;
        }
        return true;
    }

    public static boolean checkWin(String sign) {           //Переделал проверкку победы
        for (int i = 0; i < SIZE; i++) {
             if (checkLine(i, 0, 0, 1, sign)) return true;
            if (checkLine(0, i, 1, 0, sign)) return true;
        }
        if (checkLine(0, 0, 1, 1, sign)) return true;
        if (checkLine(0, SIZE - 1, 1, -1, sign)) return true;
        return false;
    }
}
