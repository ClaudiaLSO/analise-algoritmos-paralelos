# [RELATÓRIO TÉCNICO] ANÁLISE DE DESEMPENHO DE ALGORITMOS DE ORDENAÇÃO

**Autores:** Claudia Oliveira
**Disciplina:** Computação Paralela e Concorrente  
**Instituição:** UNIFOR – Universidade de Fortaleza  
**Data:** Maio de 2026

---

## 1. RESUMO

Este trabalho apresenta uma investigação sobre o desempenho dos algoritmos de ordenação Bubble Sort, Selection Sort, Merge Sort e Quick Sort em ambientes seriais e paralelos. Utilizando a linguagem Java e o framework Fork/Join, os algoritmos foram testados sob diferentes cargas de dados e configurações de núcleos (2, 4 e 8 threads). 

Os resultados foram analisados estatisticamente em um ambiente Jupyter Notebook, revelando o impacto do *overhead* de threads em algoritmos eficientes e o ganho de velocidade (*speedup*) em algoritmos de alta complexidade computacional.

**Palavras-chave:** Computação Paralela. Java Fork/Join. Speedup. Ordenação.

---

## 2. INTRODUÇÃO

A computação paralela é essencial para otimizar o processamento em hardware moderno. Este projeto compara o comportamento de algoritmos $O(n^2)$ e $O(n \log n)$, avaliando em quais cenários a divisão de tarefas entre múltiplos núcleos de processamento compensa o custo de gerenciamento do sistema operacional e da JVM.

---

## 3. METODOLOGIA

### 3.1. Hardware e Ambiente de Teste
* **Processador:** Intel Core i5-8265U (4 núcleos físicos / 8 threads lógicos).
* **Memória:** 12GB RAM.
* **SO:** Windows 11.
* **Linguagem:** Java 21 (Framework Fork/Join).
* **Análise de Dados:** Python 3.x no Jupyter Notebook (Pandas e Plotly).

---

## 4. IMPLEMENTAÇÃO TÉCNICA

O projeto está estruturado nos seguintes arquivos:
* **`SortAlgorithm.java`**: Interface base.
* **`SortingProvider.java`**: Contém a lógica de ordenação.
* **`Main.java`**: Controlador do framework de testes e exportação de dados.
* **`analise_completa.ipynb`**: Notebook responsável pelo tratamento de dados e geração dos gráficos.

---

## 5. RESULTADOS E DISCUSSÃO

### 5.1. Comparativo de Tempo
Conforme os dados coletados, algoritmos como o **Bubble Sort** apresentaram os melhores resultados com paralelismo, reduzindo o tempo de execução médio de 116ms para cerca de 54ms em vetores de 10.000 posições.

### 5.2. Análise de Speedup
O gráfico de *Speedup* gerado no Jupyter confirmou a **Lei de Amdahl**. O ganho de desempenho não foi linear, estabilizando-se após a utilização de 4 threads, que corresponde ao número de núcleos físicos do processador utilizado.

---

## 6. CONCLUSÃO

O trabalho validou que o paralelismo é uma ferramenta poderosa, mas deve ser aplicada criteriosamente. Para problemas "pequenos" ou algoritmos muito eficientes, o custo de coordenação das threads pode degradar a performance.

---

## 7. REFERÊNCIAS

ASSOCIAÇÃO BRASILEIRA DE NORMAS TÉCNICAS. **NBR 6023**: informação e documentação: referências. Rio de Janeiro, 2018.  
CORMEN, T. H. et al. **Algoritmos: teoria e prática**. 3. ed. Rio de Janeiro: Elsevier, 2012.
