// Student name:
// Student ID  :

/*
    Run-length encoding (RLE) is a popular encoding method to compress a stream 
    of symbols. We shall consider the simple case for a stream of 0 and 1. 
    To avoid confusion, we use the letter O to represent 0 and letter I to  
    represent 1 in the encoded data stream. 

    In RLE, a run (consecutive sequence) of the same symbol is represented by a 
    count-symbol pair.

    Example:
    Original data stream: 000000101111101000000000010
    Simple RLE stream:    6O1I1O5I1O1I10O1I1O

    A single character of 1 or 0 is quite common in a data stream. 
    One can see that the simple RLE is not very efficient in encoding single 
    instances of 0 or 1. A revised RLE encoding is to encode runs of 2 or more 
    characters only. The start of a run is represented by 2 consecutive character 
    of the given symbol.

    Original data stream: 000000101111101000000000010
    Revised RLE stream:   OO6IOII5OIOO10IO

    Your task is to implement the compress() and expand() methods in 
    the class RunLengthCode using the revised RLE. 

    In this exercise, you may make use of the class StringBuffer.
    StringBuffer object is mutable. It is more convenient and efficient to
    use a StringBuffer object to store the intermediate result of a computation.

    You can use the append() method to append a char, a string, or an integer 
    to a StringBuffer object.

    You can use the toString() method to produce a String object from a 
    StringBuffer object.

    You may need to use the following methods of the String class
       int length()
       String substring(int, int) 
       char charAt(int)

    To check if a char is a digit, you can use the static method
    Character.isDigit(char)

    To convert a string of digits to an integer value, you can use the static 
    method Integer.parseInt(String)
*/

public class RunLengthCode 
{
    public static void main(String[] args) 
    {
        // test data
        String msg1 = "000000101111101000000000010";
        String msg2 = "1111111111110111111111111000111111111111111111111111011111111111111";
        String rleMsg1 = "OO6IOII5OIOO10IO";
        String rleMsg2 = "II12OII12OO3II24OII14";
        
        test(1, msg1, rleMsg1);
        test(2, msg2, rleMsg2);
    }
    
    public static void test(int n, String msg, String codedMsg)
    {
        System.out.println("Test " + n + ": ");
        String temp = compress(msg);
        System.out.println("compressed message");
        System.out.println(temp);
        if (!temp.equals(codedMsg))
            System.out.println("  *** Error in the compress method");
        
        temp = expand(codedMsg);
        System.out.println("expanded message");
        System.out.println(temp);
        if (!temp.equals(msg))
            System.out.println("  *** Error in the expand method");   
        System.out.println();
    }
    
    // Compress the input message to RLE encoded message.
    // The input message is a sequence of '0' and '1'.
    // We use the letter 'O' to represent '0' and 'I' to represent '1' in the encoded message.
    public static String compress(String msg) 
    { 
        StringBuffer buf = new StringBuffer();
        String[] outSymb = {"O", "I"};
            
        int i = 0;
        while (i < msg.length())
        {
            char c = msg.charAt(i);
            int j = i + 1;
            while (j < msg.length() && msg.charAt(j) == c)
                j++;
                
            int len = j - i;                                
            if (len > 1)                
                buf.append(outSymb[c-'0'] + outSymb[c-'0'] + len);              
            else                
                buf.append(outSymb[c-'0']);
                
            i = j;
        }
        return buf.toString();
    }
    
    // Expand the RLE encoded message to original message.
    // The original message is a sequence of '0' and '1'.
    public static String expand(String codedMsg) 
    {        
        StringBuffer buf = new StringBuffer();
        char prevChar = 'x';    
        int i = 0;
        while (i < codedMsg.length())
        {                
            // assert: codedMsg.charAt(i) is either 'O' or 'I'
            char curChar = codedMsg.charAt(i);              
            char outChar = (curChar == 'O') ? '0' : '1';               
                            
            if (curChar != prevChar)    
            {
                buf.append(outChar); 
                i++;
            }
            else // compressed run
            {                                          
                int j = i+1;
                while (j < codedMsg.length() && Character.isDigit(codedMsg.charAt(j)))
                    j++;
                String num = codedMsg.substring(i+1, j);
                int len = Integer.parseInt(num);                              
                for (int k = 0; k < len-1; k++)
                    buf.append(outChar);  
                    
                i = j;                
            }  
            prevChar = curChar;
        }            
        return buf.toString();              
    }
}

