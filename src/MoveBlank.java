package src;

public class MoveBlank {
    public static Integer[] moveUp(Integer[] puzzle, int i){ //used to move the blank tile up
        swapVals(puzzle, i, i - 3); //swap values of blank tile and tile above it

        return puzzle;
    }

    public static Integer[] moveDown(Integer[] puzzle, int i){ //used to move the blank tile down
        swapVals(puzzle, i, i + 3);

        return puzzle;
    }

    public static Integer[] moveLeft(Integer[] puzzle, int i){ //used to move the blank tile left
        swapVals(puzzle, i, i - 1);

        return puzzle;
    }

    public static Integer[] moveRight(Integer[] puzzle, int i){ //used to move the blank tile right
        swapVals(puzzle, i, i + 1);

        return puzzle;
    }

    private static Integer[] swapVals(Integer[] puzzle, int val1, int val2){ //swaps the values of two given entries
        Integer temp = puzzle[val1];
        puzzle[val1] = puzzle[val2];
        puzzle[val2] = temp;

        return puzzle;
    }
}
