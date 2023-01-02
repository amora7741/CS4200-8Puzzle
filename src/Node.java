package src;

public class Node {
    Node parent;
    Integer[] puzzle;
    int heuristic;
    int weight;
    int depth;
    int blankPosition = -1;

    public Node(Node parent, Integer[] puzzle, int heuristic, int depth){
        this.parent = parent;
        this.puzzle = puzzle;
        this.heuristic = heuristic;
        this.depth = depth;
        weight = heuristic + depth;

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
                tempState = MoveBlank.moveUp(tempState, tempBlank);
                tempBlank -= 3;
                break;
        
            case "d":
                tempState = MoveBlank.moveDown(tempState, tempBlank);
                tempBlank += 3;
                break;

            case "l":
                tempState = MoveBlank.moveLeft(tempState, tempBlank--);
                break;
            
            case "r":
                tempState = MoveBlank.moveRight(tempState, tempBlank++);
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

    public String toString(){
        String puzzleString = "";

        for(int i = 0; i < puzzle.length; i++){
            if(i != 0 && i % 3 == 0)
                puzzleString += "\n";

            puzzleString += puzzle[i] + " ";
        }

        return puzzleString;
    }
}
