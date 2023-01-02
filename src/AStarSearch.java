package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.Iterator;

public class AStarSearch {
    private static int solutionDepth;
    private static int searchCost;
    private static Integer[] temp = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
    private static Integer[] goalState = {0,1,2,3,4,5,6,7,8};
    private static Node emptyNode = new Node(null, temp, -1, -1);

    public static void aStar(Integer[] board, int heuristic){
        Node startingNode = heuristic == 1 ? new Node(emptyNode, board, getMisplacedTileCount(board), 0) : new Node(emptyNode, board, manhattanHeuristic(board), 0);
        Node finalNode = null;
        List<Node> exploredNodes = new ArrayList<>();
        boolean solutionReached = false;
        solutionDepth = 0;
        searchCost = 0;

        PriorityQueue<Node> frontier;
        if(heuristic == 1)
            frontier = new PriorityQueue<>((final Node n1, final Node n2) -> (n1.depth + getMisplacedTileCount(n1.puzzle)) - (n2.depth + getMisplacedTileCount(n2.puzzle)));
        else
            frontier = new PriorityQueue<>((final Node n1, final Node n2) -> (n1.depth + manhattanHeuristic(n1.puzzle)) - (n2.depth + manhattanHeuristic(n2.puzzle)));

        frontier.add(startingNode);
        Node q;
        Queue<Node> children;

        while (!frontier.isEmpty()) {
            q = frontier.poll();
            solutionDepth = q.depth;
            children = expandNode(q);

            while(!children.isEmpty()){
                Node child = children.poll();
                if(Arrays.equals(child.puzzle, goalState)){
                    finalNode = child;
                    solutionReached = true;
                    break;
                }

                int index = containsBoard(child, frontier);

                if(index < frontier.size()){
                    Iterator<Node> iter = frontier.iterator();
                    Node item = null;

                    while(index > 0){
                        item = iter.next();
                        index--;
                    }

                    if(child.weight < item.weight){
                        frontier.remove(item);
                        frontier.add(child);
                        continue;
                    }
                    else
                        continue;
                }

                index = containsBoard(child, exploredNodes);
                Node item = null;

                if(index < exploredNodes.size()){
                    item = exploredNodes.get(index);
                    if(child.weight < item.weight){
                        exploredNodes.remove(index);
                        frontier.add(child);
                        continue;
                    }
                    else
                        continue;
                }
                frontier.add(child);
            }

            if (solutionReached)
                break;

            exploredNodes.add(q);
        }

        OutputData output = new OutputData(printSolution(finalNode), searchCost);
        return;
    }
    

    public static int getMisplacedTileCount(Integer[] puzzle){
        int count = 0;

        for(int i = 0; i < puzzle.length; i++){
            if(puzzle[i] != i)
                count++;
        }

        return count;
    }

    public static int manhattanHeuristic(Integer[] puzzle){
        int totalDistance = 0;

        for(int i = 0; i < puzzle.length; i++){
            if(puzzle[i] == i || puzzle[i].equals(0))
                continue;

            int rowIndex = puzzle[i] / 3;
            int columnIndex = puzzle[i] % 3;

            int goalRow = i / 3;
            int goalColumn = i % 3;

            totalDistance += Math.abs(rowIndex - goalRow) + Math.abs(columnIndex - goalColumn);
        }

        return totalDistance;
    }

    private static Queue<Node> expandNode(Node parent){
        Queue<Node> output = new LinkedList<>();
        Node child;

        if(parent.isValidMove("u")){
            searchCost++;
            child = generateNode("u", parent);

            if(!Arrays.equals(child.puzzle, parent.parent.puzzle))
                output.add(child);
        }

        if(parent.isValidMove("d")){
            searchCost++;
            child = generateNode("d", parent);

            if(!Arrays.equals(child.puzzle, parent.parent.puzzle))
                output.add(child);
        }

        if(parent.isValidMove("l")){
            searchCost++;
            child = generateNode("l", parent);

            if(!Arrays.equals(child.puzzle, parent.parent.puzzle))
                output.add(child);
        }

        if(parent.isValidMove("r")){
            searchCost++;
            child = generateNode("r", parent);

            if(!Arrays.equals(child.puzzle, parent.parent.puzzle))
                output.add(child);
        }

        return output;
    }

    private static Node generateNode(String action, Node parent){
        Node node;
        Integer[] tempState = parent.generateState(action);

        node = new Node(parent, tempState, getMisplacedTileCount(tempState), solutionDepth + 1);

        return node;
    }

    private static int containsBoard(Node in, PriorityQueue<Node> q) {
        Iterator<Node> i = q.iterator();
        int index = 0;
        while(i.hasNext()){
            index++;

            if(isEqual(in.puzzle, i.next().puzzle))
                break;
        }
        return index;
    }

    private static int containsBoard(Node in, List<Node> list) {
        Iterator<Node> i = list.iterator();
        int index = 0;
        while(i.hasNext()){
            index++;
            //use comparator function here
            if(isEqual(in.puzzle, i.next().puzzle)){
                index--;
                break;
            }
        }
        return index;
    }

    private static int printSolution(Node nodeCurrent) {
        Stack<Node> stack = new Stack<>();
        Node currentNode = nodeCurrent;
        int solutionDepth = 0;

        while(!currentNode.puzzle.equals(temp)){
            stack.push(currentNode);
            currentNode = currentNode.parent;
            solutionDepth++;
        }

        while(!stack.isEmpty()){
            stack.pop();
        }

        System.out.printf("Search Cost: %d%n", searchCost);
        System.out.printf("Solution depth: %d", solutionDepth - 1);

        return solutionDepth;
    }

    private static boolean isEqual(Integer[] a, Integer[] b) {
        for(int i = 0; i < 9; i++){
            if(a[i] != b[i]){
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        Integer[] board = {1,8,7,3,0,2,6,4,5};
        aStar(board.clone(), 1);
        System.out.println();
        aStar(board.clone(), 2);
    }
}
