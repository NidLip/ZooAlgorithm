package structures;

import java.util.Comparator;

public class BinarySearchTree<T>
{
    private Node<T> root;
    private Comparator<T> comparator;

    public BinarySearchTree(Comparator<T> comparator)
    {
        this.comparator = comparator;
        root = null;
    }

    public void insert(T element)
    {
        root = insertRec(root, element);
    }

    private Node<T> insertRec(Node<T> root, T element)
    {
        if (root == null)
        {
            return new Node<>(element);
        }
        if (comparator.compare(element, root.value) < 0)
        {
            root.left = insertRec(root.left, element);
        } else if (comparator.compare(element, root.value) > 0)
        {
            root.right = insertRec(root.right, element);
        }
        return root;
    }

    public T search(T element)
    {
        return searchRec(root, element);
    }

    private T searchRec(Node<T> root, T element)
    {
        if (root == null) return null;
        if (comparator.compare(element, root.value) == 0)
        {
            return root.value;
        }
        return comparator.compare(element, root.value) < 0 ?
                searchRec(root.left, element) : searchRec(root.right, element);
    }

    public void inOrderTraversal()
    {
        inOrderRec(root);
    }

    private void inOrderRec(Node<T> root)
    {
        if (root != null)
        {
            inOrderRec(root.left);
            System.out.println(root.value);
            inOrderRec(root.right);
        }
    }

    private static class Node<T>
    {
        T value;
        Node<T> left, right;

        Node(T value)
        {
            this.value = value;
            left = right = null;
        }
    }
}
