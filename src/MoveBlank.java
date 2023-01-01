package src;

public class MoveBlank {
    public static Integer[] moveUp(Integer[] puzzle, int i){
        swapVals(puzzle, i, i - 3);

        return puzzle;
    }

    public static Integer[] moveDown(Integer[] puzzle, int i){
        swapVals(puzzle, i, i + 3);

        return puzzle;
    }

    public static Integer[] moveLeft(Integer[] puzzle, int i){
        swapVals(puzzle, i, i - 1);

        return puzzle;
    }

    public static Integer[] moveRight(Integer[] puzzle, int i){
        swapVals(puzzle, i, i + 1);

        return puzzle;
    }

    private static Integer[] swapVals(Integer[] puzzle, int val1, int val2){
        Integer temp = puzzle[val1];
        puzzle[val1] = puzzle[val2];
        puzzle[val2] = temp;

        return puzzle;
    }
}
