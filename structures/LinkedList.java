package structures;

import models.Animal;

public class LinkedList
{
    private Node head;

    public LinkedList()
    {
        this.head = null;
    }

    public void add(Animal animal)
    {
        Node newNode = new Node(animal);
        if (head == null)
        {
            head = newNode;
        } else
        {
            Node current = head;
            while (current.next != null)
            {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public boolean remove(int id)
    {
        if (head == null) return false;
        if (head.animal.getId() == id)
        {
            head = head.next;
            return true;
        }
        Node current = head;
        while (current.next != null)
        {
            if (current.next.animal.getId() == id)
            {
                current.next = current.next.next;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public Animal sequentialSearch(int id)
    {
        Node current = head;
        while (current != null)
        {
            if (current.animal.getId() == id)
            {
                return current.animal;
            }
            current = current.next;
        }
        return null;
    }

    public void display()
    {
        Node current = head;
        while (current != null)
        {
            System.out.println(current.animal);
            current = current.next;
        }
    }

    public Animal[] toArray()
    {
        int size = 0;
        Node temp = head;
        while (temp != null)
        {
            size++;
            temp = temp.next;
        }
        Animal[] arr = new Animal[size];
        Node current = head;
        int i = 0;
        while (current != null)
        {
            arr[i++] = current.animal;
            current = current.next;
        }
        return arr;
    }

    public void fromArray(Animal[] arr)
    {
        head = null;
        for (Animal a : arr)
        {
            add(a);
        }
    }

    private class Node
    {
        Animal animal;
        Node next;

        Node(Animal animal)
        {
            this.animal = animal;
            this.next = null;
        }
    }
}