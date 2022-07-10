package Sorting;

import java.util.Arrays;

public class MergeSort implements SortInterface {

    int count = 0;
    long timeStart = 0;
    long timeEnd = 0;

    public MergeSort() {
        this.count = 0;
    }

    // Iterative approach from GeeksForGeeks
    // https://www.geeksforgeeks.org/iterative-merge-sort/
    public int[] iterativeSort(int arr[], int n) throws UnsortedException {
        timeStart = System.nanoTime();
        // For current size of subarrays to
        // be merged curr_size varies from
        // 1 to n/2
        int curr_size;

        // For picking starting index of
        // left subarray to be merged
        int left_start;

        // Merge subarrays in bottom up
        // manner. First merge subarrays
        // of size 1 to create sorted
        // subarrays of size 2, then merge
        // subarrays of size 2 to create
        // sorted subarrays of size 4, and
        // so on.
        for (curr_size = 1; curr_size <= n - 1; curr_size = 2 * curr_size) {

            // Pick starting point of different
            // subarrays of current size
            for (left_start = 0; left_start < n - 1; left_start += 2 * curr_size) {
                // Find ending point of left
                // subarray. mid+1 is starting
                // point of right
                int mid = Math.min(left_start + curr_size - 1, n - 1);

                int right_end = Math.min(left_start + 2 * curr_size - 1, n - 1);

                // Merge Subarrays arr[left_start...mid]
                // & arr[mid+1...right_end]
                merge(arr, left_start, mid, right_end);
            }
        }
        timeEnd = System.nanoTime();
        checkSortedArray(arr);
        return arr;
    }

    private int[] merge(int arr[], int l, int m, int r) {
        int i, j, k;
        int n1 = m - l + 1;
        int n2 = r - m;

        /* create temp arrays */
        int L[] = new int[n1];
        int R[] = new int[n2];

        /*
         * Copy data to temp arrays L[]
         * and R[]
         */
        for (i = 0; i < n1; i++)
            L[i] = arr[l + i];
        for (j = 0; j < n2; j++)
            R[j] = arr[m + 1 + j];

        /*
         * Merge the temp arrays back into
         * arr[l..r]
         */
        i = 0;
        j = 0;
        k = l;
        while (i < n1 && j < n2) {
            count++;
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
                count++;
            } else {
                arr[k] = R[j];
                j++;
                count++;
            }
            k++;
        }

        /*
         * Copy the remaining elements of
         * L[], if there are any
         */
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
            count++;
        }

        /*
         * Copy the remaining elements of
         * R[], if there are any
         */
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
            count++;
        }
        return arr;
    }

    // Recursive approach from GeeksForDeeks
    // https://www.geeksforgeeks.org/merge-sort/
    public int[] recursiveSort(int[] array) throws UnsortedException {
        timeStart = System.nanoTime();

        // array = recursiveSort(array, array.length);

        if (array.length > 1) {
            int middle = array.length / 2;

            // Split left part
            int[] left = new int[middle];
            for (int i = 0; i < middle; i++) {
                left[i] = array[i];
            }

            // Split right part
            int[] right = new int[array.length - middle];
            for (int i = middle; i < array.length; i++) {
                right[i - middle] = array[i];
            }

            int[] left2 = recursiveSort(left);
            int[] right2 = recursiveSort(right);

            array = recursiveMerge(array, left2, right2, middle, array.length - middle);
        }

        timeEnd = System.nanoTime();
        checkSortedArray(array);

        return array;
    }

    // Based off of post from Baeldung
    // https://www.baeldung.com/java-merge-sort
    private int[] recursiveMerge(int[] array, int[] leftHalf, int[] rightHalf, int left, int right) {
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < left && j < right) {
            count++;
            if (leftHalf[i] <= rightHalf[j]) {
                array[k++] = leftHalf[i++];
                count++;
            } else {
                array[k++] = rightHalf[j++];
                count++;
            }
        }

        while (i < left) {
            array[k++] = leftHalf[i++];
            count++;
        }

        while (j < right) {
            array[k++] = rightHalf[j++];
            count++;
        }
        return array;
    }

    public int getCount() {
        int result = count;
        count = 0;
        return result;
    }

    public long getTime() {
        long time = timeEnd - timeStart;
        timeEnd = 0;
        timeStart = 0;
        return time;
    }

    private void checkSortedArray(int[] list) throws UnsortedException {
        for (int i = 0; i < list.length - 1; i++) {
            if (list[i] > list[i + 1]) {
                for (int j = 0; i < list.length - 1; j++) {
                    System.out.println(" " + list[j]);
                }
                throw new UnsortedException("The array was not sorted correctly: \n" +
                        list[i] + " at index " + i + " and " + list[i + 1] + " at index " + (i + 1));
            }
        }
    }

}