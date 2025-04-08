package structures;

import models.Animal;

public class BinarySearchTree
{
    private class Node
    {
        Animal animal;
        Node left, right;

        Node(Animal animal)
        {
            this.animal = animal;
            left = right = null;
        }
    }

    private Node root;

    public BinarySearchTree()
    {
        root = null;
    }

    public void insert(Animal animal)
    {
        root = insertRec(root, animal);
    }

    private Node insertRec(Node root, Animal animal)
    {
        if (root == null)
        {
            return new Node(animal);
        }

        if (animal.getName().compareToIgnoreCase(root.animal.getName()) < 0)
        {
            root.left = insertRec(root.left, animal);
        }
        else if (animal.getName().compareToIgnoreCase(root.animal.getName()) > 0)
        {
            root.right = insertRec(root.right, animal);
        }

        return root;
    }

    public Animal search(String name)
    {
        return searchRec(root, name);
    }

    private Animal searchRec(Node root, String name)
    {
        if (root == null)
        {
            return null;
        }
        if (name.equalsIgnoreCase(root.animal.getName()))
        {
            return root.animal;
        }
        return name.compareToIgnoreCase(root.animal.getName()) < 0 ?
                searchRec(root.left, name) : searchRec(root.right, name);
    }

    public void inOrderTraversal()
    {
        inOrderRec(root);
    }

    private void inOrderRec(Node root)
    {
        if (root != null)
        {
            inOrderRec(root.left);
            System.out.println(root.animal);
            inOrderRec(root.right);
        }
    }
}
