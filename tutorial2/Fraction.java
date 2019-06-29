/**
 * Representation of a Fraction object must fulfill the 3 requirements:
 * 1. Value of the denominator > 0
 * 2. The numerator and denominator are relatively prime, e.g. 2/6 is normalized to 1/3
 * 3. There is only one representation of the value Zero, i.e. 0/1
 * 
 * In this exercise, implements the constructor, methods add, subtract, multiply,
 * divide, compareTo, equals and increment.
 */
import java.lang.Math;
public class Fraction implements Comparable<Fraction>
{
    private final long numerator;   // value cannot be changed after initialization
    private final long denominator;

    public static final Fraction ONE = new Fraction(1, 1);
    public static final Fraction ZERO = new Fraction();
    
    //utility function to compute the greatest common divisor of 2 positive integers
    //precondition: n >= 0 and m > 0
    private static long gcd(long n, long m) 
    {       
        long r = n % m;

        while (r > 0)
        {
            n = m;
            m = r;
            r = n % m;
        }
        return m;
    }

    /**
     * default constructor
     */
    public Fraction()
    {
        numerator = 0;
        denominator = 1;
    }

    /**
     * @param n is the numerator
     * @param d is the denominator
     * Precondition: d != 0
     * Create a new instance of Fraction such that the numerator and denominator
     * are relatively prime, and denominator > 0.
     */
    public Fraction(long n, long d)
    {
        /* Your codes
        if (n==0){
            return new Fraction();
        }*/
        long lgest_cmn_divisor = gcd(Math.abs(n),Math.abs(d));    // dummy statements, replace them by your codes
        long temp_denominator = d/lgest_cmn_divisor;
        long temp_numerator = n/lgest_cmn_divisor;
        //numerator = n/lgest_cmn_divisor;
        if ( (n<0 && d<0)||(n>0&&d<0)){
            numerator = -temp_numerator;
            denominator = -temp_denominator;
        }else{
            numerator = temp_numerator;
            denominator = temp_denominator;
        }
        //String str = String.format("%d %d %d", lgest_cmn_divisor, n, d);
        //System.out.println(str);
    }
    
    /**
     *
     * @param other
     * @return a new instance of Fraction that represents this + other
     */
    public Fraction add(Fraction other)
    {
        // Your codes
        long n_temp = other.denominator*this.numerator + other.numerator*this.denominator;
        long d_temp = other.denominator*this.denominator;
        return new Fraction(n_temp, d_temp);  // dummy return statement
    }

    /**
     *
     * @param other
     * @return a new instance of Fraction that represents this - other
     */
    public Fraction subtract(Fraction other)
    {
        // Your codes
        long n_temp = other.denominator*numerator - other.numerator*denominator;
        long d_temp = other.denominator*denominator;
        return new Fraction(n_temp, d_temp);  // dummy return statement
    }

     /**
     *
     * @param other
     * @return a new instance of Fraction that represents this * other
     */
    public Fraction multiply(Fraction other)
    {
        // Your codes
        long n_temp = other.numerator*numerator;
        long d_temp = other.denominator*denominator;
        return new Fraction(n_temp, d_temp);  // dummy return statement
    }

     /**
     *
     * @param other
     * @return a new instance of Fraction that represents this / other
     */
    public Fraction divide(Fraction other)
    {
        // Your codes
        long n_temp = numerator* other.denominator;
        long d_temp = denominator* other.numerator;
        return new Fraction(n_temp, d_temp);   // dummy return statement
    }
    
    /**
     * 
     * @return a new instance of Fraction that represents this incremented by 1
     */
    public Fraction increment()
    {
        return this.add(ONE);
    }
    public boolean positive()
    {
        if (this.numerator > 0)
            return true;
        else 
            return false;
    }
    public boolean greaterThan(Fraction other){
        Fraction temp = this.subtract(other);
        if (temp.positive())
            return true;
        else
            return false;
    }
    /**
     * Compare the value of this with other
     * @param other
     * @return 0 if this is equal to other, 
     *  return a +ve value if this is greater than other
     *  return a -ve value if this is less than other
     */    
    @Override
    public int compareTo(Fraction other)
    {
        // Your codes
        Fraction inter;
        inter = subtract(other);
        if (inter.numerator > 0)
            return +1;
        else if (inter.numerator < 0)
            return -1;
        return 0;  // dummy return statement
    }
    
    /**
     * @param other
     * @return true if the implicit object this is equal (equivalent) to other
     */
    /*
    @Override
    public boolean equals(Object other)
    {
        // Your codes

    }
    */

     /**
     *
     * @return a String object for text-based I/O
     */
    @Override
    public String toString()
    {
        return numerator + "/" + denominator;
    }
}
