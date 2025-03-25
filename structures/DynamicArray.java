package structures;

import models.Animal;

import java.util.Arrays;

public class DynamicArray
{
    private Animal[] array;
    private int size;

    public DynamicArray()
    {
        array = new Animal[10];
        size = 0;
    }

    public void add(Animal animal)
    {
        if (size == array.length) {

            array = Arrays.copyOf(array, array.length * 2);
        }
        array[size++] = animal;
    }

    public boolean remove(int id)
    {
        for (int i = 0; i < size; i++)
        {
            if (array[i].getId() == id)
            {
                System.arraycopy(array, i + 1, array, i, size - i - 1);
                size--;
                return true;
            }
        }
        return false;
    }

    public Animal get(int index)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return array[index];
    }

    public int getSize()
    {
        return size;
    }

    public void display()
    {
        for (int i = 0; i < size; i++)
        {
            System.out.println(array[i]);
        }
    }
}
