package com.cs3354.assign2.queue_sys;

import java.util.*;

public class Checker
{
    Queue<Integer> myQ = new LinkedList<Integer>();

    private static int people = 1, maxSize = 0, aSize = 0;
    private static LinkedList<Integer> processed;

    public void process(Checker checkA, Checker checkB, Checker checkC)
    {
        Random rand = new Random();
        Thread aThread = new Thread(),
            bThread = new Thread(),
            cThread = new Thread();

        do
        {
            if (!checkA.myQ.isEmpty())
            {
                aThread = new Thread("QUEUE A THREAD")
                {
                    public synchronized void run()
                    {
                        try
                        {
                            Thread.sleep(500 * (rand.nextInt(15) + 1));
        
                            if (checkA.myQ.peek() != null)
                                checkC.myQ.add(checkA.myQ.poll());

                            System.out.println("Queue A: " + checkA.toString());
                            System.out.println("Queue B: " + checkB.toString());
                            System.out.println("Queue C: " + checkC.toString());
                            System.out.println("-------------------------------");
                        } catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                if (aThread.isAlive())
                    try
                    {
						aThread.join();
                    }
                    catch (InterruptedException e)
                    {
						e.printStackTrace();
                    }
                    
                aThread.start();
            }

            if (!checkB.myQ.isEmpty())
            {
                bThread = new Thread("QUEUE B THREAD")
                {
                    public synchronized void run()
                    {
                        try
                        {
                            Thread.sleep(500 * (rand.nextInt(15) + 1));
        
                            if (checkB.myQ.peek() != null)
                                checkC.myQ.add(checkB.myQ.poll());

                            System.out.println("Queue A: " + checkA.toString());
                            System.out.println("Queue B: " + checkB.toString());
                            System.out.println("Queue C: " + checkC.toString());
                            System.out.println("-------------------------------");
                        } catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                if (bThread.isAlive())
                    try
                    {
						bThread.join();
                    }
                    catch (InterruptedException e)
                    {
						e.printStackTrace();
                    }
                    
                bThread.start();
            }

            if (!checkC.myQ.isEmpty())
            {
                cThread = new Thread("QUEUE C THREAD")
                {
                    public synchronized void run()
                    {
                        try
                        {
                            Thread.sleep(500 * (rand.nextInt(15) + 1));
        
                            if (checkC.myQ.peek() != null)
                                processed.add(checkC.myQ.poll());

                            System.out.println("Queue A: " + checkA.toString());
                            System.out.println("Queue B: " + checkB.toString());
                            System.out.println("Queue C: " + checkC.toString());
                            System.out.println("-------------------------------");
                        } catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                if (cThread.isAlive())
                    try
                    {
						cThread.join();
                    }
                    catch (InterruptedException e)
                    {
						e.printStackTrace();
                    }
                    
                cThread.start();
            }

            try
            {
                aThread.join();
                bThread.join();
                cThread.join();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        } while (!processed.contains(maxSize));
    }

    public void putInLine(int size, Checker check)
    {
        Random rand = new Random();

        Thread putInLine = new Thread("PUT-IN-LINE INNER THREAD")
        {
            public synchronized void run()
            {
                while (people <= size)
                {
                    try
                    {
                        Thread.sleep(500 * (rand.nextInt(10) + 1));
                        
                        check.myQ.add(people);
                        ++people;
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };

        try
        {
            putInLine.start();
            putInLine.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public String toString()
    {
        return myQ.toString();
    }

    public static void main(String[] args)
    {
        Scanner inputKey = new Scanner(System.in);
        Checker a = new Checker();
        Checker b = new Checker();
        Checker c = new Checker();
        processed = new LinkedList<Integer>();

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
                System.out.print("Enter size of Line A: ");
                aSize = inputKey.nextInt();

                if ((aSize < 1) || (aSize >= maxSize))
                {
                    System.err.println("Incorrect entry. Please input an integer between 1 and " + maxSize + ".");
                    inputKey.nextLine();
                    continue;
                }

                System.out.println("Line B set to " + (maxSize - aSize) + ".");

                break;
            }
            catch (InputMismatchException ime)
            {
                System.err.println("Incorrect entry. Please input only a positive integer.");
                inputKey.nextLine();
            }
        }

        System.out.println("Queue A: " + a.toString());
        System.out.println("Queue B: " + b.toString());
        System.out.println("Queue C: " + c.toString());
        System.out.println("-------------------------------");

        long start = System.currentTimeMillis();

        Thread putInLineA = new Thread("QUEUE A PUT-IN-LINE THREAD")
        {
            public synchronized void run()
            {
                a.putInLine(aSize, a);
            }
        };

        Thread putInLineB = new Thread("QUEUE B PUT-IN-LINE THREAD")
        {
            public synchronized void run()
            {
                b.putInLine(maxSize, b);
            }
        };

        Thread waitForC = new Thread("PROCESSED THREAD")
        {
            public synchronized void run()
            {
                c.process(a, b, c);
            }
        };

        putInLineA.start();
        putInLineB.start();
        waitForC.start();

        try
        {
            putInLineA.join();
            putInLineB.join();
            waitForC.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println("\nTime to process: " + ((end - start) / 500) + " \"minutes\".");

        inputKey.close();
    }
}