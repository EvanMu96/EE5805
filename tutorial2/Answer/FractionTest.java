/**
 * In this exercise, implements the methods findMin and findSecondLargest.
 */

import java.util.ArrayList;

public class FractionTest 
{                      
    public static void main(String[] args)
    {
        ArrayList<Fraction> list = new ArrayList();
        
        list.add(new Fraction(-15, -18));
        list.add(new Fraction(14, -20));
        list.add(new Fraction(0, 4)); 
        
        list.add(list.get(0).add(list.get(1)));
        list.add(list.get(3).multiply(list.get(0)));
        list.add(list.get(0).divide(list.get(1)));
        list.add(list.get(4).subtract(list.get(3)));
        list.add(list.get(6).increment());
              
        System.out.println("list of Fraction: ");
        for (Fraction f : list)
            System.out.print(f + ", ");
        System.out.println();
        // Expected outputs:
        // 5/6, -7/10, 0/1, 2/15, 1/9, -25/21, -1/45, 44/45,        

        System.out.println("\n--------------------------------------");
        Fraction t1 = new Fraction(1, 9);
        testContain(list, t1);
        testContain(list, t1.increment());
        
        System.out.println("\n--------------------------------------");
        Fraction min = findMin(list);
        System.out.println("Smallest value in the list = " + min);        
        
        System.out.println("\n--------------------------------------");
        Fraction secondLargest = findSecondLargest(list);
        System.out.println("Second largest value in the list = " + secondLargest); 
    }
           
    static void testContain(ArrayList<Fraction> list, Fraction t)
    {
        if (list.contains(t))
            System.out.println("list contains " + t);
        else
            System.out.println("list does not contain " + t);
    }
    
    static Fraction findMin(ArrayList<Fraction> list)
    {
        // Precondition: list is not empty, i.e. list.length > 0
        // Use the compareTo() method to compare 2 Fraction objects
        
        Fraction min = list.get(0);
        for (int i = 1; i < list.size(); i++)
        {
            if (min.compareTo(list.get(i)) > 0)
                min = list.get(i);
        }
        return min;
    }
    
    static Fraction findSecondLargest(ArrayList<Fraction> list)
    {
        // Precondition: list.length >= 2
        
        Fraction max, secondMax;
        if (list.get(0).compareTo(list.get(1)) >= 0)
        {
            max = list.get(0);
            secondMax = list.get(1);
        }
        else
        {
            max = list.get(1);
            secondMax = list.get(0);
        }
        
        for (int i = 2; i < list.size(); i++)
        {
            if (list.get(i).compareTo(max) >= 0)
            {
                secondMax = max;
                max = list.get(i);
            }
            else if (list.get(i).compareTo(secondMax) > 0)
                secondMax = list.get(i);
        }
        return secondMax;
    }
}
