// Student name: 
// Student ID  : 

// Submit 2 files: MsgServer.java and MsgClient.java
// Deadline: Thursday, 25 July 2019, 11 pm

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.ws.Response;
import javax.xml.ws.soap.AddressingFeature.Responses;

public class MsgClient extends JFrame implements ActionListener
{
    private static InetAddress host;
    private static final int PORT = 12345;
    private static Socket link;
    private static Scanner in;
    private static PrintWriter out;

    private String user;
    private JTextField msgInput, recipient;
    private JTextField display;
    private JButton sendButton, nextButton, clearButton;
    private JLabel pendingMsg;
  

    public MsgClient(String name)
    {	
        sendButton = new JButton("Send");
        nextButton = new JButton("Get / Next");
        clearButton = new JButton("Clear");
        sendButton.addActionListener(this);
        nextButton.addActionListener(this);
        clearButton.addActionListener(this);

        pendingMsg = new JLabel("Pending Message (0)");
        JLabel label1 = new JLabel("Out Going Message");
        JLabel label2 = new JLabel("Sent To");
        msgInput = new JTextField(50);
        recipient = new JTextField(50);
        msgInput.setEditable(true);
        recipient.setEditable(true);
        display = new JTextField(50);
        display.setEditable(false);
                                
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(3,1));
        panel1.add(pendingMsg);
        panel1.add(label1);
        panel1.add(label2);
        add(panel1, "West");
                
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(3,1));
        panel2.add(display);
        panel2.add(msgInput);
        panel2.add(recipient);
        add(panel2, "Center");
                
        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayout(3,1));
        panel3.add(nextButton);
        panel3.add(sendButton);
        panel3.add(clearButton);
        add(panel3, "East");                
		
        addWindowListener(new MyWinAdapter());
        setTitle(name);
        setSize(500, 120);
        user = name;
    }

    public void actionPerformed(ActionEvent event)
    {  	
        Object source = event.getSource();
	      
        if (source == sendButton)
        {	
            //codes for the sendButton
            out.println("send : " + this.user + " : " + recipient.getText() + " : " + msgInput.getText());
            
        }
        else if (source == nextButton)
        {
            //codes for the nextButton
            out.println("get : " + this.user);
        }
        else if (source == clearButton)
        {
            //codes for the clearButton
            msgInput.setText("");
            display.setText("");
            recipient.setText("");
        }
    }

    private void showResponse(String m)
    {
        display.setText(m);
    }
        
    private void setMsgCount(String value)
    {
        pendingMsg.setText("Pending Message (" + value + ")");
    }
        
    private class MyWinAdapter extends WindowAdapter
    {
        public void windowClosing(WindowEvent e) 
        { 
            if (out != null)
                out.println("quit : " + user);
            System.exit(0);	            
        }            
    }

    public static void main(String[] args) 
    {	
        String user = JOptionPane.showInputDialog("Enter user name");
                        
        MsgClient msgClient = new MsgClient(user);
        msgClient.setVisible(true);
                
        try
        {
            host = InetAddress.getLocalHost();
            link = new Socket(host, PORT);
            System.out.println("Socket created");
                        
            in = new Scanner(link.getInputStream());
            out = new PrintWriter(link.getOutputStream(),true);
                        
            out.println("connect : " + user);  //send the connect request to server

            String response;
            do
            {
            	response = in.nextLine();    
                String[] tokens = response.split("\\s*:\\s*");                  
                               
                //codes to process the data received from the server
                System.out.println(response);
                if(tokens[0].equals("msg"))
                {
                    System.out.println("new message arrived");
                    msgClient.showResponse("From: " + tokens[3] + " : " +tokens[1]);
                    msgClient.setMsgCount(tokens[4]);
                } else if(tokens[0].equals("count")) {
                    msgClient.setMsgCount(tokens[1]);
                }


            } while (true);
        }
        catch(UnknownHostException uhEx)
        {
            System.out.println("\nHost ID not found!\n");
        }
        catch(IOException ioEx)
        {  }
        finally
        {
            try
            {
                if (link!=null)
                {
                    System.out.println("Closing down connection...");
                    link.close();
                    out = null;
                }
            }
            catch(IOException ioEx)
            {  }
        } 
    }
}
