package com.cs3354.assign2.queue_sys;

import java.util.*;

public class Checker
{
    Queue<Integer> myQ = new LinkedList<Integer>();

    public String toString()
    {
        return myQ.toString();
    }

    public static void main(String[] args)
    {
        int maxSize = 0, aSize = 0, bSize = 0, count = 1;
        Scanner inputKey = new Scanner(System.in);
        Checker a = new Checker();
        Checker b = new Checker();
        Checker c = new Checker();
        Thread aT = new Thread();
        Thread bT = new Thread();
        Thread cT = new Thread();

        System.out.println("This program waits a random interval of tenth-of-a-seconds");
        System.out.println("(between 1/10th of a second and 15/10ths of a second)");
        System.out.println("to simulate an airport line process time in \"minutes\".\n");

        while (true)
        {
            try
            {
                System.out.print("Enter a number of people: ");
                maxSize = inputKey.nextInt();

                if (maxSize <= 4)
                {
                    System.err.println("Incorrect entry. Please enter a positive integer greater than 4.");
                    inputKey.nextLine();
                    continue;
                }

                inputKey.nextLine();
                System.out.print("Enter number of people in Line A: ");
                aSize = inputKey.nextInt();

                if ((aSize < 1) || (aSize >= maxSize))
                {
                    System.err.println("Incorrect entry. Please input an integer between 1 and " + maxSize + ".");
                    inputKey.nextLine();
                    continue;
                }

                bSize = maxSize - aSize;

                System.out.println("Line B set to " + bSize + ".");

                break;
            }
            catch (InputMismatchException ime)
            {
                System.err.println("Incorrect entry. Please input only a positive integer.");
                inputKey.nextLine();
            }
        }

        while (count <= aSize)
        {
            a.myQ.add(count);
            ++count;
        }

        while (count <= maxSize)
        {
            b.myQ.add(count);
            ++count;
        }

        long start = System.currentTimeMillis();

        System.out.println("Queue A: " + a.toString());
        System.out.println("Queue B: " + b.toString());
        System.out.println("Queue C: " + c.toString());
        System.out.println("-------------------------------");

        do
        {
            if (!a.myQ.isEmpty())
            {
                aT = new Thread("QUEUE A THREAD")
                {
                    public void run()
                    {
                        Random rand = new Random();
                        synchronized (this)
                        {
                            try
                            {
                                Thread.sleep(500 * (rand.nextInt(15) + 1));
            
                                if (a.myQ.peek() != null)
                                    c.myQ.add(a.myQ.poll());

                                System.out.println("Queue A: " + a.toString());
                                System.out.println("Queue B: " + b.toString());
                                System.out.println("Queue C: " + c.toString());
                                System.out.println("-------------------------------");
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                };

                aT.start();
            }

            if (!b.myQ.isEmpty())
            {
                bT = new Thread("QUEUE B THREAD")
                {
                    public void run()
                    {
                        Random rand = new Random();
                        synchronized (this)
                        {
                            try
                            {
                                Thread.sleep(500 * (rand.nextInt(15) + 1));
            
                                if (b.myQ.peek() != null)
                                    c.myQ.add(b.myQ.poll());

                                System.out.println("Queue A: " + a.toString());
                                System.out.println("Queue B: " + b.toString());
                                System.out.println("Queue C: " + c.toString());
                                System.out.println("-------------------------------");
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                };

                bT.start();
            }

            if (!c.myQ.isEmpty())
            {
                cT = new Thread("QUEUE C THREAD")
                {
                    public void run()
                    {
                        Random rand = new Random();
                        synchronized (this)
                        {
                            try
                            {
                                Thread.sleep(500 * (rand.nextInt(15) + 1));
            
                                if (c.myQ.peek() != null)
                                    c.myQ.poll();

                                System.out.println("Queue A: " + a.toString());
                                System.out.println("Queue B: " + b.toString());
                                System.out.println("Queue C: " + c.toString());
                                System.out.println("-------------------------------");
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                };

                cT.start();
            }

            try
            {
                aT.join();
                bT.join();
                cT.join();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        } while (!a.myQ.isEmpty() || !b.myQ.isEmpty() || !c.myQ.isEmpty());

        long end = System.currentTimeMillis();

        System.out.println("\nTime to process: " + ((end - start) / 50) + " \"minutes\".");

        inputKey.close();
    }
}