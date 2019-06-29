/**
 * Representation of a Fraction object must fulfill the 3 requirements:
 * 1. Value of the denominator > 0
 * 2. The numerator and denominator are relatively prime, e.g. 2/6 is normalized to 1/3
 * 3. There is only one representation of the value Zero, i.e. 0/1
 * 
 * In this exercise, implements the constructor, methods add, subtract, multiply,
 * divide, compareTo, equals and increment.
 */
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
        if (n == 0)
        {
            numerator = 0;
            denominator = 1;
            return;
        }
        
        //assert: n != 0 and d != 0
        if (d < 0)
        {
            d *= -1;
            n *= -1;
        }

        long g;
        if (n < 0)
            g = gcd(-n, d);  //n >= 0 required by the gcd()
        else
            g = gcd(n, d); 

        numerator = n / g;
        denominator = d / g;
    }
    
    /**
     *
     * @param other
     * @return a new instance of Fraction that represents this + other
     */
    public Fraction add(Fraction other)
    {
        long n = this.numerator * other.denominator + other.numerator * this.denominator;
        long d = this.denominator * other.denominator;

        return new Fraction(n, d);
    }

    /**
     *
     * @param other
     * @return a new instance of Fraction that represents this - other
     */
    public Fraction subtract(Fraction other)
    {
        long n = this.numerator * other.denominator - other.numerator * this.denominator;
        long d = this.denominator * other.denominator;

        return new Fraction(n, d);
    }

     /**
     *
     * @param other
     * @return a new instance of Fraction that represents this * other
     */
    public Fraction multiply(Fraction other)
    {
        long n = this.numerator * other.numerator;
        long d = this.denominator * other.denominator;

        return new Fraction(n, d);
    }

     /**
     *
     * @param other
     * @return a new instance of Fraction that represents this / other
     */
    public Fraction divide(Fraction other)
    {
        long n = this.numerator * other.denominator;
        long d = this.denominator * other.numerator;

        return new Fraction(n, d);
    }
    
    /**
     * 
     * @return a new instance of Fraction that represents this incremented by 1
     */
    public Fraction increment()
    {
        return this.add(ONE);
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
        long ad = this.numerator * other.denominator;
        long bc = this.denominator * other.numerator;

        if (ad == bc)
            return 0;
        else if (ad < bc)
            return -1;
        else
            return 1;
    }
    
    /**
     * @param other
     * @return true if the implicit object this is equal (equivalent) to other
     */
    @Override
    public boolean equals(Object other)
    {
        if (other instanceof Fraction)
        {
            Fraction f = (Fraction)other;        
            return numerator == f.numerator && denominator == f.denominator;
        }
        else
            return false;
    }
    
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
