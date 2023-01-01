package src;

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
    }

    private void getBlankPosition(){
        for(int i = 0; i < puzzle.length; i++){
            if(puzzle[i].equals(0))
                blankPosition = i;
        }
    }
}
