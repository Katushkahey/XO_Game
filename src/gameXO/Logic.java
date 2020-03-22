package gameXO;

import java.util.Random;

class Logic {

    final static char DOT_X = 'X';
    final static char DOT_O = 'O';
    private final static char EMPTY_DOT = '.';
    static int size =3;
    static int game_mode = 0;
    static char[][] map;
    private static Random rand = new Random();
    static boolean gameFinished = false;
    static String winnerName;
    static boolean player1IsPlayed = false;

    private static void goGameMode0() {
        gameFinished = true;
        if (checkWin(map, DOT_X)) {
            winnerName = "Вы победили!!!";
            return;
        } else if (isFullMap(map)) {
            winnerName = "Ничья!";
            return;
        } else {
            computerTurn(map);
            if (checkWin(map, DOT_O)) {
                winnerName = "Компьютер победил!!!";
                return;
            }else if (isFullMap(map)) {
                winnerName = "Ничья!";
                return;
            }
        }
        gameFinished = false;
    }

    private static void goGameMode1(int playNumber) {
        gameFinished = true;
        char a = playNumber == 1? DOT_X: DOT_O;
        if (checkWin(map, a)) {
            winnerName = ("Игрок " + playNumber+ " победил !!!");
            return;
        } else if (isFullMap(map)) {
            winnerName = "Ничья!";
            return;
        }
        gameFinished = false;
    }

    static void initMap() {
        map = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map [i][j] = EMPTY_DOT;
            }
        }
    }

//    private static void printMap(char[][] arr) {       метод для отладки.
//        System.out.print("  ");
//        for (int i = 1; i <= size; i++) {
//            System.out.print(i + " ");
//        }
//        System.out.println();
//        for (int i = 0; i < size ; i++) {
//            System.out.print(i + 1 + " ");
//            for (int j = 0; j < size ; j++) {
//                System.out.print(arr[i][j] + " ");
//            }
//            System.out.println();
//        }
//    }

    static void setHumanXY (int x, int y, int playNumber) {
        if (isCellValid(y, x)) {
            if (playNumber == 1) {
                map[y][x] = DOT_X;
            } else {
                map[y][x] = DOT_O;
            } if (game_mode == 0) {
                goGameMode0();
            } else {
                goGameMode1(playNumber);
            }
        }
    }

    private static boolean isCellValid (int y, int x) {
        if (x < 0 || y < 0 || x >= size || y >= size) {
            return false;
        }
        return (map[y][x] == EMPTY_DOT) ;
    }

    private static boolean isFullMap (char [][] map) {
        for (int i = 0; i < map.length ; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == EMPTY_DOT) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkWin(char[][] arr, char a) {
        return checkWinLines(arr, a) || checkWinColumn(arr, a) || checkWinMainDiagonal(arr, a) ||
                checkWinMinorDiagonal(arr, a);
    }

    private static boolean checkWinLines (char[][] arr, char a) {
        for (int i = 0; i < arr.length; i++) {
            int k = 0;
            if (arr[i][0] == a) {
                k++;
                for (int j = 1; j < arr[i].length; j++) {
                    if (arr[i][j] == a) {
                        k++;
                    } else {
                        break;
                    }
                }
                if (k == arr[i].length) {
                    return true;
                }
            }
        }
       return false;
    }

    private static boolean checkWinColumn (char[][] arr, char a) {
        for (int i = 0; i < arr.length; i++) {
            int k = 0;
            if (arr[0][i] == a) {
                k++;
                for (int j = 1; j < arr[i].length; j++) {
                    if (arr[j][i] == a) {
                        k++;
                    } else {
                        break;
                    }
                }
                if (k == arr[i].length) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkWinMainDiagonal(char[][] arr, char a) {
        int k = 0;
        if (arr[0][0] == a) {
            k++;
            for (int i = 1; i < size; i++) {
                if (arr[i][i] == a) {
                    k++;
                } else {
                    break;
                }
            } if (k == arr.length) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkWinMinorDiagonal (char[][] arr, char a) {
        int k = 0;
        if (arr[0][size-1] == a) {
            k++;
            for (int i = size-2; i >= 0 ; i--) {
                if (arr[size-1-i][i] == a) {
                    k++;
                } else {
                    break;
                }
            } if (k == arr.length) {
                return true;
            }
        }
        return false;
    }

    private static void computerTurn (char[][] arr) {
        if (!tryToWin(arr) && !tryToBlock(arr)) {
            int x;
            int y;
            do {
                x = rand.nextInt(size);
                y = rand.nextInt(size);
            } while (!isCellValid(y, x));
            map[y][x] = DOT_O;
//            printMap(map);
        }
    }

    private static boolean tryToWin (char[][] arr) {
        for (int i = 0; i < arr.length ; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (isCellValid(i,j)) {
                    arr[i][j] = DOT_O;
                    if (checkWin(map, DOT_O)) {
//                        printMap(map);
                        return true;
                    } else {
                        arr [i][j] = EMPTY_DOT;
                    }
                }
            }
        }
        return false;
    }

    private static boolean tryToBlock (char [][] arr) {
        for (int i = 0; i < arr.length ; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (isCellValid(i,j)) {
                    arr[i][j] = DOT_X;
                    if (checkWin(map, DOT_X)) {
                        arr[i][j] = DOT_O;
//                        printMap(map);
                        return true;
                    } else {
                        arr [i][j] = EMPTY_DOT;
                    }
                }
            }
        }
        return false;
    }
}

