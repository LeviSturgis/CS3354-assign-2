package com.cs3354.assign2.queue_sys;

import java.util.*;

public class Queue
{
    public int size;
    Node front, rear;

    public Queue()
    {
        front = null;
        rear = null;
        size = 0;
    }

    public boolean isEmpty()
    {
        return front == null;
    }

    public int getSize()
    {
        return size;
    }

    public void enqueue(int d)
    {
        Node nptr = new Node(d, null);

        if (rear == null)
        {
            front = nptr;
            rear = nptr;
        }
        else
        {
            rear.setLink(nptr);
            rear = rear.getLink();
        }

        size++;
    }

    public int dequeue()
    {
        if (isEmpty())
            throw new NoSuchElementException("Underflow Exception; Queue empty!");

        Node ptr = front;
        front = ptr.getLink();

        if (front == null)
            rear = null;

        size--;

        return ptr.getData();
    }

    public int peek()
    {
        if (isEmpty())
            throw new NoSuchElementException("Underflow Exception; Queue empty!");

        return front.getData();
    }

    public void display()
    {
        System.out.print("\nQueue ");

        if (size == 0)
        {
            System.out.print("is empty!\n");
            return;
        }

        Node ptr = front;

        while (ptr != rear.getLink())
        {
            System.out.print(ptr.getData() + " ");
            ptr = ptr.getLink();
        }

        System.out.println();
    }

    private class Node
    {
        int data;
        Node link;
    
        public Node()
        {
            link = null;
            data = 0;
        }
    
        public Node(int d, Node n)
        {
            data = d;
            link = n;
        }
    
        public void setLink(Node n)
        {
            link = n;
        }
    
        public void setData(int d)
        {
            data = d;
        }
    
        public Node getLink()
        {
            return link;
        }
    
        public int getData()
        {
            return data;
        }
    }
}