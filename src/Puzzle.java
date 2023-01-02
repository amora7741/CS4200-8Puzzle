package src;

import java.util.Arrays;
import java.util.Random;

public class Puzzle {
    public Integer[] puzzle;
    private boolean puzzleIsValid;

    public Puzzle(){
        createRandomPuzzle();
    }

    public Puzzle(Integer[] puzzle){

    }

    public boolean createPuzzle(String puzzleString){
        if(!validPuzzle(puzzleString))
            return false;

        puzzle  = new Integer[9];

        for(int i = 0; i < puzzleString.length(); i++){
            Integer tileInt = Integer.parseInt(Character.toString(puzzleString.charAt(i)));

            puzzle[i] = tileInt;
        }

        if(!boardIsSolvable(puzzle)){
            System.out.println("The puzzle is not solvable!");
            return false;
        }

        return true;
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

    private boolean validPuzzle(String puzzleString){
        int duplicateCheck = 0;

        for(int i = 0; i < puzzleString.length(); i++){
            try {
                    Integer num = Integer.parseInt(Character.toString(puzzleString.charAt(i)));
                    if(num < 0 || num > 8){
                        System.out.println("All values must be between 0 and 8.");
                        return false;
                    }

                    int val = puzzleString.charAt(i) - 'a';
                    if ((duplicateCheck & (1 << val)) > 0){
                        System.out.println("The puzzle cannot contain duplicate values!");
                        return false;
                    }

                    duplicateCheck |= (1 << val);
            } catch (NumberFormatException e) {
                System.out.println("The puzzle contained non-integer values!");
                return false;
            }
        }

        if(puzzleString.length() != 9){
            System.out.println("The puzzle did not contain 9 numbers!");;
            return false;
        }


        return true;
    }
}
