package src;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static AStarSearch search = new AStarSearch();
    
    public static void main(String[] args){
        boolean validInput = false;
        String prompt = "Select:\n[1] Generate Random Puzzle\n[2] Enter Puzzle\n[3] Exit\n", validChoices = "123";
        int choice = -1;
        
        while(!validInput){
            System.out.println(prompt);
            System.out.print("Enter choice: ");
            choice = getChoice();
            
            if(validChoices.contains(String.valueOf(choice))) //check if input is 1/2/3
                validInput = true;
        }

        Main test = new Main();
        
        switch (choice) { //user enter puzzle or generate random puzzle based on user choice
            case 1:
                test.randomPuzzle();
                break;
            case 2:
                test.userPuzzle();
                break;
            case 3:
                System.out.println("Goodbye!");
                sc.close();
                System.exit(0);
                break;
            default:
                break;
        }
    }

    /**
     * 
     */
    public void randomPuzzle(){
        Map<Integer, ArrayList<OutputData>> completeData = new TreeMap<>(); //treemap for storing data of multiple cases
        int testCases = 0;

        do {
            System.out.print("How many random puzzles would you like to solve? ");
            testCases = getChoice(); //get amount of random puzzles to generate
        } while (testCases <= 0);

        for(int i = 0; i < testCases; i++){
            Puzzle randomPuzzle = new Puzzle(); //generate random puzzle
            OutputData solution = solve(randomPuzzle); //pass random puzzle into solve method to get corresponding data

            if(!completeData.containsKey(solution.depth)) //make new entry in data set if depth of random puzzle does not exist in the set
                completeData.put(solution.depth, new ArrayList<>());

            completeData.get(solution.depth).add(solution); //add data to the specified depth
        }
        
        System.out.printf("Depth | Total Cases | Manhattan Search Cost | Manhattan Elapsed Time | Misplaced Search Cost | Misplaced Elapsed Time");

        completeData.entrySet().stream().forEach((entry) -> {
            int h1Average = 0, h1AvgTime = 0, h2Average = 0, h2AvgTime = 0, total = entry.getValue().size();

            for(int i = 0; i < entry.getValue().size(); i++){
                OutputData data = entry.getValue().get(i);
                h1Average += data.searchCostH1;
                h1AvgTime += data.totalTimeH1;
                h2Average += data.searchCostH2;
                h2AvgTime += data.totalTimeH2;
            }
            System.out.println();

            System.out.printf("%3d %10d %20d %20d ms %25d %20d ms", entry.getKey() + 1, total, (h1Average / total), (h1AvgTime/total), (h2Average/total), (h2AvgTime/total));
        });

    }

    public void userPuzzle(){
        boolean puzzleCreated = false;
        String puzzleString;
        Integer[] temp = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
        Puzzle userPuzzle = new Puzzle(temp);

        while(!puzzleCreated){ //loop until the puzzle is successfully created
            puzzleString = getPuzzle(); //get input puzzle string from the user

            puzzleCreated = userPuzzle.createPuzzle(puzzleString); //attempt to create a puzzle with the given string, looping again if unsuccessful
        }

        search.aStar(userPuzzle.puzzle, "manhattan"); //solve puzzle using both heuristics
        search.aStar(userPuzzle.puzzle, "misplaced");
        
    }

    public static int getChoice(){ //get and return user choice, printing an error message if anything other than an integer was entered
        int choice = -1;

        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Enter an integer.");
        }

        return choice;
    }

    public static String getPuzzle(){ //get puzzle string from the user from standard input
        String puzzleString = "";
        int rows = 3;

        System.out.println("Enter puzzle: ");

        for(int i = 0; i < rows; i++){
            System.out.printf("Row %d: ", i + 1);
            puzzleString += sc.nextLine().replace(" ", ""); //make the string a continuous string of numbers, no spaces
        }

        puzzleString.replace("\n", "");

        return puzzleString;
    }

    private OutputData solve(Puzzle puzzleState){
        long start = System.currentTimeMillis(); //get starting time in milliseconds
        Node goalNode1 = search.aStar(puzzleState.puzzle.clone(), "manhattan"); //solve the puzzle using the manhattan heuristic
        long end1 = System.currentTimeMillis() - start; //get elapsed time in milliseconds
        int searchCost1 = search.getSearchCost(); //get search cost of this heuristic
        System.out.println("\n------------------H1 DONE-------------------");

        long start2 = System.currentTimeMillis();
        Node goalNode2 = search.aStar(puzzleState.puzzle.clone(), "misplaced"); //solve using the misplaced tiles heuristic
        long end2 = System.currentTimeMillis() - start2;
        int searchCost2 = search.getSearchCost();
        System.out.println("\n------------------H2 DONE-------------------");

        System.out.printf("%nSolved Using Manhattan Distance Heuristic%nSolution Depth: %d%nSearch Cost: %d%nTotal Time: %d ms%n", search.getSolutionDepth() + 1, searchCost1, end1);
        System.out.printf("%nSolved Using Misplaced Tiles Heuristic%nSolution Depth: %d%nSearch Cost: %d%nTotal Time: %d ms%n%n", search.getSolutionDepth() + 1, searchCost2, end2);

        return new OutputData(search.getSolutionDepth(), searchCost1, end1, searchCost2, end2);
    }

    private class OutputData {
        public int depth;
        public int searchCostH1;
        public long totalTimeH1;
        public int searchCostH2;
        public long totalTimeH2;

        public OutputData(int d, int sCostH1, long tTimeH1, int sCostH2, long tTimeH2){
            depth = d;
            searchCostH1 = sCostH1;
            totalTimeH1 = tTimeH1;
            searchCostH2 = sCostH2;
            totalTimeH2 = tTimeH2;
        }   
    }
}