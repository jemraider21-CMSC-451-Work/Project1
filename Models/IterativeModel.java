package Models;
public class IterativeModel {
    public int iterativeCount;
    public int iterativeIndex;
    public long iterativeTime;
    public int[] sortedIterativeArray;
    public int[] iterativeCountLog;
    public long[] iterativeTimeLog;

    public IterativeModel(int numRuns) {
        this.iterativeCount = 0;
        this.iterativeIndex = 0;
        this.iterativeTime = 0;
        this.iterativeCountLog = new int[numRuns];
        this.iterativeTimeLog = new long[numRuns];
    }
}
