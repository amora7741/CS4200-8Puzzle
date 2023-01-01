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

public class MisplacedTiles {
    private static int depth;
    private static int solutionSize;
    private static Integer[] temp = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
    private static Integer[] goalState = {0,1,2,3,4,5,6,7,8};
    private static Node emptyNode = new Node(null, temp, -1, -1);
    private int blankPosition = -1;

    public static void misplacedHeuristic(Integer[] board){
        Node startingNode = new Node(emptyNode, board, getMisplacedTileCount(board), 0);
        Node finalNode = null;
        solutionSize = 0;
        depth = 0;
        List<Node> exploredNodes = new ArrayList<>();
        boolean solutionReached = false;


        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingInt(a -> a.weight));
        frontier.add(startingNode);
        Node n;
        Queue<Node> children;

        while(!frontier.isEmpty()){
            n = frontier.poll();
            depth = n.depth;

            children = expandNode(n);

            while(!children.isEmpty()){
                Node child = children.poll();
                if(child.puzzle.equals(goalState)){
                    finalNode = child;
                    solutionReached = true;
                    break;
                }

                int index = containsBoard(child, frontier);

                if(index < frontier.size()){
                    Iterator<Node> i = frontier.iterator();
                    Node n2 = null;

                    while(index > 0){
                        n2 = i.next();
                        index--;
                    }

                    if(child.weight < n2.weight){
                        frontier.remove(n2);
                        frontier.add(child);
                        continue;
                    }
                    else
                        continue;
                }

                index = containsBoard(child, exploredNodes);
                Node n2 = null;

                if(index < exploredNodes.size()){
                    n2 = exploredNodes.get(index);

                    if(child.weight < n2.weight){
                        exploredNodes.remove(index);
                        frontier.add(child);
                        continue;
                    }
                    else
                        continue;
                }

                exploredNodes.add(n);

            }
            if(solutionReached)
                break;

            exploredNodes.add(n);
        }

        OutputData output = new OutputData(printSolution(finalNode), solutionSize);

    }


    public static int getMisplacedTileCount(Integer[] puzzle){
        int count = 0;

        for(int i = 0; i < puzzle.length; i++){
            if(puzzle[i] != i)
                count++;
        }

        return count;
    }

    private static Queue<Node> expandNode(Node parent){
        Queue<Node> output = new LinkedList<>();
        Node child;

        if(parent.isValidMove("u")){
            solutionSize++;
            child = generateNode("u", parent);

            if(!Arrays.equals(child.puzzle, parent.parent.puzzle))
                output.add(child);
        }

        if(parent.isValidMove("d")){
            solutionSize++;
            child = generateNode("d", parent);

            if(!Arrays.equals(child.puzzle, parent.parent.puzzle))
                output.add(child);
        }

        if(parent.isValidMove("l")){
            solutionSize++;
            child = generateNode("l", parent);

            if(!Arrays.equals(child.puzzle, parent.parent.puzzle))
                output.add(child);
        }

        if(parent.isValidMove("r")){
            solutionSize++;
            child = generateNode("r", parent);

            if(!Arrays.equals(child.puzzle, parent.parent.puzzle))
                output.add(child);
        }

        return output;
    }

    private static Node generateNode(String action, Node parent){
        Node node;
        Integer[] tempState = parent.generateState(action);

        node = new Node(parent, tempState, getMisplacedTileCount(tempState), depth + 1);

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
        int depth = 0;

        while(!currentNode.puzzle.equals(temp)){
            stack.push(currentNode);
            currentNode = currentNode.parent;
            depth++;
        }

        while(!stack.isEmpty()){
            stack.pop();
        }

        System.out.printf("Misplaced Tiles Heuristic Search Cost: %d", solutionSize);

        return depth;
    }

    private static boolean isEqual(Integer[] a, Integer[] b) {
        for(int i = 0; i < 9; i++){
            if(a[i] != b[i]){
                return false;
            }
        }
        return true;
    }
}
