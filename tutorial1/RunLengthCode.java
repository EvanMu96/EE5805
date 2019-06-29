
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util.println;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
// Student name:
// Student ID  :

/*
    Submission deadline: Friday, 21 June 2019, 4 pm. Upload your Java file to canvas.

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
        //String msg3 = "000101001001001010001010010010010010100";
        test(1, msg1, rleMsg1);
        test(2, msg2, rleMsg2);
        //test(3, msg3, compress(msg3));
    }
    
    public static void test(int n, String msg, String codedMsg)
    {
        System.out.println("Test " + n + ": ");
        String temp = compress(msg);
        System.out.println("compressed message");
        System.out.println(temp);
        if (!temp.equals(codedMsg)){
            System.out.println("  *** Error in the compress method");
            System.out.println(codedMsg);
        }
        temp = expand(codedMsg);
        System.out.println("expanded message");
        System.out.println(temp);
        if (!temp.equals(msg)){
            System.out.println("  *** Error in the expand method");
            System.out.println(msg);
        }
        System.out.println();
    }
    
    // Compress the input message to RLE encoded message.
    // The input message is a sequence of '0' and '1'.
    // We use the letter 'O' to represent '0' and 'I' to represent '1' in the encoded message.
    public static String compress(String msg) 
    {   
        StringBuffer buf = new StringBuffer();
        int r_length = 1;
        for (int i=0; i < msg.length()-1 ;i++){
            if (msg.charAt(i) == msg.charAt(i+1)){
                r_length += 1;
            }
            else {
                if (r_length == 1){
                    if (msg.charAt(i) == '0'){
                        buf.append('O');
                    }
                    else{
                        buf.append('I');
                    }
                    
                }
                else{
                    if (msg.charAt(i)=='0'){
                        buf.append("OO");
                        buf.append(r_length);
                    }
                    else{
                        buf.append("II");
                        buf.append(r_length);
                    }
                }
                r_length = 1;
            }
        }
        if (r_length > 1){
                if  (msg.charAt(r_length-1) == '0'){
                    buf.append("OO");
                    buf.append(r_length);
                }
                else{
                    buf.append("II");
                    buf.append(r_length);
                }
            
        }
        else{
            if (msg.charAt(r_length-1) == '0')
                buf.append('O');
            else
                buf.append("I");
        }

        return buf.toString();
    }
    
    // Expand the RLE encoded message to original message.
    // The original message is a sequence of '0' and '1'.
    public static String expand(String codedMsg) 
    {        
        StringBuffer buf = new StringBuffer();
        int start = 0;
        List<String> listStr = new ArrayList<String>();

        // Your codes
        for (int i = 0; i < codedMsg.length(); i++){
            if (Character.isDigit(codedMsg.charAt(i))){
                listStr.add(codedMsg.substring(start, i+1));
                start = i + 1;
            }
  
        }
        if (start != codedMsg.length()-1){
            listStr.add(codedMsg.substring(start));
        }
        for (int i = 0; i < listStr.size(); i++){
            if (listStr.get(i).length() == 1){
                listStr.set(i-1, listStr.get(i-1)+listStr.get(i));
                listStr.remove(i);
            }
        }
        /*System.out.println(listStr);*/
        for (int i = 0; i < listStr.size(); i++){
            for (int j = 0; j < listStr.get(i).length()-1; j++){
                if ((listStr.get(i).charAt(j) == listStr.get(i).charAt(j+1)) &&Character.isLetter(listStr.get(i).charAt(j))){
                    if (listStr.get(i).charAt(j) == 'I'){
                        buf.append(repeat('1', Integer.parseInt(listStr.get(i).substring(j+2))));
                    } else {
                        buf.append(repeat('0', Integer.parseInt(listStr.get(i).substring(j+2))));
                    }
                }
                else if((listStr.get(i).charAt(j) != listStr.get(i).charAt(j+1)) && (Character.isLetter(listStr.get(i).charAt(j+1)))){
                    if (listStr.get(i).charAt(j) == 'I'){
                        buf.append('1');
                    }
                    else{
                        buf.append('0');
                    }
                }
            }
            if (listStr.get(i).endsWith("O")){
                buf.append('0');
            }else if(listStr.get(i).endsWith("I")){
                buf.append('1');
            }
        }
        return buf.toString();              
    }
    public static String repeat(char c, int rep_num)
    {
        StringBuffer buf = new StringBuffer();
        if (c == 0 || rep_num < 1)
            return "";
        for (int i = 0; i < rep_num; i++)
            buf.append(c);
        return buf.toString();
    }
    public static boolean isNumeric(String str) { 
        try {  
            Integer.parseInt(str);  
            return true;
        } catch(NumberFormatException e){  
            return false;  
        }  
    }
    public static String Compress(String str) {
        
        return " ";
    }
}

