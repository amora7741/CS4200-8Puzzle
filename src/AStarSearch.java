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

    public Node aStar(Integer[] board, String heuristic){
        //generate node based on which heuristic is chosen
        Node startingNode = heuristic.equals("misplaced") ? new Node(emptyNode, board, getMisplacedTileCount(board), 0) : new Node(emptyNode, board, manhattanHeuristic(board), 0);
        Node finalNode = null;
        List<Node> exploredNodes = new ArrayList<>();
        boolean solutionReached = false;
        solutionDepth = 0;
        searchCost = 0;

        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingInt(a -> a.weight));

        frontier.add(startingNode);
        Node q;
        Queue<Node> children;

        while (!frontier.isEmpty()) { //continue until the frontier is empty
            q = frontier.poll(); //get the first node in the frontier

            if(Arrays.equals(q.puzzle,goalState)){ //check if the current node is already in the goal state, exiting if so
                finalNode = q;
                solutionReached = true;
                break;
            }
            
            solutionDepth = q.depth;
            children = expandNode(q, heuristic); //get successors of the current node

            while(!children.isEmpty()){ //loop until there are no more successors of the current node
                Node child = children.poll();

                if(Arrays.equals(child.puzzle, goalState)){ //check if the child node is already in the goal state, exiting if so
                    finalNode = child;
                    solutionReached = true;
                    break;
                }

                int index = containsBoard(child, frontier); //get the index of the child node in the frontier

                if(index < frontier.size()){ 
                    Iterator<Node> iter = frontier.iterator();
                    Node item = null;

                    while(index > 0){
                        item = iter.next();
                        index--;
                    }

                    if(child.weight < item.weight){ //check if the cost of the child is less than the cost of the previous node, adding the child to the frontier if so
                        frontier.remove(item);
                        frontier.add(child);
                        continue;
                    }
                    else
                        continue;
                }

                index = containsBoard(child, exploredNodes); //get the index of the child node from the explored nodes list
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

        OutputData output = new OutputData(printSolution(finalNode, heuristic), searchCost); //create output data for the current puzzle
        return finalNode; //return the node which contains the goal state
    }
    

    public static int getMisplacedTileCount(Integer[] puzzle){ //find and return the amount of misplaced tiles in the given puzzle
        int count = 0;

        for(int i = 0; i < puzzle.length; i++){
            if(puzzle[i] != i) //check if index 1 != 1, 2 != 2 etc.
                count++;
        }

        return count;
    }

    public static int manhattanHeuristic(Integer[] puzzle){ //find and return the manhattan distance of the puzzle
        int totalDistance = 0;

        for(int i = 0; i < puzzle.length; i++){
            if(puzzle[i] == i || puzzle[i].equals(0)) //if the tile is in the correct position, go to the next tile
                continue;

            int rowIndex = puzzle[i] / 3; //get the row of the current tile
            int columnIndex = puzzle[i] % 3; //get the column of the current tile

            int goalRow = i / 3; //get the row/column where the tile value is supposed to be
            int goalColumn = i % 3;

            totalDistance += Math.abs(rowIndex - goalRow) + Math.abs(columnIndex - goalColumn); //get the distance between the goal row/column and actual row/column
        }

        return totalDistance;
    }

    private static Queue<Node> expandNode(Node parent, String heuristic){ //used to generate the successors of the current node
        Queue<Node> output = new LinkedList<>();
        Node child;

        if(parent.isValidMove("u")){ //check if the inputted move does not go out of bounds
            child = generateNode("u", parent, heuristic);
            searchCost++;
            
            if(!Arrays.equals(child.puzzle, parent.parent.puzzle)){ //add node to output if the puzzle state does not exist
                output.add(child);
            }
        }

        if(parent.isValidMove("d")){
            child = generateNode("d", parent, heuristic);
            searchCost++;
            
            if(!Arrays.equals(child.puzzle, parent.parent.puzzle)){
                output.add(child);
            }
        }

        if(parent.isValidMove("l")){
            child = generateNode("l", parent, heuristic);
            searchCost++;
            
            if(!Arrays.equals(child.puzzle, parent.parent.puzzle)){
                output.add(child);
            }
        }

        if(parent.isValidMove("r")){
            child = generateNode("r", parent, heuristic);
            searchCost++;
            
            if(!Arrays.equals(child.puzzle, parent.parent.puzzle)){
                output.add(child);
            }
        }

        return output; //return list of successors
    }

    private static Node generateNode(String action, Node parent, String heuristic){ //generate a new node based on given action
        Node node;
        Integer[] tempState = parent.generateState(action); //get state generated by action

        //generated node based on heuristic
        node = heuristic.equals("misplaced") ? new Node(parent, tempState, getMisplacedTileCount(tempState), solutionDepth + 1) : new Node(parent, tempState, manhattanHeuristic(tempState), solutionDepth + 1);

        return node;
    }

    private static int containsBoard(Node in, PriorityQueue<Node> frontier){ //check if two nodes contain the same puzzle state
        Iterator<Node> i = frontier.iterator();
        int index = 0;
        while(i.hasNext()){ //loop while there are more items in the queue
            index++;

            if(isEqual(in.puzzle, i.next().puzzle)) //exit if the two puzzles are equal
                break;
        }
        return index;
    }

    private static int containsBoard(Node in, List<Node> exploredNodes){ //check if two nodes contain the same puzzle state
        Iterator<Node> i = exploredNodes.iterator();
        int index = 0;
        while(i.hasNext()){ //loop while there are items in the frontier
            index++;

            if(isEqual(in.puzzle, i.next().puzzle)){ //if two puzzles are equal, exit and return index
                index--;
                break;
            }
        }
        return index;
    }

    private static int printSolution(Node nodeCurrent, String heuristic){ //used to print the search cost and depth of the current puzzle
        Stack<Node> stack = new Stack<>();
        Node currentNode = nodeCurrent;
        int solutionDepth = 0;
        String heurString = heuristic.equals("misplaced") ? "Misplaced Tiles" : "Manhattan Distance";
        int step = 0;

        while(!currentNode.puzzle.equals(temp)){ //loop until we reach the root node
            stack.push(currentNode);
            currentNode = currentNode.parent;
            solutionDepth++; //increase the solution depth
        }

        while(!stack.isEmpty()){
            if(heuristic.equals("manhattan")){
                if(step == 0)
                    System.out.println("\nInitial Puzzle\n-------------");
                else
                    System.out.printf("Step %d%n", step);

                System.out.println((stack.pop().toString())); //print each step of the solution
                System.out.println("-------------"); 
                step++;
            }
            else
                stack.pop();
        }

        System.out.printf("%n%s Search Cost: %d%n", heurString, searchCost);
        System.out.printf("%s Solution depth: %d", heurString, solutionDepth - 1);

        return solutionDepth;
    }

    public int getSolutionDepth(){
        return solutionDepth;
    }

    public int getSearchCost(){
        return searchCost;
    }

    private static boolean isEqual(Integer[] a, Integer[] b){ //used to check if two puzzles are equal
        for(int i = 0; i < 9; i++){
            if(a[i] != b[i]){ //if two states are not the same, not equal
                return false;
            }
        }
        return true;
    }
}
