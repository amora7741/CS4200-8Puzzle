package src;

import java.util.LinkedList;
import java.util.Queue;
import src.MoveBlank.*;

public class MisplacedTiles {
    private static int depth;
    private static int solutionSize;
    private static Integer[] temp = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
    private static Node emptyNode = new Node(null, temp, -1, -1);
    private int blankPosition = -1;


    public static int getMisplacedTileCount(Integer[] puzzle){
        int count = 0;

        for(int i = 0; i < puzzle.length; i++){
            if(puzzle[i] != i)
                count++;
        }

        return count;
    }

    private static Queue<Node> expandNode(Node node){
        Integer[] currentPuzzle = node.puzzle;
        Queue<Node> output = new LinkedList<>();
        Integer[] tempBoard;

        
        
    }

    private static Node generateNode(String action, Node parent){
        Node node;
        Integer[] tempState = parent.generateState(action);

        node = new Node(parent, tempState, getMisplacedTileCount(tempState), depth + 1);

        return node;
    }
}
