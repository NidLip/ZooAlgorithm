package search;

import java.util.Comparator;

public class SequentialSearch
{
    public static <T> int search(T[] array, T target, Comparator<T> comparator)
    {
        for (int i = 0; i < array.length; i++)
        {
            if (comparator.compare(array[i], target) == 0)
            {
                return i;
            }
        }
        return -1;
    }
}