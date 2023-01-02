package src;

import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        boolean validInput = false;
        String prompt = "Select:\n[1] Generate Random Puzzle\n[2] Enter Puzzle\n[3] Exit\n", validChoices = "123";
        int choice = -1;
        
        while(!validInput){
            System.out.println(prompt);
            System.out.print("Enter choice: ");
            choice = getChoice();
            
            if(validChoices.contains(String.valueOf(choice)))
                validInput = true;
        }
        
        switch (choice) {
            case 1:
                randomPuzzle();
                break;
            case 2:
                userPuzzle();
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

    public static void randomPuzzle(){

    }

    public static void userPuzzle(){
        boolean puzzleCreated = false;

    }

    public static int getChoice(){
        int choice = -1;

        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Enter an integer.");
        }

        return choice;
    }

    public static String getPuzzle(){
        String puzzleString = "";
        int rows = 3;

        System.out.println("Enter puzzle: ");

        for(int i = 0; i < rows; i++){
            System.out.printf("Row %d: ", i + 1);
            puzzleString += sc.nextLine().replace(" ", "");
        }

        puzzleString.replace("\n", "");

        return puzzleString;
    }
}