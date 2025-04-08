package algorithms;

import java.util.Comparator;

public class QuickSort {

    public static <T> void sort(T[] array, Comparator<T> comp) {
        if (array == null || array.length < 2) return;
        sort(array, 0, array.length - 1, comp);
    }

    private static <T> void sort(T[] array, int low, int high, Comparator<T> comp) {
        if (low < high) {
            int pi = partition(array, low, high, comp);
            sort(array, low, pi - 1, comp);
            sort(array, pi + 1, high, comp);
        }
    }

    private static <T> int partition(T[] array, int low, int high, Comparator<T> comp) {
        T pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comp.compare(array[j], pivot) <= 0) {
                i++;
                T temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        T temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }
}