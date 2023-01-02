package src;

import java.util.Arrays;
import java.util.Random;

public class Puzzle {
    public Integer[] puzzle;
    private boolean puzzleIsValid;

    public Puzzle(){ //create a random puzzle when no parameters are passed
        createRandomPuzzle();
    }

    public Puzzle(Integer[] puzzle){

    }

    public boolean createPuzzle(String puzzleString){
        if(!validPuzzle(puzzleString)) //check if the puzzle string has no words, duplicates, etc. before beginning
            return false;

        puzzle  = new Integer[9];

        for(int i = 0; i < puzzleString.length(); i++){
            Integer tileInt = Integer.parseInt(Character.toString(puzzleString.charAt(i)));

            puzzle[i] = tileInt; //pass characters of thes string into the puzzle array
        }

        if(!boardIsSolvable(puzzle)){ //check if amount of inversions are even
            System.out.println("The puzzle is not solvable!");
            return false;
        }

        return true;
    }

    private void createRandomPuzzle(){
        puzzleIsValid = false;
        int upperBound = 9;
        Random randInt = new Random();

        while(!puzzleIsValid){ //loop until the puzzle is solvable (even inversions)
            puzzle = new Integer[9];
            Arrays.fill(puzzle, -1);

            for(int i = 0; i < puzzle.length; i++){
                Integer tileInt = randInt.nextInt(upperBound); //get a random integer from 0-8

                while(getIndex(puzzle, tileInt) != -1) //if the random integer is not in the puzzle, place it in the puzzle
                    tileInt = randInt.nextInt(upperBound);

                puzzle[i] = tileInt;
            }

            puzzleIsValid = boardIsSolvable(puzzle); //check if inversions are even
        }
    }

    private int getIndex(Integer[] puzzle, Integer target){ //return the index of a given value, -1 if it is not present
        for(int i = 0; i < puzzle.length; i++){
            if(puzzle[i].equals(target))
                return i;
        }

        return -1;
    }

    private boolean boardIsSolvable(Integer[] puzzle){ //check the amount of inversions in a given puzzle
        int inversions = 0;

        for(int i = 0; i < puzzle.length - 1; i++){
            if(puzzle[i].equals(0)) //get next element if the tile is blank (0)
                continue;

            for(int j = i + 1; j < puzzle.length; j++){
                if(puzzle[j].equals(0)) //get next element if the tile is blank (0)
                    continue;

                if(puzzle[j] < puzzle[i]) //check if the tile preceding a tile is greater, increasing inversions if so
                    inversions++;
            }
        }

        return inversions % 2 == 0;
    }

    private boolean validPuzzle(String puzzleString){ //check if the user entered a string with no duplicate values, 9 tiles, and only integers between 0-8
        int duplicateCheck = 0;

        for(int i = 0; i < puzzleString.length(); i++){
            try { //try converting the char into an integer, returning false if it is not an int
                    Integer num = Integer.parseInt(Character.toString(puzzleString.charAt(i)));
                    if(num < 0 || num > 8){ //check if value is between 0-8
                        System.out.println("All values must be between 0 and 8.");
                        return false;
                    }

                    int val = puzzleString.charAt(i) - 'a';
                    if ((duplicateCheck & (1 << val)) > 0){ //check for duplicate tiles
                        System.out.println("The puzzle cannot contain duplicate values!");
                        return false;
                    }

                    duplicateCheck |= (1 << val);
            } catch (NumberFormatException e) {
                System.out.println("The puzzle contained non-integer values!");
                return false;
            }
        }

        if(puzzleString.length() != 9){ //check if 9 tiles are entered
            System.out.println("The puzzle did not contain 9 numbers!");;
            return false;
        }


        return true;
    }
}
