package Sorting;

public interface SortInterface {
    public int[] recursiveSort(int[] list) throws UnsortedException;

    public int[] iterativeSort(int[] list, int size) throws UnsortedException;

    public int getCount();

    public long getTime();
}
