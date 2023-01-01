package src;

import java.util.LinkedList;

public class Node {
    Node parent;
    Integer[] puzzle;
    int heuristic;
    int weight;
    int cost;
    int blankPosition = -1;

    public Node(Node parent, Integer[] puzzle, int heuristic, int cost){
        this.parent = parent;
        this.puzzle = puzzle;
        this.heuristic = heuristic;
        this.cost = cost;
        weight = heuristic + cost;

        blankPosition = getBlankPosition();
    }

    public int getBlankPosition(){
        if(!puzzle.equals(null))
        for(int i = 0; i < puzzle.length; i++){
            if(puzzle[i].equals(0))
                return i;
        }

        return -1;
    }

    public Integer[] generateState(String action){
        Integer[] tempState = puzzle.clone();
        int tempBlank = blankPosition;
        
        switch (action){
            case "u":
                tempState = swapVals(tempState, tempBlank, tempBlank - 3);
                tempBlank -= 3;
                break;
        
            case "d":
                tempState = swapVals(tempState, tempBlank, tempBlank + 3);
                tempBlank += 3;
                break;

            case "l":
                tempState = swapVals(tempState, tempBlank, --tempBlank);
                break;
            
            case "r":
                tempState = swapVals(tempState, tempBlank, ++tempBlank);
                break;

            default:
                break;
        }

        return tempState;
    }

    public boolean isValidMove(String action){
        switch (action) {
            case "u":
                if((blankPosition - 3) < 0)
                    return false;
                break;
        
            case "d":
                if((blankPosition + 3) >= puzzle.length)
                    return false;
                break;
            
            case "l":
                if(blankPosition == 0 || blankPosition % 3 == 0)
                    return false;
                break;
            
            case "r":
                if((blankPosition + 1) % 3 == 0)
                    return false;
            default:
                break;
        }

        return true;
    }

    private static Integer[] swapVals(Integer[] puzzle, int val1, int val2){
        Integer temp = puzzle[val1];
        puzzle[val1] = puzzle[val2];
        puzzle[val2] = temp;

        return puzzle;
    }
}
