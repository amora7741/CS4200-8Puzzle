package src;

public class OutputData { //used to store the solution depth and search cost of a given puzzle solution for printing
    public int solutionDepth;
    public int searchCost;

    public OutputData(int solutionDepth, int searchCost){
        this.solutionDepth = solutionDepth;
        this.searchCost = searchCost;
    }
}
