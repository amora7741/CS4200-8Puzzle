package src;

public class Node {
    Node parent;
    Integer[] puzzle;
    int heuristic;
    int weight;
    int depth;
    int blankPosition = -1;

    public Node(Node parent, Integer[] puzzle, int heuristic, int depth){ //stores the parent, state of the puzzle, heuristic choice, and depth of a node
        this.parent = parent;
        this.puzzle = puzzle;
        this.heuristic = heuristic;
        this.depth = depth;
        weight = heuristic + depth;

        blankPosition = getBlankPosition();
    }

    public int getBlankPosition(){ //find the blank tile in the current puzzle and return it
        for(int i = 0; i < puzzle.length; i++){
            if(puzzle[i].equals(0)) //return index of the blank tile
                return i;
        }

        return -1;
    }

    public Integer[] generateState(String action){ //generates a new puzzle based on the given movement
        Integer[] tempState = puzzle.clone();
        int tempBlank = blankPosition;
        
        switch (action){ //check if inputted movement is up/down/left/right
            case "u":
                tempState = MoveBlank.moveUp(tempState, tempBlank); //generate new puzzle with empty tile moving upwards
                tempBlank -= 3;
                break;
        
            case "d":
                tempState = MoveBlank.moveDown(tempState, tempBlank); //generate new puzzle with empty tile moving down
                tempBlank += 3;
                break;

            case "l":
                tempState = MoveBlank.moveLeft(tempState, tempBlank--); //generate new puzzle with empty tile moving left
                break;
            
            case "r":
                tempState = MoveBlank.moveRight(tempState, tempBlank++); //generate new puzzle with empty tile moving right
                break;

            default:
                break;
        }

        return tempState;
    }

    public boolean isValidMove(String action){ //check if the given empty tile movement is possible (inbounds)
        switch (action) {
            case "u":
                if((blankPosition - 3) < 0) //if movement is up, return false if index would be negative (out of bounds)
                    return false;
                break;
        
            case "d":
                if((blankPosition + 3) >= puzzle.length) //if movement is down, return false if blank tile index >= 9 (out of bounds)
                    return false;
                break;
            
            case "l":
                if(blankPosition == 0 || blankPosition % 3 == 0) //if movement is left, check if blank tile is first in first, second or third row
                    return false;
                break;
            
            case "r": //if movement is right, check if blank tile is at the end of the first, second or third row
                if((blankPosition + 1) % 3 == 0)
                    return false;
            default:
                break;
        }

        return true;
    }

    public String toString(){ //turns the puzzle array into a string for printing
        String puzzleString = "";

        for(int i = 0; i < puzzle.length; i++){
            if(i != 0 && i % 3 == 0) //if end of row is reached, add a new line character
                puzzleString += "\n";

            puzzleString += puzzle[i] + " "; //add tile with a space in between
        }

        return puzzleString;
    }
}
