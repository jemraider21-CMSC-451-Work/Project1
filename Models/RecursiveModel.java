package Models;
public class RecursiveModel {
    public int recursiveCount;
    public int recursiveIndex;
    public long recursiveTime;
    public int[] sortedRecursiveArray;
    public int[] recursiveCountLog;
    public long[] recursiveTimeLog;

    public RecursiveModel(int numRuns){
        this.recursiveCount = 0;
        this.recursiveIndex = 0;
        this.recursiveTime = 0;
        this.recursiveCountLog = new int[numRuns];
        this.recursiveTimeLog = new long[numRuns];
    }
}
