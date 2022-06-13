
import java.util.Random;
import Models.IterativeModel;
import Models.RecursiveModel;

public class BenchmarkSorts {

    private final static int NUMBER_OF_RUNS = 50;

    // private int[] sortedIterativeArray;
    private static IterativeModel it;
    private static RecursiveModel rec;
    private static MergeSort mergeSort = new MergeSort();

    public static void main(String[] args) {
        int[] sizes = { 50, 100, 500, 1000, 5000, 10000, 50000, 100000, 500000, 1000000 };
        for (int size : sizes) {
            BenchmarkSorts(size);
        }
    }

    private static void BenchmarkSorts(int size) {
        int[] array;
        for (int i = 0; i < NUMBER_OF_RUNS; i++) {
            array = new int[size];
            for (int j = 0; j < size; j++) {
                Random random = new Random();
                array[j] = random.nextInt(1000);
            }

            // Run the sort and produces output if an unsorted exception is found
            try {
                runSorts(array, size);
            } catch (UnsortedException e) {
                System.out.println(e.getMessage());
            }
        }
        displayReport(size);
    }

    private static void runSorts(int[] array, int numRuns) throws UnsortedException {

        // Runs iterative sort
        it = new IterativeModel(numRuns);
        it.sortedIterativeArray = mergeSort.iterativeSort(array);
        int returnCount = mergeSort.getCount();
        long returnTime = mergeSort.getTime();
        it.iterativeCount = it.iterativeCount + returnCount;
        it.iterativeTime = it.iterativeTime + returnTime;
        it.iterativeCountLog[it.iterativeIndex] = returnCount;
        it.iterativeTimeLog[it.iterativeIndex] = returnTime;
        it.iterativeIndex++;

        // Runs recursive sort
        rec = new RecursiveModel(numRuns);
        rec.sortedRecursiveArray = mergeSort.recursiveSort(array);
        returnCount = mergeSort.getCount();
        returnTime = mergeSort.getTime();
        rec.recursiveCount = rec.recursiveCount + returnCount;
        rec.recursiveTime = rec.recursiveTime + returnTime;
        rec.recursiveCountLog[rec.recursiveIndex] = rec.recursiveCount;
        rec.recursiveTimeLog[rec.recursiveIndex] = rec.recursiveTime;
        rec.recursiveIndex++;
    }

    private static void displayReport(int arraySize) {

        // Sets local variables
        double iterativeAverageCount = 0;
        double iterativeAverageTime = 0;
        double recursiveAverageCount = 0;
        double recursiveAverageTime = 0;
        double iterativeVarianceCount = 0;
        double iterativeVarianceTime = 0;
        double recursiveVarianceCount = 0;
        double recursiveVarianceTime = 0;
        double iterativeSDCount = 0;
        double iterativeSDTime = 0;
        double recursiveSDCount = 0;
        double recursiveSDTime = 0;

        // Calculates averages
        for (int i = 0; i < NUMBER_OF_RUNS; i++) {
            iterativeAverageCount += it.iterativeCountLog[i];
            iterativeAverageTime += it.iterativeTimeLog[i];
            recursiveAverageCount += rec.recursiveCountLog[i];
            recursiveAverageTime += rec.recursiveTimeLog[i];
        }

        iterativeAverageCount = iterativeAverageCount / arraySize;
        iterativeAverageTime = iterativeAverageTime / arraySize;
        recursiveAverageCount = recursiveAverageCount / arraySize;
        recursiveAverageTime = recursiveAverageTime / arraySize;

        // Calculates standard deviations
        for (int i = 0; i < NUMBER_OF_RUNS; i++) {
            iterativeVarianceCount += Math.pow(iterativeAverageCount - it.iterativeCountLog[i], 2);
            iterativeVarianceTime += Math.pow(iterativeAverageTime - it.iterativeTimeLog[i], 2);
            recursiveVarianceCount += Math.pow(recursiveAverageCount - rec.recursiveCountLog[i], 2);
            recursiveVarianceTime += Math.pow(recursiveAverageTime - rec.recursiveTimeLog[i], 2);
        }

        iterativeVarianceCount = iterativeVarianceCount / arraySize;
        iterativeVarianceTime = iterativeVarianceTime / arraySize;
        recursiveVarianceCount = recursiveVarianceCount / arraySize;
        recursiveVarianceTime = recursiveVarianceTime / arraySize;

        iterativeSDCount = Math.sqrt(iterativeVarianceCount);
        iterativeSDTime = Math.sqrt(iterativeVarianceTime);
        recursiveSDCount = Math.sqrt(recursiveVarianceCount);
        recursiveSDTime = Math.sqrt(recursiveVarianceTime);

        // Produces output
        System.out.println("Data Set Size (n): " + arraySize +
                "\n\tIterative Selection Sort Results: \t\t\t\t\tRecursive Selection Sort Results:" +
                "\n\tAverage Critical Operation Count: " + Math.round(iterativeAverageCount) +
                "\t\t\tAverage Critical Operation Count: " + Math.round(recursiveAverageCount) +
                "\n\tStandard Deviation of Count: " + Math.round(iterativeSDCount) +
                "\t\t\t\t\tStandard Deviation of Count: " + Math.round(recursiveSDCount) +
                "\n\tAverage Execution Time: " + Math.round(iterativeAverageTime) +
                "\t\t\t\t\t\tAverage Execution Time: " + Math.round(recursiveAverageTime) +
                "\n\tStandard Deviation of Time: " + Math.round(iterativeSDTime) +
                "\t\t\t\t\t\tStandard Deviation of Time: " + Math.round(recursiveSDTime));
    }
}