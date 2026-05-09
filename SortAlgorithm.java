public interface SortAlgorithm {
    void sort(int[] array);
    void parallelSort(int[] array, int threads);
}