package direelf;

import java.util.Arrays;
import java.util.Scanner;

public class App {

    public static int countOfX = 0;
    public static int countOfO = 0;
    public static int countOfEmptyCells = 9;
    public static int countLineOfX = 0;
    public static int countLineOfO = 0;
    public static boolean hasWinner = false;
    public static boolean isDraw = false;

    public static void main(String[] args) {
        String userInput = "_________";
        char[][] gameField = createGameField(userInput);
        printGameField(gameField);
        while (true) {
            askForInputAndCheckIt(gameField, 'X');
            printGameField(gameField);
            checkWinner(gameField);
            if (hasWinner || isDraw) {
                break;
            }
            askForInputAndCheckIt(gameField, 'O');
            printGameField(gameField);
            checkWinner(gameField);
            if (hasWinner || isDraw) {
                break;
            }
        }
        printResult();
    }

    public static char[][] createGameField(String userInput) {
        int gridLength = (int) Math.sqrt(userInput.length());
        char[][] gameField = new char[gridLength][gridLength];
        for (int i = 0; i < gridLength; i++) {
            int rangeStart = gridLength * i;
            int rangeEnd = gridLength * (i + 1);
            gameField[i] = userInput.substring(rangeStart, rangeEnd).toCharArray();
        }
        return gameField;
    }

    public static void printGameField(char[][] gameField) {
        for (int i = 0; i < gameField.length * 2 + 3; i++) {
            System.out.print("-");
        }
        System.out.println();
        for (char[] line : gameField) {
            String lineOfField = Arrays.toString(line);
            lineOfField = lineOfField.substring(1, lineOfField.length() - 1);
            System.out.printf("| %s |\n", lineOfField.replaceAll(",", ""));
        }
        for (int i = 0; i < gameField.length * 2 + 3; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    public static void askForInputAndCheckIt(char[][] gameField, char gamer) {
        while (true) {
            Scanner userInput = new Scanner(System.in);
            int rowNumber;
            int columnNumber;
            System.out.print("Enter the coordinates: ");
            if (userInput.hasNextInt()) {
                rowNumber = userInput.nextInt();
            } else {
                System.out.println("You should enter numbers!");
                continue;
            }
            if (userInput.hasNextInt()) {
                columnNumber = userInput.nextInt();
            } else {
                System.out.println("You should enter numbers!");
                continue;
            }
            if (rowNumber < 1 || rowNumber > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            } else if (columnNumber < 1 || columnNumber > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
            if (gameField[rowNumber - 1][columnNumber - 1] != '_') {
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                gameField[rowNumber - 1][columnNumber - 1] = gamer;
                if (gamer == 'X') {
                    countOfX++;
                } else {
                    countOfO++;
                }
                countOfEmptyCells--;
                break;
            }
        }
    }

    public static void checkWinner(char[][] gameField) {
        //check horizontal lines
        for (char[] line : gameField) {
            int count = 0;
            for (char c : line) {
                count += c;
            }
            if (count == 264) {
                hasWinner = true;
                countLineOfX++;
            } else if (count == 237) {
                hasWinner = true;
                countLineOfO++;
            }
        }
        //check vertical lines
        for (int i = 0; i < gameField[0].length; i++) {
            int count = 0;
            for (char[] chars : gameField) {
                count += chars[i];
                if (count == 264) {
                    hasWinner = true;
                    countLineOfX++;
                } else if (count == 237) {
                    hasWinner = true;
                    countLineOfO++;
                }
            }
        }
        //check diagonal from left to right
        int result = 0;
        int numberOfColumn = 0;
        for (char[] chars : gameField) {
            result += chars[numberOfColumn];
            numberOfColumn++;
        }
        if (result == 264) {
            hasWinner = true;
            countLineOfX++;
        } else if (result == 237) {
            hasWinner = true;
            countLineOfO++;
        }
        //check second diagonal
        int result2 = 0;
        int numberOfColumn2 = gameField.length - 1;
        for (char[] chars : gameField) {
            result2 += chars[numberOfColumn2];
            numberOfColumn2--;
        }
        if (result2 == 264) {
            hasWinner = true;
            countLineOfX++;
        } else if (result2 == 237) {
            hasWinner = true;
            countLineOfO++;
        }
        //check draw
        if (countOfX + countOfO == gameField.length * gameField.length && !hasWinner) {
            isDraw = true;
        }
    }

    public static void printResult() {
        if (hasWinner) {
            if (countLineOfX > 0) {
                System.out.println("X wins");
            } else {
                System.out.println("O wins");
            }
        } else {
            System.out.println("Draw");
        }
    }
}
