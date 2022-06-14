import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import Models.ReportData;
import Models.ReportModel;
import Models.SortModel;

public class BenchmarkSorts {

    private final static int NUMBER_OF_RUNS = 50;

    // private int[] sortedIterativeArray;
    private static SortModel iterative;
    private static SortModel recursive;
    private static MergeSort mergeSort = new MergeSort();

    public static void main(String[] args) {
        int[] sizes = { 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000 };
        for (int size : sizes) {
            BenchmarkSorts(size);
        }
    }

    private static void BenchmarkSorts(int size) {
        int[] array;
        ReportData iterativeData = new ReportData(size, NUMBER_OF_RUNS);
        ReportData recursiveData = new ReportData(size, NUMBER_OF_RUNS);

        for (int i = 0; i < NUMBER_OF_RUNS; i++) {
            array = new int[size];
            for (int j = 0; j < size; j++) {
                Random random = new Random();
                array[j] = random.nextInt(1000);
            }

            // Run the sort and produces output if an unsorted exception is found
            try {
                ReportModel[] datas = runSorts(array, size);
                iterativeData.data[i] = datas[0];
                recursiveData.data[i] = datas[1];
            } catch (UnsortedException e) {
                System.out.println(e.getMessage());
            }
        }
        //displayReport(iterative, size, "Iterative");
        //displayReport(recursive, size, "Recursive");
        try {
            generateReport(iterativeData, "Reports/iterativeInput.txt");
            generateReport(recursiveData, "Reports/recursiveInput.txt");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static ReportModel[] runSorts(int[] array, int numRuns) throws UnsortedException {
        int[] itArray = array.clone();
        int[] recArray = array.clone();

        // Runs iterative sort
        iterative = new SortModel(numRuns);
        iterative = runSort(itArray, iterative, numRuns, true);
        ReportModel itData = new ReportModel(iterative.count, iterative.time);

        // Runs recursive sort
        recursive = new SortModel(numRuns);
        recursive = runSort(recArray, recursive, numRuns, false);
        ReportModel recData = new ReportModel(recursive.count, recursive.time);

        return new ReportModel[] { itData, recData };
    }

    private static SortModel runSort(int[] array, SortModel model, int numRuns, boolean isIterative)
            throws UnsortedException {
        model.sortedArray = (isIterative) ? mergeSort.iterativeSort(array) : mergeSort.recursiveSort(array);
        int returnCount = mergeSort.getCount();
        long returnTime = mergeSort.getTime();
        model.count = model.count + returnCount;
        model.time = model.time + returnTime;
        model.countLog[model.index] = returnCount;
        model.timeLog[model.index] = returnTime;
        model.index++;
        return model;
    }

    private static void generateReport(ReportData reportData, String fileName) throws IOException{
        String newLine = "";
        newLine += reportData.size +" ";
        for (ReportModel model : reportData.data) {
            newLine += model.toString() + " ";
        }

        FileWriter fileWriter = new FileWriter(fileName, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(newLine);
        bufferedWriter.newLine();
        bufferedWriter.close();
    }

    // private static void displayReport(SortModel model, int arraySize, String type) {
    //     // set local variables
    //     double averageCount = 0;
    //     double averageTime = 0;
    //     double varianceCount = 0;
    //     double varianceTime = 0;
    //     double sdCount = 0;
    //     double sdTime = 0;

    //     // Calculate averages
    //     for (int i = 0; i < NUMBER_OF_RUNS; i++) {
    //         averageCount += model.countLog[i];
    //         averageTime += model.timeLog[i];
    //     }
    //     averageCount = averageCount / arraySize;
    //     averageTime = averageTime / arraySize;

    //     // Calculate standard deviation
    //     for (int i = 0; i < NUMBER_OF_RUNS; i++) {
    //         varianceCount += Math.pow(averageCount - model.countLog[i], 2);
    //         varianceTime += Math.pow(averageTime - model.timeLog[i], 2);
    //     }

    //     varianceCount = varianceCount / arraySize;
    //     varianceTime = varianceTime / arraySize;

    //     sdCount = Math.sqrt(varianceCount);
    //     sdTime = Math.sqrt(varianceTime);

    //     System.out.println("Data Set Size (n): " + arraySize
    //             + "\n" + type + " Selection Sort Results:"
    //             + "\nAverage Critical Operation Count: " + Math.round(averageCount)
    //             + "\nStandard Deviation of Count: " + Math.round(sdCount)
    //             + "\nAverage Execution Time: " + Math.round(averageTime)
    //             + "\nStandard Deviation of Time: " + Math.round(sdTime)
    //             + "\n\n");
    // }
}