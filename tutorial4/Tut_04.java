// Student name: MU Sen
// Student ID  : 55364124

// Submission deadline: Tuesday, 2 July 2019, 4 pm

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.Arrays;
import javax.swing.*;

public class Tut_04 extends JFrame 
{
    static final int DEFAULT_FRAME_WIDTH = 500;
    static final int DEFAULT_FRAME_HEIGHT = 250;
    static final int DISPLAY_SIZE = 32;
    static final int NUMBER_OF_BUTTONS = 18;
    static final int NUMBER_OF_CTL_BUTTONS = 2;
    static final int NUMBER_OF_RADIX = 4;
    static final int[] RADIXES = {2, 8, 10, 16};
    static int radix;
    private final JButton[] buttons;
    private final JTextField display;
    private final JRadioButton[] choices;
    

    public static void main(String[] args) 
    {
        Tut_04 myFrame = new Tut_04();
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setTitle("Number Format Conversion");
        myFrame.setVisible(true);
    }

    public Tut_04() {
        String[] keyLabels = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "CLR", "<<"};
        // CLR = clear,  << = backspace

        String[] choiceLabels = {"Bin", "Oct", "Dec", "Hex"};

        setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);

        JPanel numberPanel = new JPanel();
        numberPanel.setLayout(new GridLayout(4, 4));

        buttons = new JButton[NUMBER_OF_BUTTONS];
        for (int i = 0; i < NUMBER_OF_BUTTONS; i++) 
        {
            buttons[i] = new JButton(keyLabels[i]);
            if (i < NUMBER_OF_BUTTONS - NUMBER_OF_CTL_BUTTONS) 
                numberPanel.add(buttons[i]);
        }

        display = new JTextField(DISPLAY_SIZE);
        display.setHorizontalAlignment(JTextField.RIGHT);  //text is right-justified
        display.setFont(new Font("Dialog", Font.PLAIN, 14)); //select font and size
        display.setEditable(false); //user is not allowed to edit the text display
        display.setText("123456789");

        JPanel textPanel = new JPanel();
        textPanel.add(buttons[16]);
        textPanel.add(display);
        textPanel.add(buttons[17]);
        for(int i = 10; i < buttons.length - 2; i++){
            buttons[i].setEnabled(false);
        }

        JPanel controlPanel = new JPanel();
        ButtonGroup group = new ButtonGroup();
        choices = new JRadioButton[4];
        for (int i = 0; i < NUMBER_OF_RADIX; i++) 
        {
            choices[i] = new JRadioButton(choiceLabels[i]);
            group.add(choices[i]);
            controlPanel.add(choices[i]);
        }
        choices[2].setSelected(true);  // default radix = decimal
        radix = RADIXES[2];

        add(textPanel, "North");
        add(numberPanel, "Center");
        add(controlPanel, "South");

        // Add your codes here to complete the deisgn of the JFrame constructor
        // create the action listener object
        ButtonListener lsn = new ButtonListener();
        // registe all the button with one listener
        for(JRadioButton rb: choices){
            rb.addActionListener(lsn);
        }
        for(JButton b: buttons){
            b.addActionListener(lsn);
        }

    }

    private class ButtonListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent event) 
        {
            // Implement the actionPerformed method  
            String[] keyLabels = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "CLR", "<<"};
            Object source = event.getSource();
            String number = display.getText();
            BigInteger bi_number = new BigInteger(number, radix);
            System.out.println("The current radix is " + radix);
            System.out.println("The current number is " + number);
            if (source instanceof JRadioButton) // choosing the radix
            {
                // Your statements
                //initialize
                for(JButton b: buttons)
                    b.setEnabled(true);
                //disable some buttons
                if (((JRadioButton) source).getText()=="Bin"){
                    for(int i = 2; i < buttons.length-2; i++){      
                        buttons[i].setEnabled(false);
                    }
                    display.setText(bi_number.toString(2));
                    radix = 2;
                } else if (((JRadioButton) source).getText()=="Oct"){
                    for(int i = 8; i< buttons.length-2; i++){
                        buttons[i].setEnabled(false);
                        

                    }
                    display.setText(bi_number.toString(8));
                    radix = 8;
                } else if (((JRadioButton) source).getText()=="Dec"){

                    for(int i = 10; i< buttons.length-2; i++){
                        buttons[i].setEnabled(false);
                    }
                    display.setText(bi_number.toString(10));
                    radix = 10;
                } else{
                    display.setText(bi_number.toString(16).toUpperCase());
                    radix = 16;
                }
                
            } 
            else // Other buttons
            {
                // Your statements
                if (((JButton) source).getText()=="CLR")
                    display.setText("0");
                else if(((JButton) source).getText()=="<<"){
                    if (number.length() == 1){
                        display.setText("0");
                    }else{
                        display.setText(number.substring(0, number.length()-1));
                    }
                    
                }else{
                    Integer idx = Arrays.asList(keyLabels).indexOf(((JButton) source).getText());
                    String fix = keyLabels[idx];
                    String n_disp = number + fix;
                    //remove prefix of multiple zeros
                    display.setText(n_disp.replaceFirst("^0", ""));
                }
            }
  
        }      
    }
}
