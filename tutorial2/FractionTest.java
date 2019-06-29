/**
 * In this exercise, implements the methods findMin and findSecondLargest.
 * Submission not required.
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class FractionTest 
{          
    public static double lg(double e){
        
    }
    public static void main(String[] args)
    {
        /*
        String input_str = "1 fish 2 fish red fish blue fish";
        String input_str2 = "China \nThe United State\n";
        System.out.print(input_str2);
        Scanner sc = new Scanner(input_str2);
        sc.useDelimiter("\\s*fish\\s*");
        System.out.println(sc.nextLine());
        System.out.println(sc.nextLine());
        System.out.println(1+2+"3");
        */
        /*
        boolean[][] a = new boolean[10][10];
        for (int i = 0; i < a.length; i++){
            for(int j=0; j<a[i].length; j++){
                if (i==j)
                    a[i][j]=true;
            }
            
        }
        System.out.println("initialized!");
        for (int i = 0; i < a.length; i++){
            for(int j=0; j<a[i].length; j++){
                if (a[i][j] == true)
                    System.out.print("*");
                else{
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
        */
        int[] a = new int[10];
        for (int i = 0; i < 10; i++)
            a[i] = 9-i;
        for (int i=0; i < 10; i++)
            a[i] = a[a[i]];
        for (int i=0; i < 10; i++)
            System.out.println(i);
        //System.out.println(sc.next());
        //System.out.println(sc.next());
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
        // Observe the outputs if method equals() in Fraction is not overriden.
        
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
        

        // Your codes
        
        Iterator<Fraction> i = list.iterator();
        Fraction min_f = i.next();
        while(i.hasNext()){
            Fraction temp = i.next();
            if (min_f.greaterThan(temp)){
                min_f = temp;
            }
        }

        return min_f;  // dummy return statement
    }
    
    static Fraction findSecondLargest(ArrayList<Fraction> list)
    {
        // Precondition: list.length >= 2
        // DO NOT modify list in your implementation.
        

        // Your codes
        Iterator<Fraction> i = list.iterator();
        Fraction largest1 = i.next();
        Fraction largest2 = new Fraction(-9999999,1);
        while(i.hasNext()){
            Fraction temp = i.next();
            if (temp.greaterThan(largest1)){
                largest2 = largest1;
                largest1 = temp;
            }else if((temp.greaterThan(largest2))&&(largest1.greaterThan(temp))){
                largest2 = temp;
            }
        }
        return largest2;  // dummy return statement
    }
}
