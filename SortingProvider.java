import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class SortingProvider {

    // --- BUBBLE SORT ---
    public static class BubbleSort implements SortAlgorithm {
        public void sort(int[] arr) {
            int n = arr.length;
            for (int i = 0; i < n - 1; i++)
                for (int j = 0; j < n - i - 1; j++)
                    if (arr[j] > arr[j + 1]) {
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                    }
        }

        // Versão paralela simplificada (Odd-Even Sort)
        public void parallelSort(int[] arr, int threads) {
            int n = arr.length;
            boolean sorted = false;
            while (!sorted) {
                sorted = true;
                // Implementação simplificada para fins acadêmicos
                for (int i = 1; i < n - 1; i += 2) {
                    if (arr[i] > arr[i + 1]) {
                        int temp = arr[i]; arr[i] = arr[i + 1]; arr[i + 1] = temp;
                        sorted = false;
                    }
                }
                for (int i = 0; i < n - 1; i += 2) {
                    if (arr[i] > arr[i + 1]) {
                        int temp = arr[i]; arr[i] = arr[i + 1]; arr[i + 1] = temp;
                        sorted = false;
                    }
                }
            }
        }
    }

    // --- SELECTION SORT ---
    public static class SelectionSort implements SortAlgorithm {
        public void sort(int[] arr) {
            int n = arr.length;
            for (int i = 0; i < n - 1; i++) {
                int minIdx = i;
                for (int j = i + 1; j < n; j++)
                    if (arr[j] < arr[minIdx]) minIdx = j;
                int temp = arr[minIdx];
                arr[minIdx] = arr[i];
                arr[i] = temp;
            }
        }
        public void parallelSort(int[] arr, int threads) {
            sort(arr); // Selection é difícil de paralelizar eficientemente, mantemos a lógica base
        }
    }

    // --- MERGE SORT (RECURSIVO / FORK-JOIN) ---
    public static class MergeSort implements SortAlgorithm {
        public void sort(int[] arr) {
            sequentialMergeSort(arr, 0, arr.length - 1);
        }

        private void sequentialMergeSort(int[] arr, int l, int r) {
            if (l < r) {
                int m = l + (r - l) / 2;
                sequentialMergeSort(arr, l, m);
                sequentialMergeSort(arr, m + 1, r);
                merge(arr, l, m, r);
            }
        }

        public void parallelSort(int[] arr, int threads) {
            ForkJoinPool pool = new ForkJoinPool(threads);
            pool.invoke(new MergeTask(arr, 0, arr.length - 1));
        }

        static class MergeTask extends RecursiveAction {
            int[] arr; int l, r;
            MergeTask(int[] arr, int l, int r) { this.arr = arr; this.l = l; this.r = r; }
            protected void compute() {
                if (l < r) {
                    int m = l + (r - l) / 2;
                    invokeAll(new MergeTask(arr, l, m), new MergeTask(arr, m + 1, r));
                    new MergeSort().merge(arr, l, m, r);
                }
            }
        }

        void merge(int[] arr, int l, int m, int r) {
            int n1 = m - l + 1; int n2 = r - m;
            int L[] = new int[n1]; int R[] = new int[n2];
            System.arraycopy(arr, l, L, 0, n1);
            System.arraycopy(arr, m + 1, R, 0, n2);
            int i = 0, j = 0, k = l;
            while (i < n1 && j < n2) arr[k++] = (L[i] <= R[j]) ? L[i++] : R[j++];
            while (i < n1) arr[k++] = L[i++];
            while (j < n2) arr[k++] = R[j++];
        }
    }

    // --- QUICK SORT ---
    public static class QuickSort implements SortAlgorithm {
        public void sort(int[] arr) { sequentialQuickSort(arr, 0, arr.length - 1); }
        
        private void sequentialQuickSort(int[] arr, int low, int high) {
            if (low < high) {
                int pi = partition(arr, low, high);
                sequentialQuickSort(arr, low, pi - 1);
                sequentialQuickSort(arr, pi + 1, high);
            }
        }

        public void parallelSort(int[] arr, int threads) {
            ForkJoinPool pool = new ForkJoinPool(threads);
            pool.invoke(new QuickTask(arr, 0, arr.length - 1));
        }

        static class QuickTask extends RecursiveAction {
            int[] arr; int low, high;
            QuickTask(int[] arr, int low, int high) { this.arr = arr; this.low = low; this.high = high; }
            protected void compute() {
                if (low < high) {
                    int pi = new QuickSort().partition(arr, low, high);
                    invokeAll(new QuickTask(arr, low, pi - 1), new QuickTask(arr, pi + 1, high));
                }
            }
        }

        int partition(int[] arr, int low, int high) {
            int pivot = arr[high]; int i = (low - 1);
            for (int j = low; j < high; j++) {
                if (arr[j] < pivot) {
                    i++; int temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
                }
            }
            int temp = arr[i + 1]; arr[i + 1] = arr[high]; arr[high] = temp;
            return i + 1;
        }
    }
}