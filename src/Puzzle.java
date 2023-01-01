package src;

import java.util.Arrays;
import java.util.Random;

public class Puzzle {
    private Integer[] puzzle;
    private boolean puzzleIsValid;

    public Puzzle(){
        createRandomPuzzle();
    }

    public Puzzle(Integer[] puzzle){
        this.puzzle = puzzle;
    }

    private void createRandomPuzzle(){
        puzzleIsValid = false;
        int upperBound = 9;
        Random randInt = new Random();

        while(!puzzleIsValid){
            puzzle = new Integer[9];
            Arrays.fill(puzzle, -1);

            for(int i = 0; i < puzzle.length; i++){
                Integer tileInt = randInt.nextInt(upperBound);

                while(getIndex(puzzle, tileInt) != -1)
                    tileInt = randInt.nextInt(upperBound);

                puzzle[i] = tileInt;
            }

            puzzleIsValid = boardIsSolvable(puzzle);
        }
    }

    private int getIndex(Integer[] puzzle, Integer target){
        for(int i = 0; i < puzzle.length; i++){
            if(puzzle[i].equals(target))
                return i;
        }

        return -1;
    }

    private boolean boardIsSolvable(Integer[] puzzle){
        int inversions = 0;

        for(int i = 0; i < puzzle.length - 1; i++){
            if(puzzle[i].equals(0))
                continue;

            for(int j = i + 1; j < puzzle.length; j++){
                if(puzzle[j].equals(0))
                    continue;

                if(puzzle[j] < puzzle[i])
                    inversions++;
            }
        }

        return inversions % 2 == 0;
    }
}
