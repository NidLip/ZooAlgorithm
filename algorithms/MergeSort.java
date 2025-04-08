package algorithms;

import java.util.Arrays;
import java.util.Comparator;

public class MergeSort
{

    public static <T> void sort(T[] array, Comparator<T> comp)
    {
        if (array == null || array.length < 2) return;
        sort(array, 0, array.length - 1, comp);
    }

    private static <T> void sort(T[] array, int left, int right, Comparator<T> comp)
    {
        if (left < right)
        {
            int mid = left + (right - left) / 2;
            sort(array, left, mid, comp);
            sort(array, mid + 1, right, comp);
            merge(array, left, mid, right, comp);
        }
    }

    private static <T> void merge(T[] array, int left, int mid, int right, Comparator<T> comp)
    {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        T[] leftArray = Arrays.copyOfRange(array, left, mid + 1);
        T[] rightArray = Arrays.copyOfRange(array, mid + 1, right + 1);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2)
        {
            if (comp.compare(leftArray[i], rightArray[j]) <= 0)
            {
                array[k++] = leftArray[i++];
            } else
            {
                array[k++] = rightArray[j++];
            }
        }
        while (i < n1) array[k++] = leftArray[i++];
        while (j < n2) array[k++] = rightArray[j++];
    }
}