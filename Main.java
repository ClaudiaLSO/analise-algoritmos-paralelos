import java.io.FileWriter;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        int[] tamanhos = {1000, 5000, 10000}; // Comece com valores menores para testar
        int[] threadsConfigs = {2, 4, 8};
        int amostras = 5;

        FileWriter writer = new FileWriter("resultados.csv");
        writer.append("Algoritmo,Tamanho,Modo,Threads,TempoMS\n");

        SortAlgorithm[] algoritmos = {
            new SortingProvider.BubbleSort(),
            new SortingProvider.SelectionSort(),
            new SortingProvider.MergeSort(),
            new SortingProvider.QuickSort()
        };

        String[] nomes = {"BubbleSort", "SelectionSort", "MergeSort", "QuickSort"};

        for (int i = 0; i < algoritmos.length; i++) {
            for (int tamanho : tamanhos) {
                // TESTE SERIAL
                for (int a = 0; a < amostras; a++) {
                    int[] arr = gerarVetor(tamanho);
                    long start = System.currentTimeMillis();
                    algoritmos[i].sort(arr);
                    long end = System.currentTimeMillis();
                    writer.append(nomes[i] + "," + tamanho + ",Serial,1," + (end - start) + "\n");
                }

                // TESTE PARALELO
                for (int t : threadsConfigs) {
                    for (int a = 0; a < amostras; a++) {
                        int[] arr = gerarVetor(tamanho);
                        long start = System.currentTimeMillis();
                        algoritmos[i].parallelSort(arr, t);
                        long end = System.currentTimeMillis();
                        writer.append(nomes[i] + "," + tamanho + ",Paralelo," + t + "," + (end - start) + "\n");
                    }
                }
            }
            System.out.println(nomes[i] + " concluído...");
        }
        writer.flush();
        writer.close();
        System.out.println("Arquivo resultados.csv gerado com sucesso!");
    }

    private static int[] gerarVetor(int n) {
        Random rd = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rd.nextInt(10000);
        return arr;
    }
}