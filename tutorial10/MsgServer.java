// Student name: 
// Student ID  : 

// Submit 2 files: MsgServer.java and MsgClient.java
// Deadline: Thursday, 25 July 2019, 11 pm

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Hashtable;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.sun.org.apache.regexp.internal.recompile;
import com.sun.webkit.dom.RectImpl;

import java.util.ArrayList;

public class MsgServer extends JFrame
{
    /**
     *
     */
    
    private ServerSocket servSocket;
    private static final int PORT = 12345;
    
    private Hashtable<String, UserRecord> userTable;
    private JTextArea display;
    private ArrayList<String> online = new ArrayList<>();
    
    public MsgServer(String filename)
    {	
        userTable = new Hashtable();
        try
        {
            Scanner sc = new Scanner(new File(filename));
            while (sc.hasNext())
            {
                String name = sc.next().toUpperCase();
                userTable.put(name, new UserRecord(name));
            }
            sc.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");                    
        }
                
        try
        {
            servSocket = new ServerSocket(PORT);
        }                
        catch (IOException e)
        {
            System.out.println("\nUnable to set up port!");
            System.exit(1);
        }        

        setTitle("Message Server");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Messages received:");
        display = new JTextArea(50, 10);
        display.setEditable(false);

        JScrollPane sp = new JScrollPane(display);

        add(label, "North");
        add(sp, "Center");              
        setVisible(true);
    }


    public static void main(String[] args) throws IOException
    {	
        MsgServer ms = new MsgServer("userlist.txt");
        ms.go();
    }

    synchronized protected void displayMsg(String m)
    {
        display.append(m + "\n");       
    }
    

    public void go()
    {	
        System.out.println("Message Server started");

        do
        {
            try
            {
                Socket client = servSocket.accept();

                System.out.println("\nNew client accepted.\n");

                ClientHandler handler = new ClientHandler(client, this);
                handler.start();

            }
            catch (IOException e)
            { }

        } while (true);
    }

    /*
        Implement your methods to process the user commands
        - connect request
        - send request
        - get request
        - quit request
    */

    synchronized protected void removeOnline(String user)
    {
        user = user.toUpperCase();
        if(online.contains(user))
        {
            System.out.println("Unexpected offline occured, automatically remove "+user);
            online.remove(user);
        }
           
    }
    //return true if the user is valid and update the user table
    //else close the socket, terminate the handler and return false
    synchronized protected boolean connectRequest(String user)
    {
        if (userTable.containsKey(user.toUpperCase())&& (!online.contains(user.toUpperCase())))
        {
            online.add(user.toUpperCase());
            return true;
        }
        else
            return false;
    }
    synchronized protected int countMessageNum(String user)
    {
        int count;
        if(userTable.containsKey(user.toUpperCase()))
        {
            count = userTable.get(user.toUpperCase()).msgQueue.size();
            return count;
        }else{
            count = 0;
        }
        return count;
    }

    synchronized protected String getRequest(String user)
    {
        String msg;
        if(userTable.get(user.toUpperCase()).msgQueue.size()>0){
            msg = userTable.get(user.toUpperCase()).msgQueue.poll();
            msg = msg + " : " + userTable.get(user.toUpperCase()).msgQueue.size();
        } else {
            msg = "No Message : from : Server : 0";
        }
        System.out.println(msg);
        return msg; 
    }

    synchronized protected void sendRequest(ClientHandler handler, String sender, String recipient, String msg)
    {
        String recipientUpc = recipient.toUpperCase();
        if(userTable.containsKey(recipientUpc))
        {
            userTable.get(recipientUpc).msgQueue.addLast(msg + " : from : "+sender);
            System.out.println("One message has put in " + recipient + "\'smailbox");
        }else{
            System.out.println("Cannot find the user: "+ recipient);
        }
    }

    synchronized protected void quitRequest(String user, ClientHandler handler)
    {
        online.remove(user.toUpperCase());
        System.out.println("The user " + user + " is now offline");
        try {
            handler.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }




    private class UserRecord   //No changes to the private class are required
    {
        protected String userName;         // Methods in the server can make direct access
        protected ClientHandler handler;   // to the instance variables of UserRecord
        protected ArrayDeque<String> msgQueue;
    
        UserRecord(String name)
        {
            userName = name;
            handler = null;
            msgQueue = new ArrayDeque();
        }    
           
    }
}

class ClientHandler extends Thread
{
    private String user;
    private Socket client;
    private Scanner in;
    private PrintWriter out;
    private MsgServer mServer;

    public ClientHandler(Socket socket, MsgServer ms)
    {
        client = socket;
        mServer = ms;
        try
        {
            in = new Scanner(client.getInputStream());
            out = new PrintWriter(client.getOutputStream(),true);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void sendMessage(String m)
    {	
        out.println(m);
    }

    @Override
    public void run()
    {	
        try
        {			
            while (true)
            {
                String received = in.nextLine();
                mServer.displayMsg(received);
          
                if (isInterrupted())
                    break;
                System.out.println(received);
                String[] tokens = received.split("\\s*:\\s*");
                

                // Your codes to process the data (command) receive from client
                if(tokens[0].equals("connect"))
                {
                    boolean flag = mServer.connectRequest(tokens[1]);
                    
                    mServer.displayMsg("Connecting " + tokens[1] + " " + flag);
                    if(flag)
                    {
                        sendMessage("count : " + mServer.countMessageNum(tokens[1]));
                    }else{
                        sendMessage("msg : " + "You are not a VALID USER : From : Server : 0");
                        break;
                    }

                }else if(tokens[0].equals("get"))
                {
                    String msg = mServer.getRequest(tokens[1]);
                    sendMessage("msg : " + msg);
                }else if(tokens[0].equals("send"))
                {
                    mServer.sendRequest(this, tokens[1], tokens[2], tokens[3]);
                }else 
                {
                    mServer.quitRequest(tokens[1], this);
                    break;
                }

            } 
        }
        catch(IllegalStateException e)
        {  }
        finally
        {
            // Your code to handle the case when the connection is closed.
            try
            {
                if (client!=null)
                {	
                    System.out.println("Closing down connection...");
                    client.close();
                }
            }
            catch(IOException e)
            {  }
        }
    }
}

