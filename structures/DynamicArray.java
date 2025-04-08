package structures;

import java.util.Arrays;

public class DynamicArray<T> {
    private T[] array;
    private int size;

    @SuppressWarnings("unchecked")
    public DynamicArray() {
        array = (T[]) new Object[10];
        size = 0;
    }

    public void add(T element) {
        if (size == array.length) {
            array = Arrays.copyOf(array, array.length * 2);
        }
        array[size++] = element;
    }

    public boolean remove(T element) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(element)) {
                System.arraycopy(array, i + 1, array, i, size - i - 1);
                size--;
                array[size] = null;
                return true;
            }
        }
        return false;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return array[index];
    }

    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        array[index] = element;
    }

    public int getSize() {
        return size;
    }

    public T[] toArray() {
        return Arrays.copyOf(array, size);
    }

    public void display() {
        for (int i = 0; i < size; i++) {
            System.out.println(array[i]);
        }
    }
}
